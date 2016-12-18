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
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.Exam;
import com.ulewo.po.query.ExamQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.CategoryService;
import com.ulewo.service.ExamService;
import com.ulewo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminExamController extends BaseController{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private UserService userService;
	
	@Resource
	private ExamService examService;

	@Resource
	private CategoryService categoryService;
	
	@RequestMapping(value = "my_question.action")
	public ModelAndView question_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/admin/question_list");
		return view;
	}
	
	@RequestMapping(value = "my_error.action")
	public ModelAndView error_question_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/admin/question_list");
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "load_my_question.action")
	public AjaxResponse<PaginationResult<Exam>> load_my(HttpSession session, ExamQuery query) {
		AjaxResponse<PaginationResult<Exam>> result = new AjaxResponse<PaginationResult<Exam>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			query.setUserId(this.getUserId(session));
//			query.setStatus(StatusEnum.INIT);
			
			PaginationResult<Exam> examList = examService.findExamByPage(query) ;//.findBlogByPage(query);
			result.setData(examList);
		} catch (Exception e) {
			logger.error("获取博客信息异常", e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "load_my_error_question.action")
	public AjaxResponse<PaginationResult<Exam>> load_error(HttpSession session, ExamQuery query) {
		AjaxResponse<PaginationResult<Exam>> result = new AjaxResponse<PaginationResult<Exam>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			query.setUserId(this.getUserId(session));
			
			PaginationResult<Exam> examList = examService.findExamByPage(query) ;//.findBlogByPage(query);
			result.setData(examList);
		} catch (Exception e) {
			logger.error("获取博客信息异常", e);
		}
		return result;
	}
	
}
