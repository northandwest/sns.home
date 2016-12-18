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
import com.ulewo.po.model.SpitSlot;
import com.ulewo.po.model.SpitSlotComment;
import com.ulewo.po.query.SpitSlotQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.SpitSlotService;

/**
 * ClassName:ManagerSpitSlotController <br/>
 * Date:     2015年12月10日 下午9:38:43 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
@RequestMapping("/manage/spitsplot")
public class ManageSpitSlotController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ManageSpitSlotController.class);

	@Resource
	private SpitSlotService spitSlotService;

	@RequestMapping(value = "spit_slot_list")
	public ModelAndView spit_slot_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/manage/spit_slot_list");
		return view;
	}

	/**
	 * 
	 * load_spit_slot:(加载吐槽). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "load_spit_slot")
	public AjaxResponse<PaginationResult<SpitSlot>> load_spit_slot(SpitSlotQuery query) {
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

	@RequestMapping(value = "spit_slot_comment_list")
	public ModelAndView blog_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/manage/spit_slot_comment_list");
		return view;
	}

	/**
	 * load_spit_slot_comment_list:(加载评论). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "load_spit_slot_comment_list")
	public AjaxResponse<PaginationResult<SpitSlotComment>> load_spit_slot_comment_list(SpitSlotQuery query) {
		AjaxResponse<PaginationResult<SpitSlotComment>> result = new AjaxResponse<PaginationResult<SpitSlotComment>>();
		try {
			PaginationResult<SpitSlotComment> data = spitSlotService.findSpitSlotCommentList(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载吐槽评论异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载吐槽评论异常");
		}
		return result;
	}

	/**
	 * 
	 * delete_spit_slot:(删除吐槽). <br/>
	 *
	 * @author 不错啊
	 * @param spitSlotId
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "delete_spit_slot")
	public AjaxResponse<?> delete_spit_slot(Integer spitSlotId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			spitSlotService.deleteSpitSlot(spitSlotId);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除吐槽异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除吐槽异常");
		}
		return result;
	}

	/**
	 * 
	 * delete_spit_slot_comment:(删除吐槽评论异常). <br/>
	 *
	 * @author 不错啊
	 * @param spitSlotId
	 * @param commentId
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "delete_spit_slot_comment")
	public AjaxResponse<?> delete_spit_slot_comment(Integer spitSlotId, Integer commentId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			spitSlotService.deleteSpitSlotComment(spitSlotId, commentId);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除吐槽评论异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除吐槽评论异常");
		}
		return result;
	}
}
