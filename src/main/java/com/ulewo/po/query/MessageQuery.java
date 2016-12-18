/**
 * Project Name:ulewo-common
 * File Name:MessageQuery.java
 * Package Name:com.ulewo.po.query
 * Date:2015年11月30日下午9:30:30
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.query;

import com.ulewo.po.enums.StatusEnum;

/**
 * ClassName:MessageQuery <br/>
 * Date:     2015年11月30日 下午9:30:30 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class MessageQuery extends BaseQuery {
	private Integer id;
	private Integer receivedUserId;
	private String startTime;
	private String endTime;
	private StatusEnum status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReceivedUserId() {
		return receivedUserId;
	}

	public void setReceivedUserId(Integer receivedUserId) {
		this.receivedUserId = receivedUserId;
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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
