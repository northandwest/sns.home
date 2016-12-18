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
import com.ulewo.po.model.Exam;
import com.ulewo.po.query.ExamQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.ExamService;

/**
 * ClassName:ManagerSpitSlotController <br/>
 * Date:     2015年12月10日 下午9:38:43 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
@RequestMapping("/manage/exam")
public class ManageExamController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ManageExamController.class);

	@Resource
	private ExamService examService;

	@RequestMapping(value = "exam_list")
	public ModelAndView exam_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/manage/exam_list");
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
	@RequestMapping(value = "load_exam")
	public AjaxResponse<PaginationResult<Exam>> load_exam(ExamQuery query) {
		AjaxResponse<PaginationResult<Exam>> result = new AjaxResponse<PaginationResult<Exam>>();
		try {
			PaginationResult<Exam> data = examService.findExamByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载考题异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载考题异常");
		}
		return result;
	}

	/**
	 * 
	 * audit_exam:(审核考题). <br/>
	 *
	 * @author 不错啊
	 * @param examIds
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "audit_exam")
	public AjaxResponse<?> audit_exam(Integer[] ids) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			examService.auditExam(ids);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("审核考题异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("审核考题异常");
		}
		return result;
	}

	/**
	 * 
	 * delete_exam:(删除考题). <br/>
	 *
	 * @author 不错啊
	 * @param examIds
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "delete_exam")
	public AjaxResponse<?> delete_exam(Integer[] ids) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			examService.deleteExam(ids);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除考题异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除考题异常");
		}
		return result;
	}
}
