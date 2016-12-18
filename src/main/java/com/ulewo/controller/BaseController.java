/**
 * Project Name:ulewo-web
 * File Name:BaseController.java
 * Package Name:com.ulewo.controller
 * Date:2015年9月26日下午11:06:20
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import com.ulewo.po.model.SessionUser;
import com.ulewo.utils.Constants;

/**
 * ClassName:BaseController <br/>
 * Date:     2015年9月26日 下午11:06:20 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class BaseController {

	public void setUserBaseInfo(Class<?> classz, Object obj, HttpSession session) {
		SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER_KEY);
		Integer userId = sessionUser.getUserId();
		String userIcon = sessionUser.getUserIcon();
		String userName = sessionUser.getUserName();

		try {
			Method userIdMethod = classz.getDeclaredMethod("setUserId", Integer.class);
			userIdMethod.invoke(obj, userId);
			Method userIconMethod = classz.getDeclaredMethod("setUserIcon", String.class);
			userIconMethod.invoke(obj, userIcon);
			Method userNameMethod = classz.getDeclaredMethod("setUserName", String.class);
			userNameMethod.invoke(obj, userName);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public Integer getUserId(HttpSession session) {
		Object sessionObj = session.getAttribute(Constants.SESSION_USER_KEY);
		if (null != sessionObj) {
			SessionUser sessionUser = (SessionUser) sessionObj;
			return sessionUser.getUserId();
		}
		return null;
	}

	public SessionUser getSessionUser(HttpSession session) {
		Object sessionObj = session.getAttribute(Constants.SESSION_USER_KEY);
		if (null != sessionObj) {
			SessionUser sessionUser = (SessionUser) sessionObj;
			return sessionUser;
		}
		return null;
	}
}
