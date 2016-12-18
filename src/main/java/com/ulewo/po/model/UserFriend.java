package com.ulewo.po.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ulewo.po.serializ.CustomDateSerializer;

/**
 * 好友
 * 
 * @author luo.hl
 * @date 2013-12-8 下午3:46:37
 * @version 3.0
 * @copyright www.bucuoa.com
 */
public class UserFriend {
	private Integer userId;
	private String userName;
	private String userIcon;
	private Integer friendUserId;
	private String friendUserName;
	private String friendUserIcon;
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date createTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public Integer getFriendUserId() {
		return friendUserId;
	}

	public void setFriendUserId(Integer friendUserId) {
		this.friendUserId = friendUserId;
	}

	public String getFriendUserName() {
		return friendUserName;
	}

	public void setFriendUserName(String friendUserName) {
		this.friendUserName = friendUserName;
	}

	public String getFriendUserIcon() {
		return friendUserIcon;
	}

	public void setFriendUserIcon(String friendUserIcon) {
		this.friendUserIcon = friendUserIcon;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
