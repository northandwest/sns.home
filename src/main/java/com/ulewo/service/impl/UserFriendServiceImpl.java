/**
 * Project Name:ulewo-web
 * File Name:UserFriendServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年10月7日上午11:33:02
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bucuoa.ucenter.model.UlewoUser;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.UserFriendMapper;
import com.ulewo.po.model.UserFriend;
import com.ulewo.po.query.UserFriendQuery;
import com.ulewo.service.UserFriendService;
import com.ulewo.service.UserService;

/**
 * ClassName:UserFriendServiceImpl <br/>
 * Date:     2015年10月7日 上午11:33:02 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("userFriendService")
public class UserFriendServiceImpl implements UserFriendService {

	@Resource
	private UserFriendMapper<UserFriend, UserFriendQuery> userFriendMapper;

	@Resource
	private UserService userService;

	@Override
	public List<UserFriend> findFocusList(Integer userId) {
		UserFriendQuery query = new UserFriendQuery();
		query.setUserId(userId);
		return userFriendMapper.selectList(query);
	}

	@Override
	public int findFocusCount(Integer userId) {
		UserFriendQuery query = new UserFriendQuery();
		query.setUserId(userId);
		return userFriendMapper.selectCount(query);
	}

	@Override
	public List<UserFriend> findFansList(Integer userId) {
		UserFriendQuery query = new UserFriendQuery();
		query.setFriendUserId(userId);
		return userFriendMapper.selectList(query);
	}

	@Override
	public int findFansCount(Integer userId) {
		UserFriendQuery query = new UserFriendQuery();
		query.setFriendUserId(userId);
		return userFriendMapper.selectCount(query);
	}

	@Override
	public int findFocusCount(Integer userId, Integer friendUserId) {
		UserFriendQuery query = new UserFriendQuery();
		query.setUserId(userId);
		query.setFriendUserId(friendUserId);
		return userFriendMapper.selectCount(query);
	}

	@Override
	public void focusUser(UserFriend userFriend) throws BusinessException {
		if (userFriend.getFriendUserId() == null
				|| userFriend.getUserId().intValue() == userFriend.getFriendUserId().intValue()) {
			throw new BusinessException("参数错误");
		}
		UlewoUser user = userService.findUserByUserId(String.valueOf(userFriend.getFriendUserId()));
		if (user == null) {
			throw new BusinessException("关注的用户不存在");
		}
		userFriend.setFriendUserIcon(user.getUserIcon());
		userFriend.setFriendUserName(user.getUserName());
		userFriend.setCreateTime(new Date());
		userFriendMapper.insert(userFriend);
	}

	@Override
	public void cancelFocus(UserFriendQuery userFriendQuery) throws BusinessException {
		userFriendMapper.delete(userFriendQuery);
	}

}
