package com.ulewo.po.model;

import java.util.ArrayList;
import java.util.List;

public class Category implements Cloneable {
	private Integer categoryId;
	private Integer pid;
	private String name;
	private String description;
	private Integer rang;
	private Integer count;
	private Integer todayCount;
	private String allowPost;
	private String showInBBS;
	private String showInQuestion;
	private String showInKnowledge;
	private String showInExam;
	private List<Category> children = new ArrayList<Category>();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRang() {
		return rang;
	}

	public void setRang(Integer rang) {
		this.rang = rang;
	}

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

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public String getShowInExam() {
		return showInExam;
	}

	public void setShowInExam(String showInExam) {
		this.showInExam = showInExam;
	}

	public String getAllowPost() {
		return allowPost;
	}

	public void setAllowPost(String allowPost) {
		this.allowPost = allowPost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTodayCount() {
		return todayCount;
	}

	public void setTodayCount(Integer todayCount) {
		this.todayCount = todayCount;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
