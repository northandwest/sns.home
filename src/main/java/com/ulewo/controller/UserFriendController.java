/**
 * Project Name:ulewo-web
 * File Name:UserController.java
 * Package Name:com.ulewo.controller
 * Date:2015年9月20日上午11:17:18
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.UserFriend;
import com.ulewo.po.query.UserFriendQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.service.UserFriendService;

/**
 * 
 * ClassName: UserFriendController
 * date: 2015年10月7日 下午12:55:19 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */

@Controller
@RequestMapping("/user")
public class UserFriendController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(UserFriendController.class);

	@Resource
	private UserFriendService userFriendService;

	@ResponseBody
	@RequestMapping(value = "focus.action")
	public AjaxResponse<?> focus(HttpSession session, UserFriend userFriend) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			this.setUserBaseInfo(UserFriend.class, userFriend, session);
			userFriendService.focusUser(userFriend);
		} catch (BusinessException e) {
			result.setErrorMsg(e.getMessage());
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			logger.error("关注用户失败", e);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("关注用户失败", e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "cancel_focus.action")
	public AjaxResponse<?> cancel_focus(HttpSession session, UserFriendQuery query) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			query.setUserId(this.getUserId(session));
			userFriendService.cancelFocus(query);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("取消关注异常", e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "loadFocus")
	public AjaxResponse<List<UserFriend>> loadFocus(HttpSession session, Integer userId) {
		AjaxResponse<List<UserFriend>> result = new AjaxResponse<List<UserFriend>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			userId = userId == null ? this.getUserId(session) : userId;
			List<UserFriend> list = userFriendService.findFocusList(userId);
			result.setData(list);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("获取用户关注的用户失败", e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "loadFans")
	public AjaxResponse<List<UserFriend>> loadFans(HttpSession session, Integer userId) {
		AjaxResponse<List<UserFriend>> result = new AjaxResponse<List<UserFriend>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			userId = userId == null ? this.getUserId(session) : userId;
			List<UserFriend> list = userFriendService.findFansList(userId);
			result.setData(list);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("获取用户的粉丝失败", e);
		}
		return result;
	}
}
