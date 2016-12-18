/**
 * Project Name:umei-common
 * File Name:TaskQuery.java
 * Package Name:com.umei.po.query
 * Date:2015年8月4日下午9:39:37
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.query;

/**
 * 
 * ClassName: TaskQuery
 * date: 2015年8月9日 下午12:06:26 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
public class TaskQuery extends BaseQuery {
	private Integer id;
	private String taskClassz;
	private String taskMethod;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
