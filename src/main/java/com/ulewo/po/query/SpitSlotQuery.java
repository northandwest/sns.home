/**
 * Project Name:ulewo-common
 * File Name:SpitSlotQuery.java
 * Package Name:com.ulewo.po.query
 * Date:2015年9月26日下午7:57:07
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.query;

/**
 * ClassName:SpitSlotQuery <br/>
 * Date:     2015年9月26日 下午7:57:07 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class SpitSlotQuery extends BaseQuery {
	private Integer spitSlotId;
	private Integer id;
	private Integer userId;
	private String startTime;
	private String endTime;
	private String userName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSpitSlotId() {
		return spitSlotId;
	}

	public void setSpitSlotId(Integer spitSlotId) {
		this.spitSlotId = spitSlotId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
