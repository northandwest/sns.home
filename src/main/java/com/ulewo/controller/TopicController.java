/**
 * Project Name:ulewo-web
 * File Name:ExamController.java
 * Package Name:com.ulewo.controller
 * Date:2015年10月11日上午7:54:30
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.cache.CategoryCache;
import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.enums.TopicType;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.Category;
import com.ulewo.po.model.Topic;
import com.ulewo.po.model.TopicVote;
import com.ulewo.po.query.CategoryQuery;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.CategoryService;
import com.ulewo.service.TopicService;
import com.ulewo.service.TopicVoteService;
import com.ulewo.utils.Constants;
import com.ulewo.utils.DateUtil;

/**
 * ClassName:ExamController <br/>
 * Date:     2015年10月11日 上午7:54:30 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
@RequestMapping("/bbs")
public class TopicController extends BaseController {
	Logger logger = LoggerFactory.getLogger(TopicController.class);

	@Resource
	private CategoryService categoryService;

	@Resource
	private TopicService topicService;

	@Resource
	private TopicVoteService topicVoteService;

	@RequestMapping
	public ModelAndView bbs(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/bbs/topic");
		Date curDate = new Date();
		//获取当日的日期字符串
		String curDateStr = DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern());
		CategoryQuery query = new CategoryQuery();
		query.setStartDate(curDateStr);
		query.setEndDate(curDateStr);
		view.addObject("cateogries", categoryService.findCategory4TopicCount(query));

		//获取总帖子数
		view.addObject("count", topicService.findCount(null));

		//获取今日帖子数
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setStartDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		topicQuery.setEndDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("today", topicService.findCount(topicQuery));

		//获取昨日帖子数
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		topicQuery.setStartDate(DateUtil.format(c.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		topicQuery.setEndDate(DateUtil.format(c.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("yesterdayCount", topicService.findCount(topicQuery));

		//活跃用户
		view.addObject("activeUsers", topicService.findActiveUsers());
		return view;
	}

	/**
	 * 
	 * board:(板块). <br/>
	 *
	 * @author 不错啊
	 * @param pCategoryId
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/board/{pCategoryId}", method = RequestMethod.GET)
	public ModelAndView board(@PathVariable Integer pCategoryId, TopicQuery query) {
		ModelAndView view = new ModelAndView("/page/bbs/topic_list");
		//获取分类
		Category category = topicService.getPcategoryByPCategoryId(pCategoryId);
		if (null == category) {
			view.setViewName("redirect:" + Constants.ERROR_404);
			logger.error("分类没有找到");
			return view;
		}
		view.addObject("pCategory", category);
		//获取帖子
		PaginationResult<Topic> result = topicService.findTopicsByPage(query);
		view.addObject("result", result);
		//获取分类下的帖子数
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setpCategoryId(category.getCategoryId());
		view.addObject("count", topicService.findCount(topicQuery));
		//今日帖子数量
		Date curDate = new Date();
		topicQuery.setStartDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		topicQuery.setEndDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayCount", topicService.findCount(topicQuery));
		return view;
	}

	@RequestMapping(value = "/sub_board/{categoryId}", method = RequestMethod.GET)
	public ModelAndView sub_board(@PathVariable Integer categoryId, TopicQuery query) {
		ModelAndView view = new ModelAndView("/page/bbs/topic_list");

		Category category = topicService.getPcategoryByCategoryId(categoryId);
		if (null == category) {
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		view.addObject("pCategory", category);

		view.addObject("category", CategoryCache.getCategoryById(categoryId));
		//获取帖子
		PaginationResult<Topic> result = topicService.findTopicsByPage(query);
		view.addObject("result", result);
		//获取分类下的帖子输
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setpCategoryId(category.getCategoryId());
		view.addObject("count", topicService.findCount(topicQuery));
		//今日帖子数量
		Date curDate = new Date();
		topicQuery.setStartDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		topicQuery.setEndDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayCount", topicService.findCount(topicQuery));

		view.addObject("categoryId", categoryId);
		return view;
	}

	@RequestMapping(value = "post_topic.action")
	public ModelAndView post_topic(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/bbs/add_topic");
		view.addObject("topicType", TopicType.values());
		view.addObject("nextMonthToday", DateUtil.getNextMonthDay());
		return view;
	}

	@ResponseBody
	@RequestMapping("loadCategories")
	public AjaxResponse<List<Category>> loadCategories() {
		AjaxResponse<List<Category>> result = new AjaxResponse<List<Category>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			result.setData(CategoryCache.getBbsCategory());
		} catch (Exception e) {
			logger.error("获取分类信息异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}

	/**
	 * 
	 * addTopic:(新增主题). <br/>
	 *
	 * @author 不错啊
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "add_topic.action")
	public AjaxResponse<Integer> add_topic(HttpSession session, Topic topic, TopicVote vote, String[] voteTitle,
			Attachment attachment, String richContent) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			topic.setContent(richContent);
			this.setUserBaseInfo(Topic.class, topic, session);
			topicService.addTopic(topic, vote, voteTitle, attachment);
			result.setData(topic.getTopicId());
		} catch (BusinessException e) {
			logger.error("发帖异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("发帖异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}

	@RequestMapping(value = "/{topicId}", method = RequestMethod.GET)
	public ModelAndView showTopic(@PathVariable Integer topicId) {
		ModelAndView mv = new ModelAndView();
		try {
			Topic topic = topicService.showTopic(topicId);
			mv.addObject("topic", topic);
			mv.setViewName("/page/bbs/topic_detail");
		} catch (Exception e) {
			logger.error("帖子详情{" + topicId + "}:" + e.getMessage(), e);
			mv.setViewName("redirect:" + Constants.ERROR_404);
			return mv;
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "loadVote")
	public AjaxResponse<TopicVote> loadVote(HttpSession session, Integer topicId) {
		AjaxResponse<TopicVote> result = new AjaxResponse<TopicVote>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			TopicVote topicVote = topicVoteService.getTopicVote(topicId, this.getUserId(session));
			result.setData(topicVote);
		} catch (Exception e) {
			logger.error("获取投票异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("获取投票异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "doVote")
	public AjaxResponse<TopicVote> doVote(HttpSession session, Integer voteId, Integer voteType, Integer[] voteDtlId,
			Integer topicId) {
		AjaxResponse<TopicVote> result = new AjaxResponse<TopicVote>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			TopicVote topicVote = topicVoteService
					.doVote(voteId, voteType, voteDtlId, this.getUserId(session), topicId);
			result.setData(topicVote);
		} catch (BusinessException e) {
			logger.error("投票异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("投票异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("投票异常");
		}
		return result;
	}
}
