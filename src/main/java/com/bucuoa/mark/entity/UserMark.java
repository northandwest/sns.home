package com.bucuoa.mark.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Table(name = "user_mark")
public class UserMark implements Serializable {

	@Transient
	private static final long serialVersionUID = 1475424001003l;

	@Column(name = "id")
	@Id
	private int id; // id

	@Column(name = "user_id")
	private int userId; // 用户

	@Column(name = "mark")
	private int mark; // 积分

	@Column(name = "typex")
	private int typex; // 类型

	@Column(name = "create_time")
	private Date createTime; // 时间

	@Column(name = "status")
	private int status; // 状态

	@Column(name = "current")
	private int current; // 当前积分

	@Column(name = "relate_id")
	private int relateId; // 关联

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

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

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getTypex() {
		return typex;
	}

	public void setTypex(int typex) {
		this.typex = typex;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRelateId() {
		return relateId;
	}

	public void setRelateId(int relateId) {
		this.relateId = relateId;
	}
}
