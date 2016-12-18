/**
 * Project Name:ulewo-common
 * File Name:UserFriendQuery.java
 * Package Name:com.ulewo.po.query
 * Date:2015年10月7日上午11:20:49
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.query;

/**
 * ClassName:UserFriendQuery <br/>
 * Date:     2015年10月7日 上午11:20:49 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class UserFriendQuery extends BaseQuery {
	private Integer userId;
	private Integer friendUserId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFriendUserId() {
		return friendUserId;
	}

	public void setFriendUserId(Integer friendUserId) {
		this.friendUserId = friendUserId;
	}

}
