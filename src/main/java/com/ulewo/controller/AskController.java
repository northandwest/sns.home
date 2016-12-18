/**
 * Project Name:ulewo-web
 * File Name:CommentController.java
 * Package Name:com.ulewo.controller
 * Date:2015年11月2日下午9:30:38
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

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
import com.ulewo.po.enums.SloveTypeEnum;
import com.ulewo.po.model.Ask;
import com.ulewo.po.model.Category;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.AskService;
import com.ulewo.service.CategoryService;
import com.ulewo.service.UserService;
import com.ulewo.utils.Constants;
import com.ulewo.utils.DateUtil;

@Controller
@RequestMapping("/ask")
public class AskController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(AskController.class);

	@Resource
	private AskService askService;

	@Resource
	private UserService UserService;

	@Resource
	private CategoryService categoryService;

	@RequestMapping
	public ModelAndView ask(HttpSession session, AskQuery query) {
		if (query.getSloveType() == null) {
			query.setSloveType(SloveTypeEnum.NOTSLOVE);
		}
		ModelAndView view = new ModelAndView("/page/ask/ask");
		PaginationResult<Ask> result = askService.findAskByPage(query);
		view.addObject("result", result);
		view.addObject("haveSolve", query.getSloveType().getType());

		Date curDate = new Date();
		//获取今日问题数
		AskQuery askQuery = new AskQuery();
		askQuery.setStartDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		askQuery.setEndDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayCount", askService.findCount(askQuery));
		//今日解决数
		askQuery.setSloveType(SloveTypeEnum.SLOVED);
		view.addObject("todaySloveCount", askService.findCount(askQuery));

		//总问答数
		askQuery = new AskQuery();
		view.addObject("totalCount", askService.findCount(askQuery));
		//总共解决问答数
		askQuery.setSloveType(SloveTypeEnum.SLOVED);
		view.addObject("totalSloveCount", askService.findCount(askQuery));

		//琅琊榜
		view.addObject("topUsers", askService.selectTopUsers());
		return view;
	}

	@RequestMapping(value = "post_ask.action")
	public ModelAndView post_ask(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/ask/add_ask");
		view.addObject("leftMark", UserService.findUserByUserId(this.getUserId(session).toString()).getMark());
		return view;
	}

	@ResponseBody
	@RequestMapping("loadCategories")
	public AjaxResponse<List<Category>> loadCategories() {
		AjaxResponse<List<Category>> result = new AjaxResponse<List<Category>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			result.setData(CategoryCache.getAskCategory());
		} catch (Exception e) {
			logger.error("获取分类信息异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "add_ask.action")
	public AjaxResponse<Integer> add_ask(HttpSession session, Ask ask, String richContent) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			ask.setContent(richContent);
			this.setUserBaseInfo(Ask.class, ask, session);
			askService.addAsk(ask);
			result.setData(ask.getAskId());
		} catch (BusinessException e) {
			logger.error("提问异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("提问异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}

	@RequestMapping(value = "/{askId}", method = RequestMethod.GET)
	public ModelAndView showAsk(@PathVariable Integer askId) {
		ModelAndView mv = new ModelAndView();
		try {
			Ask ask = askService.showAsk(askId);
			mv.addObject("topic", ask);
			mv.setViewName("/page/ask/ask_detail");
		} catch (Exception e) {
			logger.error("问答详情{" + askId + "}:" + e.getMessage(), e);
			mv.setViewName("redirect:" + Constants.ERROR_404);
			return mv;
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "set_best_answer.action")
	public AjaxResponse<Integer> set_best_answer(HttpSession session, Integer bestAnswerId, Integer askId) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			askService.setBestAnswer(bestAnswerId, askId, this.getUserId(session));
		} catch (BusinessException e) {
			logger.error("设置最佳答案异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("设置最佳答案异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}
}
