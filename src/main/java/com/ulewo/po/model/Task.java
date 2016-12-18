/**
 * Project Name:umei-common
 * File Name:Task.java
 * Package Name:com.umei.po
 * Date:2015年8月4日下午9:01:32
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ulewo.po.enums.Taskstatus;

/**
 * 
 * ClassName: Task
 * date: 2015年8月9日 下午12:05:38 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
public class Task {
	private Integer id;
	private String taskClassz;
	private String taskMethod;
	private String taskTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastupdateTime;
	private Taskstatus taskStatus;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskClassz() {
		return taskClassz;
	}

	public void setTaskClassz(String taskClassz) {
		this.taskClassz = taskClassz;
	}

	public String getTaskMethod() {
		return taskMethod;
	}

	public void setTaskMethod(String taskMethod) {
		this.taskMethod = taskMethod;
	}

	public Date getLastupdateTime() {
		return lastupdateTime;
	}

	public void setLastupdateTime(Date lastupdateTime) {
		this.lastupdateTime = lastupdateTime;
	}

	public Taskstatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Taskstatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "任务类名：" + this.taskClassz + ",方法名：" + this.taskMethod + ",调度时间:" + this.taskTime + ",描述："
				+ this.getDescription();
	}
}
