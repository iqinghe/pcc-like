package cn.iqinghe.pcc.result;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import cn.iqinghe.pcc.domain.User;

/**
 * feed赞列表结果
 * 
 * @author wallechen
 *
 */
public class LikeUsersResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7170920916130464457L;
	// oid
	private long oid;
	private int next_cursor;
	private List<User> like_list = new LinkedList<User>();

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public int getNext_cursor() {
		return next_cursor;
	}

	public void setNext_cursor(int next_cursor) {
		this.next_cursor = next_cursor;
	}

	public List<User> getLike_list() {
		return like_list;
	}

	public void setLike_list(List<User> like_list) {
		this.like_list = like_list;
	}
}
