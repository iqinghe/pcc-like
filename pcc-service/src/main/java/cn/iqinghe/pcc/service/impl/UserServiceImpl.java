package cn.iqinghe.pcc.service.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.iqinghe.pcc.dao.UserMapper;
import cn.iqinghe.pcc.dao.UserRelationMapper;
import cn.iqinghe.pcc.domain.User;
import cn.iqinghe.pcc.domain.UserRelation;
import cn.iqinghe.pcc.service.UserService;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UserServiceImpl implements UserService {
	public final static String KEY_USER_FRIENDS = "friends:";
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRelationMapper userRelationMapper;
	@Autowired
	private StringRedisTemplate template;

	@Override
	@Cacheable("users")
	public User getById(Long uid) {
		if (uid == null) {
			return null;
		}
		return userMapper.getById(uid);
	}

	@Override
	public List<UserRelation> listUserFriends(int start, int pageCount, Long uid) {
		if (uid == null) {
			return Collections.emptyList();
		}
		PageHelper.startPage(start, pageCount);
		return userRelationMapper.listUserFriends(uid);
	}

	@Override
	public List<Long> listUserFriendIds(int start, int pageCount, Long uid) {
		if (uid == null) {
			return Collections.emptyList();
		}
		ZSetOperations<String, String> ops = this.template.opsForZSet();
		Set<String> friendIds = ops.range(KEY_USER_FRIENDS + uid, start, start + pageCount);
		if (friendIds.isEmpty()) {
			List<UserRelation> relations = this.listUserFriends(start, pageCount, uid);
			List<Long> result = new LinkedList<Long>();
			for (UserRelation ur : relations) {
				result.add(ur.getToUid());
			}
			// 此处不增加缓存，在用户关系写入时增加缓存
			return result;
		} else {
			List<Long> result = new LinkedList<Long>();
			for (String f_id : friendIds) {
				result.add(Long.parseLong(f_id));
			}
			return result;
		}
	}
}
