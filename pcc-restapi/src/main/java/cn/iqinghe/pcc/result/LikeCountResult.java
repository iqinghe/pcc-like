package cn.iqinghe.pcc.result;

import java.io.Serializable;

public class LikeCountResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8163053201529386930L;
	private long oid;
	private long count;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
