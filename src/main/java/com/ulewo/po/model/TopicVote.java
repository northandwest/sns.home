package com.ulewo.po.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ulewo.po.enums.VoteType;

/**
 * 调查
 * 
 * @author luo.hl
 * @date 2013-12-8 下午3:33:06
 * @version 3.0
 * @copyright www.bucuoa.com
 */
public class TopicVote {
	private Integer voteId;
	private Integer topicId;
	private VoteType voteType;
	private String endDateStr;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate;
	private int sumCount;
	private List<TopicVoteDtl> dtlList = new ArrayList<TopicVoteDtl>();
	private List<TopicVoteUser> voteUser = new ArrayList<TopicVoteUser>();
	private boolean canVote;

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getSumCount() {
		return sumCount;
	}

	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}

	public List<TopicVoteDtl> getDtlList() {
		return dtlList;
	}

	public void setDtlList(List<TopicVoteDtl> dtlList) {
		this.dtlList = dtlList;
	}

	public List<TopicVoteUser> getVoteUser() {
		return voteUser;
	}

	public void setVoteUser(List<TopicVoteUser> voteUser) {
		this.voteUser = voteUser;
	}

	public boolean isCanVote() {
		return canVote;
	}

	public void setCanVote(boolean canVote) {
		this.canVote = canVote;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Integer getVoteId() {
		return voteId;
	}

	public void setVoteId(Integer voteId) {
		this.voteId = voteId;
	}

}
