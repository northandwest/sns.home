/**
 * Project Name:ulewo-web
 * File Name:SpitSlotController.java
 * Package Name:com.ulewo.controller
 * Date:2015年9月27日下午6:18:59
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
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.SpitSlot;
import com.ulewo.po.model.SpitSlotComment;
import com.ulewo.po.query.SpitSlotQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.SpitSlotService;

/**
 * ClassName:SpitSlotController <br/>
 * Date:     2015年9月27日 下午6:18:59 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
public class SpitSlotController extends BaseController {

	Logger logger = LoggerFactory.getLogger(SpitSlotController.class);

	@Resource
	private SpitSlotService spitSlotService;

	@RequestMapping(value = "spitslot")
	public ModelAndView spitSlot() {
		ModelAndView view = new ModelAndView("/page/spitslot");
		List<SpitSlot> list = spitSlotService.findActiveUser4SpitSlot();
		view.addObject("list", list);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "loadSpitSlot")
	public AjaxResponse<PaginationResult<SpitSlot>> loadSpitSlot(SpitSlotQuery query) {
		AjaxResponse<PaginationResult<SpitSlot>> result = new AjaxResponse<PaginationResult<SpitSlot>>();
		try {
			PaginationResult<SpitSlot> data = spitSlotService.findSpitSlotList(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载吐槽异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载吐槽异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "addSpitSlot.action")
	public AjaxResponse<SpitSlot> addSpitSlot(HttpSession session, SpitSlot slot) {
		AjaxResponse<SpitSlot> result = new AjaxResponse<SpitSlot>();
		try {
			this.setUserBaseInfo(SpitSlot.class, slot, session);
			spitSlotService.addSpitSlot(slot);
			result.setData(slot);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("发表吐槽异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("发表吐槽异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("发表吐槽异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "addSpitSlotComment.action")
	public AjaxResponse<SpitSlotComment> addSpitSlotComment(HttpSession session, SpitSlotComment comment) {
		AjaxResponse<SpitSlotComment> result = new AjaxResponse<SpitSlotComment>();
		try {
			this.setUserBaseInfo(SpitSlotComment.class, comment, session);
			spitSlotService.addSpitSlotComment(comment);
			result.setData(comment);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("发表吐槽评论异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("发表吐槽评论异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("发表吐槽评论异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "loadSpitSlotComment")
	public AjaxResponse<List<SpitSlotComment>> loadSpitSlotComment(Integer spitSlotId) {
		AjaxResponse<List<SpitSlotComment>> result = new AjaxResponse<List<SpitSlotComment>>();
		try {
			List<SpitSlotComment> data = spitSlotService.loadSpitSlotComment(spitSlotId);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载吐槽评论异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载吐槽评论异常");
		}
		return result;
	}
}
