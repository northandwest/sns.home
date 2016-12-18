package com.ulewo.po.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ulewo.po.serializ.CustomDateSerializer;
import com.ulewo.utils.Emotions;
import com.ulewo.utils.Emotions.Dev;

public class SpitSlotComment {
	private Integer id;

	private Integer spitSlotId;

	private String content;

	private String showContent;

	@JsonSerialize(using = CustomDateSerializer.class)
	private Date createTime;

	private Integer userId;

	private String userName;

	private String userIcon;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSpitSlotId() {
		return spitSlotId;
	}

	public void setSpitSlotId(Integer spitSlotId) {
		this.spitSlotId = spitSlotId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon == null ? null : userIcon.trim();
	}

	public String getShowContent() {
		this.showContent = Emotions.formatEmotion(this.content, Dev.WEB);
		return showContent;
	}

	public void setShowContent(String showContent) {
		this.showContent = showContent;
	}
}