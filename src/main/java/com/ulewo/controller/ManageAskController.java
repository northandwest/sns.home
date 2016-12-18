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
import com.ulewo.po.model.Ask;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.AskService;

/**
 * 
 * ClassName: ManagerKnowledgeController
 * date: 2015年12月13日 下午9:08:51 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/manage/ask")
public class ManageAskController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ManageAskController.class);

	@Resource
	private AskService askService;

	@RequestMapping(value = "ask_list")
	public ModelAndView ask_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/manage/ask_list");
		return view;
	}

	/**
	 * 
	 * load_topic:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "load_ask")
	public AjaxResponse<PaginationResult<Ask>> load_topic(AskQuery query) {
		AjaxResponse<PaginationResult<Ask>> result = new AjaxResponse<PaginationResult<Ask>>();
		try {
			PaginationResult<Ask> data = askService.findAskByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载问答异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载问答异常");
		}
		return result;
	}

	/**
	 * 
	 * delete_ask:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param ids
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "delete_ask")
	public AjaxResponse<?> delete_ask(Integer[] ids) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			askService.deleteBatch(ids);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除帖子问答", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除问答异常");
		}
		return result;
	}
}
