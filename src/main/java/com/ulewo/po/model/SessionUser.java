/**
 * Project Name:ulewo-common
 * File Name:SessionUser.java
 * Package Name:com.ulewo.po.model
 * Date:2015年9月20日下午8:13:57
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.model;

/**
 * ClassName:SessionUser <br/>
 * Date:     2015年9月20日 下午8:13:57 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class SessionUser {
	private Integer userId;
	private String userName;
	private String userIcon;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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

}
