package com.ulewo.po.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Statistics {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date statisticsDate;

	private Integer signinCount;

	private Integer spitSlotCount;

	private Integer spitSlotCommentCount;

	private Integer topicCount;

	private Integer topicCommentCount;

	private Integer knowledgeCount;

	private Integer knowledgeCommentCount;

	private Integer askCount;

	private Integer askCommentCount;

	private Integer blogCount;

	private Integer blogCommentCount;

	private Integer examCount;

	private Integer userCount;

	private Integer activeUserCount;

	public Date getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public Integer getSigninCount() {
		return signinCount;
	}

	public void setSigninCount(Integer signinCount) {
		this.signinCount = signinCount;
	}

	public Integer getSpitSlotCount() {
		return spitSlotCount;
	}

	public void setSpitSlotCount(Integer spitSlotCount) {
		this.spitSlotCount = spitSlotCount;
	}

	public Integer getSpitSlotCommentCount() {
		return spitSlotCommentCount;
	}

	public void setSpitSlotCommentCount(Integer spitSlotCommentCount) {
		this.spitSlotCommentCount = spitSlotCommentCount;
	}

	public Integer getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

	public Integer getTopicCommentCount() {
		return topicCommentCount;
	}

	public void setTopicCommentCount(Integer topicCommentCount) {
		this.topicCommentCount = topicCommentCount;
	}

	public Integer getKnowledgeCount() {
		return knowledgeCount;
	}

	public void setKnowledgeCount(Integer knowledgeCount) {
		this.knowledgeCount = knowledgeCount;
	}

	public Integer getKnowledgeCommentCount() {
		return knowledgeCommentCount;
	}

	public void setKnowledgeCommentCount(Integer knowledgeCommentCount) {
		this.knowledgeCommentCount = knowledgeCommentCount;
	}

	public Integer getAskCount() {
		return askCount;
	}

	public void setAskCount(Integer askCount) {
		this.askCount = askCount;
	}

	public Integer getAskCommentCount() {
		return askCommentCount;
	}

	public void setAskCommentCount(Integer askCommentCount) {
		this.askCommentCount = askCommentCount;
	}

	public Integer getBlogCount() {
		return blogCount;
	}

	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}

	public Integer getBlogCommentCount() {
		return blogCommentCount;
	}

	public void setBlogCommentCount(Integer blogCommentCount) {
		this.blogCommentCount = blogCommentCount;
	}

	public Integer getExamCount() {
		return examCount;
	}

	public void setExamCount(Integer examCount) {
		this.examCount = examCount;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Integer getActiveUserCount() {
		return activeUserCount;
	}

	public void setActiveUserCount(Integer activeUserCount) {
		this.activeUserCount = activeUserCount;
	}
}