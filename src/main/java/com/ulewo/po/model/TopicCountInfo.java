/**
 * Project Name:ulewo-common
 * File Name:TopicCountInfo.java
 * Package Name:com.ulewo.po.model
 * Date:2015年11月7日下午9:03:47
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.model;

/**
 * ClassName:TopicCountInfo <br/>
 * Date:     2015年11月7日 下午9:03:47 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class TopicCountInfo {
	private Integer count;
	private Integer todayCount;
	private Integer yesterdayCount;

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

	public Integer getYesterdayCount() {
		return yesterdayCount;
	}

	public void setYesterdayCount(Integer yesterdayCount) {
		this.yesterdayCount = yesterdayCount;
	}

}
