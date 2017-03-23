package cn.iqinghe.pcc.result;

import java.io.Serializable;

public class LikedResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5802430065977603461L;
	private long oid = -1;
	private long uid = -1;
	private int is_like = 0;

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

	public int getIs_like() {
		return is_like;
	}

	public void setIs_like(int is_like) {
		this.is_like = is_like;
	}
}
