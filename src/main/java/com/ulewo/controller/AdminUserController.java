/**
 * Project Name:ulewo-web
 * File Name:CommentController.java
 * Package Name:com.ulewo.controller
 * Date:2015年11月2日下午9:30:38
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bucuoa.ucenter.model.UlewoUser;
import com.ulewo.exception.BusinessException;
import com.ulewo.po.config.ConfigInfo;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.service.BlogCategoryService;
import com.ulewo.service.BlogService;
import com.ulewo.service.UserService;
import com.ulewo.utils.Constants;
import com.ulewo.utils.ServerUtils;
import com.ulewo.utils.StringTools;

//import bucuoa.upload.sftp.SFTPConstants;
//import bucuoa.upload.sftp.SFTPUtils;

@Controller
@RequestMapping("/admin")
public class AdminUserController extends BaseController {

	Logger logger = LoggerFactory.getLogger(AdminUserController.class);

	@Resource
	private UserService userService;

	@Resource
	private BlogCategoryService blogCategoryService;

	@Resource
	private BlogService blogService;
	@Resource
	private ConfigInfo configInfo;
	
	@RequestMapping(value = "update_user.action")
	public ModelAndView user_update(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/admin/user_update");
		view.addObject("user", userService.findUserByUserId(this.getUserId(session).toString()));
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "save_user.action")
	public AjaxResponse<?> save_user(HttpSession session, UlewoUser user) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			user.setUserId(this.getUserId(session).longValue());
			userService.updateInfo(user);
		} catch (BusinessException e) {
			logger.error("更新用户信息异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("更新用户信息异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	@RequestMapping(value = "change_pwd.action")
	public ModelAndView change_pwd(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/admin/user_change_pwd");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "save_pwd.action")
	public AjaxResponse<?> save_pwd(HttpSession session, String oldPwd, String newPwd) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			userService.changePwd(this.getUserId(session), oldPwd, newPwd);
		} catch (BusinessException e) {
			logger.error("修改密码异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("修改密码异常", e);
		}
		return result;
	}

	@RequestMapping(value = "change_user_icon.action")
	public ModelAndView change_user_icon(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/admin/user_change_icon");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/save_user_icon.action")
	public AjaxResponse<?> save_user_icon(HttpSession session, HttpServletRequest request, Integer x1, Integer y1,
			Integer width, Integer height, String img) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		if (StringTools.isEmpty(img)) {
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg("请求参数错误");
			return result;
		}
		Integer userId = this.getUserId(session);
		InputStream tempIn = null;
//		ByteArrayOutputStream out = null;
//		OutputStream imgOut = null;
		String imgType = Constants.IMAGE_SUFFIX_JPG;
		String srcpath = ServerUtils.getRealPath() + "/" + img;
		File file = new File(srcpath);
		try {
			BufferedImage image = ImageIO.read(file);
			// 裁剪图片
			BufferedImage subimg = image.getSubimage(x1, y1, width, height);
			// 将图片转为字节数组
			String okSrcPath = ServerUtils.getRealPath() + Constants.PATH_AVATARS;
			File imagePathFile = new File(okSrcPath);
			if (!imagePathFile.exists()) {
				imagePathFile.mkdirs();
			}
			String userIconName = userId + imgType;
			String pathname = okSrcPath + userIconName;
			File okfile = new File(pathname);
			ImageIO.write(subimg, "JPEG", okfile);
			
			Map<String, String> sftpDetails = new HashMap<String, String>();
			// 设置主机ip，端口，用户名，密码
			
//			sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, configInfo.getSshdUrl());
//			sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, configInfo.getSshdUser());
//			sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, configInfo.getSshdPassword());
//			sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, configInfo.getSshdPort());
//			
////			String _sourceroot = "D:/logs/abcd1.txt";
//			String _targetroot = "/opt/webroot/static.bucuoa.com/upload/avatars/"+userIconName;
//			
//			SFTPUtils.upload(pathname, _targetroot, sftpDetails);
			
			return result;
		} catch (Exception e) {
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("系统异常");
			return result;
		} finally {
			try {
				if (null != tempIn) {
					tempIn.close();
					tempIn = null;
				}
//				if (null != out) {
//					out.close();
//					out = null;
//				}
//				if (imgOut != null) {
//					imgOut.close();
//				}
			} catch (Exception e) {

			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/save_sys_user_icon.action")
	public AjaxResponse<?> save_sys_user_icon(HttpSession session, HttpServletRequest request, String userIcon) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			Integer userId = this.getUserId(session);
			UlewoUser user = new UlewoUser();
			user.setUserId(userId.longValue());
			String targetIcon = userId + Constants.IMAGE_SUFFIX_JPG;
//			userService.copyUserIcon(userIcon, targetIcon);
			user.setUserIcon(Constants.PATH_AVATARS_SUFFIX + targetIcon);
			userService.updateInfoIcon(user);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/user_center_background.action")
	public ModelAndView user_center_background(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {
			UlewoUser user = userService.findUserByUserId(this.getUserId(session).toString());
			mv.addObject("background", user.getUserBg());
			mv.setViewName("/page/admin/user_center_background");
		} catch (Exception e) {
			logger.error(e.getMessage());
			mv.setViewName("redirect:" + Constants.ERROR_404);
			return mv;
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/save_user_center_background.action")
	public AjaxResponse<?> save_user_center_background(HttpSession session, HttpServletRequest request,
			String background) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			if (StringUtils.isEmpty(background)) {
				result.setResponseCode(ResponseCode.BUSINESSERROR);
				result.setErrorMsg("参数错误");
				return result;
			}
			Integer userId = this.getUserId(session);
			UlewoUser user = new UlewoUser();
			user.setUserId(userId.longValue());
			user.setUserBg(background);
			userService.updateInfoBg(user);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}
}
