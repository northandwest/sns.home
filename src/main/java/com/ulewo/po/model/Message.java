package com.ulewo.po.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ulewo.po.serializ.CustomDateSerializer;

public class Message {
	private Integer id;
	private Integer receivedUserId;
	private Integer status;
	private String description;
	private String url;
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReceivedUserId() {
		return receivedUserId;
	}

	public void setReceivedUserId(Integer receivedUserId) {
		this.receivedUserId = receivedUserId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
