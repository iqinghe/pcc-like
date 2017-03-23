package cn.iqinghe.pcc.service;

import java.util.List;

import cn.iqinghe.pcc.domain.Feed;

/**
 * 信息流服务
 * 
 * @author wallechen
 *
 */
public interface FeedService {
	/**
	 * 获取我的信息流
	 * 
	 * @param start
	 * @param pageCount
	 * @param uid
	 * 
	 * @return
	 */
	List<Feed> listFeeds(int start, int pageCount, Long uid);

	/**
	 * 根据id获取feed信息
	 * 
	 * @param feedId
	 * @return
	 */
	Feed getFeed(Long feedId);
}
