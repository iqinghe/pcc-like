package cn.iqinghe.pcc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞
 * 
 * @author wallechen
 *
 */
public class Like implements Serializable {
	private static final long serialVersionUID = 1L;
	// PK
	private Long id;
	// 信息流
	private Long feedId;
	// 点赞用户
	private Long uid;
	// 创建时间
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFeedId() {
		return feedId;
	}

	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
