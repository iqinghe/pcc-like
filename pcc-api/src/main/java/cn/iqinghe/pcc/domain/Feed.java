package cn.iqinghe.pcc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 信息流
 * 
 * @author wallechen
 *
 */
public class Feed implements Serializable {
	private static final long serialVersionUID = 1L;
	// PK
	private Long id;
	// 内容
	private String content;
	// 作者
	private Long uid;
	// 创建时间
	private Date createDate;
	// 状态
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
