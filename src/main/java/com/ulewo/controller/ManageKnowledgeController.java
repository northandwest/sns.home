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
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.KnowledgeService;

/**
 * 
 * ClassName: ManagerKnowledgeController
 * date: 2015年12月13日 下午9:08:51 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/manage/knowledge")
public class ManageKnowledgeController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ManageKnowledgeController.class);

	@Resource
	private KnowledgeService knowledgeService;

	@RequestMapping(value = "knowledge_list")
	public ModelAndView knowledge_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/manage/knowledge_list");
		return view;
	}

	/**
	 * 
	 * load_exam:(加载考题). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "load_knowledge")
	public AjaxResponse<PaginationResult<Knowledge>> load_knowledge(KnowledgeQuery query) {
		AjaxResponse<PaginationResult<Knowledge>> result = new AjaxResponse<PaginationResult<Knowledge>>();
		try {
			PaginationResult<Knowledge> data = knowledgeService.findKnowledgesByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载知识库异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载知识库异常");
		}
		return result;
	}

	/**
	 * 
	 * audit_knowledge:(审核知识库). <br/>
	 *
	 * @author 不错啊
	 * @param ids
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "audit_knowledge")
	public AjaxResponse<?> audit_knowledge(Integer[] ids) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			knowledgeService.auditKnowledge(ids);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("审核知识库异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("审核知识库异常");
		}
		return result;
	}

	/**
	 * 
	 * delete_knowledge:(删除知识库). <br/>
	 *
	 * @author 不错啊
	 * @param ids
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "delete_knowledge")
	public AjaxResponse<?> delete_knowledge(Integer[] ids) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			knowledgeService.deleteKnowledge(ids);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除考题异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除考题异常");
		}
		return result;
	}
}
