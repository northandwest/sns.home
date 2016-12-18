/**
 * Project Name:ulewo-web
 * File Name:UserFirendService.java
 * Package Name:com.ulewo.service
 * Date:2015年10月7日上午11:24:49
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.UserFriend;
import com.ulewo.po.query.UserFriendQuery;

/**
 * ClassName:UserFirendService <br/>
 * Date:     2015年10月7日 上午11:24:49 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface UserFriendService {
	/**
	 * 
	 * findFocusList:(查询关注的人). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public List<UserFriend> findFocusList(Integer userId);

	/**
	 * 
	 * findFocusCount:(关注的人数). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public int findFocusCount(Integer userId);

	/**
	 * 
	 * findFansList:(查询粉丝). <br/>
	 *
	 * @author 不错啊
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<UserFriend> findFansList(Integer userId);

	/**
	 * 
	 * findFansCount:(查询粉丝数). <br/>
	 *
	 * @author 不错啊
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public int findFansCount(Integer userId);

	/**
	 * 
	 * findFocusCount:(). <br/>
	 *
	 * @author 不错啊
	 * @param userId
	 * @param friendUserId
	 * @return
	 * @since JDK 1.7
	 */
	public int findFocusCount(Integer userId, Integer friendUserId);

	/**
	 * 
	 * focusUser:(关注). <br/>
	 *
	 * @author 不错啊
	 * @param userFriend
	 * @since JDK 1.7
	 */
	public void focusUser(UserFriend userFriend) throws BusinessException;

	/**
	 * 
	 * cancelFocus:(取消关注). <br/>
	 *
	 * @author 不错啊
	 * @param userFriendQuery
	 * @since JDK 1.7
	 */
	public void cancelFocus(UserFriendQuery userFriendQuery) throws BusinessException;

}
