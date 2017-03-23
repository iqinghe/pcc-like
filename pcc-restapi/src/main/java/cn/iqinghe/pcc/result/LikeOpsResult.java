package cn.iqinghe.pcc.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.iqinghe.pcc.domain.User;

/**
 * 点赞、取消点赞操作结果
 * 
 * @author wallechen
 *
 */
public class LikeOpsResult implements Serializable {
	private static final long serialVersionUID = 5580239695882408838L;
	private long oid = -1;
	private long uid = -1;
	private List<User> like_list = new ArrayList<User>();

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public List<User> getLike_list() {
		return like_list;
	}

	public void setLike_list(List<User> like_list) {
		this.like_list = like_list;
	}

}
