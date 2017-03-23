package cn.iqinghe.pcc.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.iqinghe.pcc.dao.LikeMapper;
import cn.iqinghe.pcc.domain.Like;
import cn.iqinghe.pcc.domain.User;
import cn.iqinghe.pcc.service.LikeService;
import cn.iqinghe.pcc.service.UserService;

/**
 * like server
 * 
 * @author wallechen
 *
 */
@Service
public class LikeServiceImpl implements LikeService {
	public final static String KEY_FEED_LIKE = "like_list:";
	public final static String KEY_FEED_LIKE_COUNT = "like_count:";
	public final static String KEY_FEED_LIKE_FRIENDS = "like_list_friends:";
	public final static String KEY_FEED_LIKE_OTHERS = "like_list_others:";
	@Autowired
	private LikeMapper likeMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private StringRedisTemplate template;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long like(Long feedId, Long uid) {
		if (feedId == null || uid == null) {
			return -1;
		}
		int dbResult = -1;
		Like like = new Like();
		like.setCreateDate(new Date());
		like.setFeedId(feedId);
		like.setUid(uid);
		dbResult = likeMapper.insert(like);
		if (dbResult > 0) {
			ZSetOperations<String, String> feedLikes = this.template.opsForZSet();
			ValueOperations<String, String> feedCount = this.template.opsForValue();
			feedLikes.add(KEY_FEED_LIKE + feedId, Long.toString(uid), System.currentTimeMillis());
			return feedCount.increment(KEY_FEED_LIKE_COUNT + feedId, 1);
		}
		return -1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long unlike(Long feedId, Long uid) {
		if (feedId == null || uid == null) {
			return -1;
		}
		Like like = likeMapper.getLike(feedId, uid);
		if (like == null) {
			return -1;
		} else {
			int deleteResult = likeMapper.delete(like.getId());
			if (deleteResult > 0) {
				ZSetOperations<String, String> feedLikes = this.template.opsForZSet();
				ValueOperations<String, String> feedCount = this.template.opsForValue();
				feedLikes.remove(KEY_FEED_LIKE + feedId, Long.toString(uid));
				return feedCount.increment(KEY_FEED_LIKE_COUNT + feedId, -1);
			}
			return -1;
		}
	}

	@Override
	public List<User> likeUsers(int start, int pageCount, Long feedId) {
		if (feedId == null) {
			return Collections.emptyList();
		}
		List<Long> userIds = this.likeUserIds(start, pageCount, feedId);
		List<User> result = new LinkedList<User>();
		for (Long userId : userIds) {
			result.add(userService.getById(userId));
		}
		return result;
	}

	@Override
	public List<User> likeUsersWithFriendsFirst(int start, int pageCount, Long feedId, Long uid) {
		if (feedId == null) {
			return Collections.emptyList();
		}
		List<Long> userIds = this.likeUserIdsWithFriendsFirst(start, pageCount, feedId, uid);
		List<User> result = new LinkedList<User>();
		for (Long userId : userIds) {
			result.add(userService.getById(userId));
		}
		return result;
	}

	@Override
	public List<Long> likeUserIds(int start, int pageCount, Long feedId) {
		if (feedId == null) {
			return Collections.emptyList();
		}
		ZSetOperations<String, String> feedLikes = this.template.opsForZSet();
		Set<String> uids = feedLikes.range(KEY_FEED_LIKE + feedId, start, start + pageCount - 1);
		return transfer(uids);
	}

	@Override
	public List<Long> likeUserIdsWithFriendsFirst(int start, int pageCount, Long feedId, Long uid) {
		if (feedId == null) {
			return Collections.emptyList();
		}
		ZSetOperations<String, String> feedLikes = this.template.opsForZSet();
		// 求交集，得到好友关系
		feedLikes.intersectAndStore(KEY_FEED_LIKE + feedId, UserServiceImpl.KEY_USER_FRIENDS + uid,
				KEY_FEED_LIKE_FRIENDS + feedId);
		Set<String> friends = feedLikes.range(KEY_FEED_LIKE_FRIENDS + feedId, start, start + pageCount);
		if (friends.size() > pageCount) {
			return transfer(friends);
		} else {
			// TODO
			// 剩下的，全部遍历，然后检查是否是好友，如果是，直接排除。如果不是，增加到列表中；
			// 由于涉及到分页，需要记录上次操作的index
		}
		return Collections.emptyList();
	}

	@Override
	public long likeUserCount(Long feedId) {
		if (feedId == null) {
			return -1;
		}
		ValueOperations<String, String> feedCount = this.template.opsForValue();
		String result = feedCount.get(KEY_FEED_LIKE_COUNT + feedId);
		if (StringUtils.isEmpty(result)) {
			return -1;
		}
		return Long.parseLong(result);
	}

	@Override
	public boolean hasLiked(Long feedId, Long uid) {
		if (feedId == null || uid == null) {
			return false;
		}
		// 直接从数据库中判断即可，没必要从缓存查找
		Like like = likeMapper.getLike(feedId, uid);
		if (like == null) {
			return false;
		}
		return true;
	}

	private List<Long> transfer(Set<String> ori) {
		List<Long> result = new LinkedList<Long>();
		if (ori == null || ori.isEmpty()) {
			return result;
		}
		for (String s : ori) {
			result.add(Long.parseLong(s));
		}
		return result;
	}
}
