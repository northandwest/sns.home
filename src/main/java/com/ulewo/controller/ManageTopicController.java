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
import com.ulewo.po.model.Topic;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.TopicService;

/**
 * 
 * ClassName: ManagerKnowledgeController
 * date: 2015年12月13日 下午9:08:51 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/manage/topic")
public class ManageTopicController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ManageTopicController.class);

	@Resource
	private TopicService topicService;

	@RequestMapping(value = "topic_list")
	public ModelAndView topic_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/manage/topic_list");
		return view;
	}

	/**
	 * 
	 * load_topic:(加载主题)
	 * @author luohaili
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "load_topic")
	public AjaxResponse<PaginationResult<Topic>> load_topic(TopicQuery query) {
		AjaxResponse<PaginationResult<Topic>> result = new AjaxResponse<PaginationResult<Topic>>();
		try {
			PaginationResult<Topic> data = topicService.findTopicsByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载帖子异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载帖子异常");
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
	@RequestMapping(value = "delete_topic")
	public AjaxResponse<?> delete_topic(Integer[] ids) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			topicService.deleteTopic(ids);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除帖子异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除帖子异常");
		}
		return result;
	}

	/**
	 * 
	 * change_category:(修改帖子分类)
	 * @author luohaili
	 * @param topicId
	 * @param pCategoryId
	 * @param categoryId
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "change_category")
	public AjaxResponse<?> change_category(Integer topicId, Integer pCategoryId, Integer categoryId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			topicService.changeCategory(topicId, pCategoryId, categoryId);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("修改帖子分类异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("修改帖子分类异常");
		}
		return result;
	}

	/**
	 * 
	 * set_top:(置顶)
	 * @author luohaili
	 * @param topicId
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "set_top")
	public AjaxResponse<?> set_top(Integer topicId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			topicService.setTop(topicId);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("设置置顶异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("设置置顶异常");
		}
		return result;
	}

	/**
	 * 
	 * cancel_top:(取消置顶)
	 * @author luohaili
	 * @param topicId
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "cancel_top")
	public AjaxResponse<?> cancel_top(Integer topicId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			topicService.cancelTop(topicId);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("取消置顶异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("取消置顶异常");
		}
		return result;
	}

	/**
	 * 
	 * set_essence:(设置精华)
	 * @author luohaili
	 * @param topicId
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "set_essence")
	public AjaxResponse<?> set_essence(Integer topicId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			topicService.setEssence(topicId);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("设置置顶异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("设置置顶异常");
		}
		return result;
	}

	/**
	 * 
	 * cancel_essence:(取消精华)
	 * @author luohaili
	 * @param topicId
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "cancel_essence")
	public AjaxResponse<?> cancel_essence(Integer topicId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			topicService.cancelEssence(topicId);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("取消置顶异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("取消置顶异常");
		}
		return result;
	}
}
