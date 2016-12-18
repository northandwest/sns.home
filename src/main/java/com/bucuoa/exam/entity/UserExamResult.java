package com.bucuoa.exam.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Table(name="user_exam_result")
public class UserExamResult implements Serializable {

	@Transient
	private static final long serialVersionUID = 4124700500948l;

		
	@Column(name = "id")
	@Id
	private int id;  // id
	
	@Column(name = "test_id")
	private long testId;
	
	@Column(name = "user_id")
	private int userId;  // 用户id
	
		
	@Column(name = "question_id")
	private int questionId;  // 题目
	
		
	@Column(name = "create_time")
	private Date createTime;  // 时间
	
		
	@Column(name = "result")
	private String result;  // 结果
	
		
	@Column(name = "user_name")
	private String userName;  // 用户名
	
		
	@Column(name = "user_icon")
	private String userIcon;  // 用户小图像
	
		
	@Column(name = "statusx")
	private int statusx;  // 状态
	
	

		public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
		public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
		public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
		public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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
		public int getStatusx() {
		return statusx;
	}

	public void setStatusx(int statusx) {
		this.statusx = statusx;
	}

	public long getTestId() {
		return testId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}
	} 
								 
								