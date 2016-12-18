package com.ulewo.po.model;

import java.util.Date;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-15 下午8:09:37
 * @version 3.0
 * @copyright www.bucuoa.com
 */
public class TopicVoteUser {
	private Integer VoteDtlId;
	private Integer userId;
	private Date voteDate;
	private String title;

	public Integer getVoteDtlId() {
		return VoteDtlId;
	}

	public void setVoteDtlId(Integer voteDtlId) {
		VoteDtlId = voteDtlId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getVoteDate() {
		return voteDate;
	}

	public void setVoteDate(Date voteDate) {
		this.voteDate = voteDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
