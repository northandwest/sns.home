/**
 * Project Name:ulewo-web
 * File Name:ManagerSpitSlotController.java
 * Package Name:com.ulewo.controller
 * Date:2015年12月10日下午9:38:43
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.User;
import com.ulewo.po.query.UserQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.UserService;

/**
 * 
 * ClassName: ManagerKnowledgeController
 * date: 2015年12月13日 下午9:08:51 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/manage/user")
public class ManageUserController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ManageUserController.class);

	@Resource
	private UserService userService;

	@RequestMapping(value = "user_list")
	public ModelAndView user_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/manage/user_list");
		return view;
	}

	/**
	 * 加载用户
	 * load_user:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "load_user")
	public AjaxResponse<PaginationResult<User>> load_user(UserQuery query) {
		AjaxResponse<PaginationResult<User>> result = new AjaxResponse<PaginationResult<User>>();
		try {
			PaginationResult<User> data = null;//userService.findUserByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载用户异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载用户异常");
		}
		return result;
	}

	/**
	 * 
	 * delete_topic:(删除主题)
	 * @author luohaili
	 * @param ids
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "delete_user")
	public AjaxResponse<?> delete_topic(Integer userId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			//topicService.deleteTopic(ids);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除用户异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除用户异常");
		}
		return result;
	}

	/**
	 * 奖励积分
	 * reward_mark:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param userId
	 * @param mark
	 * @param message
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "reward_mark")
	public AjaxResponse<?> reward_mark(Integer userId, Integer mark, String message) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			userService.rewardMark(userId, mark, message);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("奖励积分异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("奖励积分异常");
		}
		return result;
	}

	/**
	 * 警告
	 * warn_user:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param userId
	 * @param message
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "warn_user")
	public AjaxResponse<?> warn_user(Integer userId, String message) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
//			userService.warnUser(userId, message);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("警告用户异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("警告用户异常");
		}
		return result;
	}

	/**
	 * 删除用户
	 * del_user:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param userId
	 * @param message
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "del_user")
	public AjaxResponse<?> del_user(Integer userId, String message) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			userService.delete(userId);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除用户异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除用户异常");
		}
		return result;
	}
}
