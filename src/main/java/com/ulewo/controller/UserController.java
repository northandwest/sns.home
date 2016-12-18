/**
 * Project Name:ulewo-web
 * File Name:UserController.java
 * Package Name:com.ulewo.controller
 * Date:2015年9月20日上午11:17:18
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.SessionUser;
import com.ulewo.po.model.User;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.service.UserService;
import com.ulewo.utils.Constants;
import com.ulewo.utils.StringTools;

/**
 * ClassName:UserController <br/>
 * Date:     2015年9月20日 上午11:17:18 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
public class UserController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

//	@Resource
//	private UserService userService;

//	@RequestMapping(value = "register")
//	public ModelAndView register(HttpServletRequest request) {
//		ModelAndView view = new ModelAndView("/page/register");
//		return view;
//	}

	/**
	 * 
	 * registerDo:(注册). <br/>
	 *
	 * @author 不错啊
	 * @param session
	 * @param user
	 * @param checkCode
	 * @return
	 * @since JDK 1.7
	 */
/*	@ResponseBody
	@RequestMapping(value = "register.do")
	public AjaxResponse<Object> registerDo(HttpSession session, User user, String checkCode) {
		AjaxResponse<Object> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			String sessionCheckCode = session.getAttribute(Constants.CHECK_CODE_KEY).toString();
			if (!sessionCheckCode.equalsIgnoreCase(checkCode)) {
				result.setErrorMsg("验证码错误");
				result.setResponseCode(ResponseCode.CODEERROR);
			} else {
				userService.restister(user);
				SessionUser sessionUser = new SessionUser();
				sessionUser.setUserId(user.getUserId());
				sessionUser.setUserIcon(user.getUserIcon());
				sessionUser.setUserName(user.getUserName());
				session.setAttribute(Constants.SESSION_USER_KEY, sessionUser);
				result.setData(user.getUserId());
			}
		} catch (BusinessException e) {
			result.setErrorMsg(e.getMessage());
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			logger.error("注册用户失败,用户名{},邮箱：{}", user.getUserName(), user.getEmail());
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("注册用户失败,用户名{},邮箱：{}", user.getUserName(), user.getEmail());
		}
		return result;
	}
*/
/*	@RequestMapping(value = "login")
	public ModelAndView login(HttpServletRequest request, HttpSession session) {
		ModelAndView view = new ModelAndView("/page/login");
		if (null != session.getAttribute(Constants.SESSION_ERROR_LOGIN_COUNT)
				&& (Integer) session.getAttribute(Constants.SESSION_ERROR_LOGIN_COUNT) >= Constants.MAX_LOGIN_ERROR_COUNT) {
			view.addObject("checkCode", "checkCode");
		}
		return view;
	}*/

	/*@ResponseBody
	@RequestMapping(value = "login.do")
	public AjaxResponse<Object> loginDo(HttpSession session, HttpServletResponse response, String account,
			String password, String rememberMe, String checkCode) {
		final String REMEMBERME = "1";
		String jsessionId = session.getId();
		logger.info("请求的sessionId:{}", jsessionId);
		AjaxResponse<Object> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			String sessionCheckCode = String.valueOf(session.getAttribute(Constants.CHECK_CODE_KEY));
			if ((!StringTools.isEmpty(sessionCheckCode) && !sessionCheckCode.equalsIgnoreCase(checkCode))
					&& null != session.getAttribute(Constants.SESSION_ERROR_LOGIN_COUNT)
					&& (Integer) session.getAttribute(Constants.SESSION_ERROR_LOGIN_COUNT) >= Constants.MAX_LOGIN_ERROR_COUNT) {
				result.setErrorMsg("验证码错误");
				result.setResponseCode(ResponseCode.CODEERROR);
				return result;
			}
			User user = userService.login(account, password, true);
			SessionUser sessionUser = new SessionUser();
			sessionUser.setUserId(user.getUserId());
			sessionUser.setUserIcon(user.getUserIcon());
			sessionUser.setUserName(user.getUserName());
			session.setAttribute(Constants.SESSION_USER_KEY, sessionUser);
			//记住登陆状态
			if (REMEMBERME.equals(rememberMe)) {
				// 自动登录，保存用户名密码到 Cookie
				String infor = URLEncoder.encode(account.toString(), "utf-8") + "|" + user.getPassword();
				// 清除之前的Cookie 信息
				Cookie cookie = new Cookie(Constants.COOKIE_USER_INFO, null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				// 建用户信息保存到Cookie中
				cookie = new Cookie(Constants.COOKIE_USER_INFO, infor);
				cookie.setPath("/");
				// 设置最大生命周期为1年。
				cookie.setMaxAge(31536000);
				response.addCookie(cookie);
			} else {
				Cookie cookie = new Cookie(Constants.COOKIE_USER_INFO, null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		} catch (BusinessException e) {
			if (null == session.getAttribute(Constants.SESSION_ERROR_LOGIN_COUNT)) {
				session.setAttribute(Constants.SESSION_ERROR_LOGIN_COUNT, 1);
			} else {
				session.setAttribute(Constants.SESSION_ERROR_LOGIN_COUNT,
						(Integer) session.getAttribute(Constants.SESSION_ERROR_LOGIN_COUNT) + 1);
			}
			if ((Integer) session.getAttribute(Constants.SESSION_ERROR_LOGIN_COUNT) >= Constants.MAX_LOGIN_ERROR_COUNT) {
				result.setResponseCode(ResponseCode.MOREMAXLOGINCOUNT);
			} else {
				result.setResponseCode(ResponseCode.BUSINESSERROR);
			}
			result.setErrorMsg(e.getMessage());
			logger.error("登陆失败，账号：{}", account, e);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("登陆失败，账号：{}", account, e);
		}
		return result;
	}*/

	@ResponseBody
	@RequestMapping(value = "/logout")
	public AjaxResponse<Object> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		AjaxResponse<Object> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		Cookie cookie = new Cookie(Constants.COOKIE_USER_INFO, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		cookie.setDomain(".bucuoa.com");
		response.addCookie(cookie);
		session.invalidate();
		return result;
	}

//	@RequestMapping(value = "findpwd")
//	public ModelAndView findpwd(HttpServletRequest request, HttpSession session) {
//		ModelAndView view = new ModelAndView("/page/findpwd");
//		return view;
//	}

/*	@ResponseBody
	@RequestMapping(value = "sendCheckCode.do")
	public AjaxResponse<Object> sendCheckCode(HttpSession session, HttpServletResponse response, String email,
			String checkCode) {
		AjaxResponse<Object> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			String sessionCheckCode = session.getAttribute(Constants.CHECK_CODE_KEY).toString();
			if (!sessionCheckCode.equalsIgnoreCase(checkCode)) {
				result.setErrorMsg("验证码错误");
				result.setResponseCode(ResponseCode.CODEERROR);
			} else {
				this.userService.sendCheckCode(email);
			}
			this.userService.sendCheckCode(email);
		} catch (BusinessException e) {
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
			logger.error("发送验证码失败，邮箱：{}", email, e);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("发送验证码失败，邮箱：{}", email, e);
		}
		return result;
	}*/

	/*@ResponseBody
	@RequestMapping(value = "resetpwd.do")
	public AjaxResponse<Object> resetpwd(HttpSession session, HttpServletResponse response, String email,
			String checkCode, String password) {
		AjaxResponse<Object> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			this.userService.resetPwd(email, password, checkCode);
		} catch (BusinessException e) {
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
			logger.error("重置密码错误，邮箱：{},checkCode:{}", email, checkCode);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("重置密码错误，邮箱：{},checkCode:{}", email, checkCode);
		}
		return result;
	}*/

	/*
	 * 生成验证码
	 */
//	@RequestMapping(value = "checkCode")
//	public void checkCode(HttpServletRequest request, HttpServletResponse response, HttpSession session)
//			throws IOException {
//		ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
//		//设置验证码颜色
//		cs.setColorFactory(new SingleColorFactory(new Color(234, 0, 0)));
//		cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
//		RandomFontFactory font = new RandomFontFactory();
//		font.setMaxSize(25);
//		font.setMinSize(25);
//		cs.setFontFactory(font);
//		//设置宽度
//		cs.setWidth(180);
//		//设置高度
//		cs.setHeight(40);
//		//设置验证码
//		cs.setWordFactory(new WordFactory() {
//
//			@Override
//			public String getNextWord() {
//				return getCode();
//			}
//		});
//		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Cache-Control", "no-cache");
//		response.setDateHeader("Expires", 0);
//		response.setContentType("image/jpeg");
/*		String code = "bucuoa";//EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
		session.setAttribute(Constants.CHECK_CODE_KEY, code);
	}*/

	private static String getCode() {
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer randomCode = new StringBuffer();
		// 随机产生codeCount数字的验证码。         
		Random random = new Random();
		int codeCount = 4;
		int codeSequenceLength = codeSequence.length;
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。         
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequenceLength)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}
}
