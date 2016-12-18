/**
 * Project Name:ulewo-web
 * File Name:ExamController.java
 * Package Name:com.ulewo.controller
 * Date:2015年10月11日上午7:54:30
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

import com.ulewo.cache.CategoryCache;
import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ExamChooseType;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Category;
import com.ulewo.po.model.Exam;
import com.ulewo.po.model.SessionUser;
import com.ulewo.po.query.CategoryQuery;
import com.ulewo.po.query.ExamQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.CategoryService;
import com.ulewo.service.ExamService;
import com.ulewo.utils.Constants;

/**
 * ClassName:ExamController <br/>
 * Date:     2015年10月11日 上午7:54:30 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
@RequestMapping("/exam")
public class ExamController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ExamController.class);

	@Resource
	private ExamService examService;

	@Resource
	private CategoryService categoryService;

	@RequestMapping
	public ModelAndView exam(HttpSession session) {
		List<Category> categoryList = CategoryCache.getExamCategory();
		ModelAndView view = new ModelAndView("/page/exam/exam");
		view.addObject("categoryList", categoryList);
		return view;
	}

	/**
	 * 
	 * loadPostExamUsers:(加载发题人). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping("load_post_exam_users")
	public AjaxResponse<PaginationResult<Exam>> load_post_exam_users(ExamQuery query) {
		AjaxResponse<PaginationResult<Exam>> result = new AjaxResponse<PaginationResult<Exam>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			PaginationResult<Exam> data = examService.findsExamUsers(query);
			result.setData(data);
		} catch (Exception e) {
			logger.error("加载发题人异常", e);
			result.setErrorMsg("加载发题人失败");
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	@RequestMapping(value = "do_exam.action")
	public ModelAndView do_exam(HttpSession session, Integer categoryId) {
		ModelAndView view = new ModelAndView("/page/exam/do_exam");
		view.addObject("categoryId", categoryId);
		return view;
	}

	
	@RequestMapping(value = "do_learn.action")
	public ModelAndView do_learn(HttpSession session, Integer categoryId) {
		ModelAndView view = new ModelAndView("/page/exam/learn");
		view.addObject("categoryId", categoryId);
		return view;
	}
	/**
	 * 
	 * loadExams:(加载随机考题). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping("load_exams.action")
	public AjaxResponse<List<Exam>> load_exams(Integer categoryId) {
		AjaxResponse<List<Exam>> result = new AjaxResponse<List<Exam>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			List<Exam> data = examService.findExamListRand(categoryId,Constants.EXAM_MAX_TITLE);
			result.setData(data);
		} catch (Exception e) {
			logger.error("加载考题异常");
			result.setErrorMsg("加载考题失败");
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("learn.action")
	public AjaxResponse<List<Exam>> learn(Integer categoryId) {
		AjaxResponse<List<Exam>> result = new AjaxResponse<List<Exam>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			List<Exam> data = examService.findExamListRand(categoryId,1);
			result.setData(data);
		} catch (Exception e) {
			logger.error("加载考题异常");
			result.setErrorMsg("加载考题失败");
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	/**
	 * 
	 * do_mark:(交卷打分). <br/>
	 *
	 * @author 不错啊
	 * @param categoryId
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping("do_mark.action")
	public AjaxResponse<List<Exam>> do_mark(HttpSession session,String examIdstr, String rightAnswerstr) {
		AjaxResponse<List<Exam>> result = new AjaxResponse<List<Exam>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		
		final SessionUser sessionUser = super.getSessionUser(session);
		try {
			List<Exam> data = examService.doMark(examIdstr, rightAnswerstr,sessionUser);
			result.setData(data);
		} catch (BusinessException e) {
			logger.error("打分异常", e);
			result.setErrorMsg(e.getMessage());
			result.setResponseCode(ResponseCode.BUSINESSERROR);
		} catch (Exception e) {
			logger.error("考题异常", e);
			result.setErrorMsg("系统异常");
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	/**
	 * 
	 * spitSlot:(发表考题). <br/>
	 *
	 * @author 不错啊
	 * @param session
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "post_exam.action")
	public ModelAndView postExam(HttpSession session) {
		CategoryQuery query = new CategoryQuery();
		query.setShowInExam(Constants.Y);
		List<Category> categoryList = categoryService.findCategroyList(query);
		ModelAndView view = new ModelAndView("/page/exam/add_exam");
		view.addObject("examChooseType", ExamChooseType.values());
		view.addObject("categoryList", categoryList);
		return view;
	}

	@RequestMapping(value = "add_exam.action")
	@ResponseBody
	public AjaxResponse<Integer> addExam(HttpSession session, Exam exam, String[] answer, Integer[] rightAnswer) {
		this.setUserBaseInfo(Exam.class, exam, session);
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			examService.saveExam(exam, answer, rightAnswer);
			result.setData(exam.getId());
		} catch (BusinessException e) {
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
			logger.error("保存考题失败 exam:{},answer:{},rightAnswer:{}", exam, answer, rightAnswer, e);
		} catch (Exception e) {
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("系统异常");
			logger.error("保存考题失败", e);
		}
		return result;
	}
}
