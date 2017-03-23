package cn.iqinghe.pcc.service.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.iqinghe.pcc.dao.FeedMapper;
import cn.iqinghe.pcc.domain.Feed;
import cn.iqinghe.pcc.service.FeedService;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class FeedServiceImpl implements FeedService {
	public final static String KEY_FEED_TIMELINE = "timelines:";
	@Autowired
	private FeedMapper feedMapper;
	@Autowired
	private RedisTemplate<String, String> template;

	@Override
	public List<Feed> listFeeds(int start, int pageCount, Long uid) {
		if (uid == null) {
			return Collections.emptyList();
		}
		ZSetOperations<String, String> ops = this.template.opsForZSet();
		Set<String> timelines = ops.range(KEY_FEED_TIMELINE + uid, start, start + pageCount);
		if (timelines.isEmpty()) {
			PageHelper.startPage(start, pageCount);
			List<Feed> result = feedMapper.listFeeds(uid);
			// 缓存写入在feed写入时操作，由于本项目无写入，此处直接返回数据库中信息
			return result;
		} else {
			List<Feed> result = new LinkedList<Feed>();
			for (String feedId : timelines) {
				Feed feed = this.getFeed(Long.parseLong(feedId));
				if (feed != null) {
					result.add(feed);
				}
			}
			return result;
		}
	}

	@Override
	@Cacheable("feeds")
	public Feed getFeed(Long feedId) {
		if (feedId == null) {
			return null;
		}
		return feedMapper.getById(feedId);
	}

}
