package cn.iqinghe.pcc.service;

import java.util.List;

import cn.iqinghe.pcc.domain.User;

/**
 * 赞服务
 * 
 * @author wallechen
 *
 */
public interface LikeService {
	/**
	 * 用户对feed点赞，返回本次点赞后，本条feed的赞总数，如果返回-1，说明失败
	 * 
	 * @param feedId
	 * @param uid
	 * @return
	 */
	long like(Long feedId, Long uid);

	/**
	 * 用户对feed取消点赞,返回本次取消点赞后，本条feed的赞总数，如果返回-1，说明失败
	 * 
	 * @param feedId
	 * @param uid
	 * @return
	 */
	long unlike(Long feedId, Long uid);

	/**
	 * 获取某一feed的like用户id列表
	 * 
	 * @param feedId
	 * @param start
	 * @param pageCount
	 * @param uid
	 * @return
	 */
	List<Long> likeUserIds(int start, int pageCount, Long feedId);

	/**
	 * 获取某一feed的like用户id列表，其中，好友优先显示
	 * 
	 * @param start
	 * @param pageCount
	 * @param feedId
	 * @param uid
	 * @return
	 */
	List<Long> likeUserIdsWithFriendsFirst(int start, int pageCount, Long feedId, Long uid);

	/**
	 * 获取某一feed的like用户列表
	 * 
	 * @param start
	 * @param pageCount
	 * @param feedId
	 * @return
	 */
	List<User> likeUsers(int start, int pageCount, Long feedId);

	/**
	 * 获取某一feed的like用户列表，其中，好友优先显示
	 * 
	 * @param start
	 * @param pageCount
	 * @param feedId
	 * @return
	 */
	List<User> likeUsersWithFriendsFirst(int start, int pageCount, Long feedId, Long uid);

	/**
	 * 获取某一feed的like总数
	 * 
	 * @param feedId
	 * @return
	 */
	long likeUserCount(Long feedId);

	/**
	 * 用户对本条feed是否点赞
	 * 
	 * @param feedId
	 * @param uid
	 * @return
	 */
	boolean hasLiked(Long feedId, Long uid);
}
