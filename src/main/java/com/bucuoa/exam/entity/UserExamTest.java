package com.bucuoa.exam.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "user_exam_test")
public class UserExamTest implements Serializable {

	@Transient
	private static final long serialVersionUID = 1474815159905l;

	@Column(name = "id")
	@Id
	private long id; // id

	@Column(name = "user_id")
	private int userId; // 用户id

	@Column(name = "user_name")
	private String userName; // 用户名

	@Column(name = "user_icon")
	private String userIcon; // 用户小图像

	@Column(name = "create_time")
	private Date createTime; // 时间

	@Column(name = "statusx")
	private int statusx; // 状态

	@Column(name = "total_question")
	private int totalQuestion; // 总题数

	@Column(name = "right_nums")
	private int rightNums; // 正确题数

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatusx() {
		return statusx;
	}

	public void setStatusx(int statusx) {
		this.statusx = statusx;
	}

	public int getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(int totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public int getRightNums() {
		return rightNums;
	}

	public void setRightNums(int rightNums) {
		this.rightNums = rightNums;
	}

}
