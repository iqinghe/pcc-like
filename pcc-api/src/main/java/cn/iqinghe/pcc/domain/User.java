package cn.iqinghe.pcc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * 
 * @author wallechen
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	// PK
	private Long uid;
	// 用户名
	private String username;
	// 昵称
	private String nickname;
	// 创建时间
	private Date createDate;
	// 头像（小头像）
	private String avatar;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
