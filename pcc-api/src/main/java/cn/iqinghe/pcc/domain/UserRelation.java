package cn.iqinghe.pcc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户好友关系
 * 
 * @author wallechen
 *
 */
public class UserRelation implements Serializable {
	private static final long serialVersionUID = 1L;
	// PK
	private Long id;
	// 发起方
	private Long fromUid;
	// 接收方
	private Long toUid;
	// 创建时间
	private Date createDate;
	// 状态，是否有效
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFromUid() {
		return fromUid;
	}

	public void setFromUid(Long fromUid) {
		this.fromUid = fromUid;
	}

	public Long getToUid() {
		return toUid;
	}

	public void setToUid(Long toUid) {
		this.toUid = toUid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
