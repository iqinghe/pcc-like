package cn.iqinghe.pcc.service;

import java.util.List;

import cn.iqinghe.pcc.domain.User;
import cn.iqinghe.pcc.domain.UserRelation;

/**
 * 用户服务，将用户关系也同时放在此服务中实现
 * 
 * @author wallechen
 *
 */
public interface UserService {
	/**
	 * 获取用户信息
	 * 
	 * @param uid
	 * @return
	 */
	User getById(Long uid);

	/**
	 * 获取用户好友列表
	 * 
	 * @param uid
	 * @param start
	 * @param pageCount
	 * @return
	 */
	List<UserRelation> listUserFriends(int start, int pageCount, Long uid);

	/**
	 * 获取用户好友id列表
	 * 
	 * @param start
	 * @param pageCount
	 * @param uid
	 * @return
	 */
	List<Long> listUserFriendIds(int start, int pageCount, Long uid);
}
