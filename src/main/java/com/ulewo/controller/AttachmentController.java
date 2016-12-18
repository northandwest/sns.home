/**
 * Project Name:ulewo-web
 * File Name:AttachmentController.java
 * Package Name:com.ulewo.controller
 * Date:2015年10月30日下午9:54:40
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
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
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.AttachmentDownload;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.service.AttachementService;
import com.ulewo.service.AttachmentDownloadService;
import com.ulewo.utils.Constants;
import com.ulewo.utils.ServerUtils;

/**
 * ClassName:AttachmentController <br/>
 * Date:     2015年10月30日 下午9:54:40 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
public class AttachmentController extends BaseController {

	@Resource
	private AttachementService attachementService;

	@Resource
	private AttachmentDownloadService attachmentDownloadService;

	Logger logger = LoggerFactory.getLogger(AttachmentController.class);

	@ResponseBody
	@RequestMapping(value = "checkDownload.action")
	public AjaxResponse<Boolean> checkDownload(HttpSession session, Integer attachmentId, Integer topicId) {
		AjaxResponse<Boolean> result = new AjaxResponse<Boolean>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			result.setData(Boolean.TRUE);
			attachementService.checkDownload(attachmentId, topicId, this.getSessionUser(session));
		} catch (BusinessException e) {
			logger.error("获取下载权限异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("获取下载权限异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}

	@RequestMapping(value = "downloadAttachment.action")
	public ModelAndView downloadAttachment(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Integer attachmentId) {
		ModelAndView view = new ModelAndView("redirect:" + Constants.ERROR_404);
		InputStream in = null;
		OutputStream out = null;
		try {
			Attachment attachment = attachementService.downloadAttachment(this.getSessionUser(session), attachmentId);
			String realPath = ServerUtils.getRealPath() + Constants.PATH_UPLOAD;
			String filePath = realPath + attachment.getFileUrl();
			File file = new File(filePath);
			in = new FileInputStream(file);
			out = response.getOutputStream();
			response.setContentType("application/x-msdownload; charset=UTF-8");
			String fileName = attachment.getFileName();
			// 解决中文文件名乱码问题
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1"); // firefox浏览器
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
			}
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
			byte[] byteData = new byte[1024 * 5];
			int len = 0;
			while ((len = in.read(byteData)) != -1) {
				out.write(byteData, 0, len); // write
			}
			out.flush();
			return null;
		} catch (BusinessException e) {
			logger.error("获取下载权限异常", e);
		} catch (Exception e) {
			logger.error("下载异常", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "findAttachmentDonwload")
	public AjaxResponse<List<AttachmentDownload>> findAttachmentDonwload(HttpSession session, Integer attachmentId) {
		AjaxResponse<List<AttachmentDownload>> result = new AjaxResponse<List<AttachmentDownload>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			List<AttachmentDownload> list = attachmentDownloadService.findAttachmentDonwload(attachmentId);
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取下载信息异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}
}
