/**
 * Project Name:ulewo-common
 * File Name:CategoryQuery.java
 * Package Name:com.ulewo.po.query
 * Date:2015年10月17日下午12:58:41
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.query;


/**
 * ClassName:CategoryQuery <br/>
 * Date:     2015年10月17日 下午12:58:41 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class CategoryQuery extends BaseQuery {
	private Integer categoryId;
	private Integer pid;
	private String startDate;
	private String endDate;
	private String showInBBS;
	private String showInQuestion;
	private String showInKnowledge;
	private String showInExam;

	public String getShowInBBS() {
		return showInBBS;
	}

	public void setShowInBBS(String showInBBS) {
		this.showInBBS = showInBBS;
	}

	public String getShowInQuestion() {
		return showInQuestion;
	}

	public void setShowInQuestion(String showInQuestion) {
		this.showInQuestion = showInQuestion;
	}

	public String getShowInKnowledge() {
		return showInKnowledge;
	}

	public void setShowInKnowledge(String showInKnowledge) {
		this.showInKnowledge = showInKnowledge;
	}

	public String getShowInExam() {
		return showInExam;
	}

	public void setShowInExam(String showInExam) {
		this.showInExam = showInExam;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
