package com.ulewo.po.query;

import java.util.Date;

/**
 * TODO: 增加描述
 * 
 * @author luohaili
 * @date 2015年9月24日 下午4:59:48
 * @version 0.1.0 
 */
public class SignInQuery extends BaseQuery {
	private Integer userId;
	private Date curDate;
	private Date startDate;
	private Date endDate;
	private Integer year;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCurDate() {
		return curDate;
	}

	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}
