/**
 * Project Name:ulewo-common
 * File Name:ExamQuery.java
 * Package Name:com.ulewo.po.query
 * Date:2015年10月10日下午10:06:03
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.query;

import com.ulewo.po.enums.StatusEnum;

/**
 * ClassName:ExamQuery <br/>
 * Date:     2015年10月10日 下午10:06:03 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class ExamQuery extends BaseQuery {
	private StatusEnum status;
	private Integer categoryId;
	private Integer userId;

	private Integer examId;
	private String[] examIds;
	private Boolean showAnalyse;
	private Integer examMaxTitle;
	private String userName;
	private String startTime;
	private String endTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public String[] getExamIds() {
		return examIds;
	}

	public void setExamIds(String[] examIds) {
		this.examIds = examIds;
	}

	public Boolean getShowAnalyse() {
		return showAnalyse;
	}

	public void setShowAnalyse(Boolean showAnalyse) {
		this.showAnalyse = showAnalyse;
	}

	public Integer getExamMaxTitle() {
		return examMaxTitle;
	}

	public void setExamMaxTitle(Integer examMaxTitle) {
		this.examMaxTitle = examMaxTitle;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
