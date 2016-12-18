/**
 * Project Name:ulewo-web
 * File Name:CommentController.java
 * Package Name:com.ulewo.controller
 * Date:2015年11月2日下午9:30:38
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.cache.CategoryCache;
import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.enums.StatusEnum;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.Category;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.CategoryService;
import com.ulewo.service.KnowledgeService;
import com.ulewo.utils.Constants;

@Controller
@RequestMapping("/knowledge")
public class KnowledgeController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(KnowledgeController.class);

	@Resource
	private KnowledgeService knowledgeService;

	@Resource
	private CategoryService categoryService;

	@RequestMapping
	public ModelAndView knowledge(HttpSession session, KnowledgeQuery query) {
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge");
		query.setStatus(StatusEnum.AUDIT);
		PaginationResult<Knowledge> result = knowledgeService.findKnowledgesByPage(query);
		view.addObject("result", result);
		view.addObject("categories", CategoryCache.getKnowledgeCategory());
		return view;
	}

	@RequestMapping(value = "/pCategoryId/{pCategoryId}", method = RequestMethod.GET)
	public ModelAndView knowledgePyPcategory(HttpSession session, @PathVariable Integer pCategoryId,
			KnowledgeQuery query) {
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge");
		query.setStatus(StatusEnum.AUDIT);
		PaginationResult<Knowledge> result = knowledgeService.findKnowledgesByPage(query);
		view.addObject("result", result);
		view.addObject("categories", CategoryCache.getKnowledgeCategory());
		view.addObject("pCategory", CategoryCache.getCategoryById(pCategoryId));
		return view;
	}

	@RequestMapping(value = "/categoryId/{categoryId}", method = RequestMethod.GET)
	public ModelAndView knowledgePycategory(HttpSession session, @PathVariable Integer categoryId, KnowledgeQuery query) {
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge");
		query.setStatus(StatusEnum.AUDIT);
		PaginationResult<Knowledge> result = knowledgeService.findKnowledgesByPage(query);
		view.addObject("result", result);
		view.addObject("categories", CategoryCache.getKnowledgeCategory());
		Category category = CategoryCache.getCategoryById(categoryId);
		if (category != null) {
			view.addObject("pCategory", CategoryCache.getCategoryById(category.getPid()));
		}
		view.addObject("category", category);
		view.addObject("categoryId", categoryId);
		return view;
	}

	@RequestMapping(value = "post_knowledge.action")
	public ModelAndView post_knowledge(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/knowledge/add_knowledge");
		return view;
	}

	@ResponseBody
	@RequestMapping("loadCategories")
	public AjaxResponse<List<Category>> loadCategories() {
		AjaxResponse<List<Category>> result = new AjaxResponse<List<Category>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			result.setData(CategoryCache.getKnowledgeCategory());
		} catch (Exception e) {
			logger.error("获取分类信息异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "add_knowledge.action")
	public AjaxResponse<Integer> add_knowledge(HttpSession session, Knowledge knowledge, Attachment attachment,
			String richContent) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			knowledge.setContent(richContent);
			this.setUserBaseInfo(Knowledge.class, knowledge, session);
			knowledgeService.addKnowledge(knowledge, attachment);
			result.setData(knowledge.getTopicId());
		} catch (BusinessException e) {
			logger.error("发布知识异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("发布知识异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}

	@RequestMapping(value = "/{knowledgeId}", method = RequestMethod.GET)
	public ModelAndView showKnowledge(@PathVariable Integer knowledgeId, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {
			Knowledge knowledge = knowledgeService.showKnowledge(knowledgeId, this.getUserId(session));
			mv.addObject("categories", CategoryCache.getKnowledgeCategory());
			Category category = CategoryCache.getCategoryById(knowledge.getCategoryId());
			if (category != null) {
				mv.addObject("pCategory", CategoryCache.getCategoryById(category.getPid()));
			}
			mv.addObject("category", category);
			mv.addObject("categoryId", knowledge.getCategoryId());
			mv.addObject("topic", knowledge);
			mv.setViewName("/page/knowledge/knowledge_detail");
		} catch (Exception e) {
			logger.error("知识详情{" + knowledgeId + "}:" + e.getMessage(), e);
			mv.setViewName("redirect:" + Constants.ERROR_404);
			return mv;
		}
		return mv;
	}
}
