/**
 * Project Name:ulewo-web
 * File Name:SpitSlotController.java
 * Package Name:com.ulewo.controller
 * Date:2015年9月27日下午6:18:59
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Calendar4Signin;
import com.ulewo.po.model.SignIn;
import com.ulewo.po.model.SignInInfo;
import com.ulewo.po.query.SignInQuery;
import com.ulewo.po.query.SpitSlotQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.SignInService;
import com.ulewo.utils.DateUtil;

/**
 * ClassName:SpitSlotController <br/>
 * Date:     2015年9月27日 下午6:18:59 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
public class SignInController extends BaseController {

	Logger logger = LoggerFactory.getLogger(SignInController.class);

	@Resource
	private SignInService signInService;

	@RequestMapping(value = "signIn")
	public ModelAndView spitSlot(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/sign_in");
		SignInInfo signInInfo = signInService.findSignInInfoByUserId(this.getUserId(session));
		view.addObject("signInInfo", signInInfo);
		return view;
	}

	/**
	 * 
	 * doSignIn:(签到)
	 * @author luohaili
	 * @param session
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "doSignIn.action")
	public AjaxResponse<SignIn> doSignIn(HttpSession session, SpitSlotQuery query) {
		AjaxResponse<SignIn> result = new AjaxResponse<SignIn>();
		try {
			SignIn signIn = signInService.doSignIn(this.getSessionUser(session));
			result.setData(signIn);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("签到异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("签到异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("签到异常");
		}
		return result;
	}

	/**
	 * loadCurDaySigin:(获取今日签到信息)
	 * @author luohaili
	 * @param session
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "loadCurDaySigin")
	public AjaxResponse<PaginationResult<SignIn>> loadCurDaySigin(HttpSession session, SignInQuery query) {
		AjaxResponse<PaginationResult<SignIn>> result = new AjaxResponse<PaginationResult<SignIn>>();
		try {
			PaginationResult<SignIn> data = signInService.findCurDaySigin(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("获取今日签到异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("获取今日签到异常");
		}
		return result;
	}

	/**
	 * 
	 * loadUserSigin:(获取用户签到信息)
	 * @author luohaili
	 * @param session
	 * @param year
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "loadUserSigin.action")
	public AjaxResponse<Map<String, Object>> loadUserSigin(HttpSession session, Integer year) {
		AjaxResponse<Map<String, Object>> result = new AjaxResponse<Map<String, Object>>();
		try {
			Map<String, Object> resultData = new HashMap<String, Object>();
			List<Calendar4Signin> data = signInService.findUserSiginsByYear(this.getUserId(session), year);
			resultData.put("list", data);
			resultData.put("curYear", DateUtil.format(new Date(), DateTimePatternEnum.YYYY.getPattern()));
			resultData.put("year", year);
			result.setData(resultData);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("获取用户签到信息异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("获取用户签到信息异常");
		}
		return result;
	}
}
