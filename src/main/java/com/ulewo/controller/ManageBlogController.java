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
import com.ulewo.po.model.Blog;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.BlogService;

/**
 * 
 * ClassName: ManagerKnowledgeController
 * date: 2015年12月13日 下午9:08:51 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/manage/blog")
public class ManageBlogController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ManageBlogController.class);

	@Resource
	private BlogService blogService;

	@RequestMapping(value = "blog_list")
	public ModelAndView blog_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/manage/blog_list");
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
	@RequestMapping(value = "load_blog")
	public AjaxResponse<PaginationResult<Blog>> load_blog(BlogQuery query) {
		AjaxResponse<PaginationResult<Blog>> result = new AjaxResponse<PaginationResult<Blog>>();
		try {
			PaginationResult<Blog> data = blogService.findBlogByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载博客异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载博客异常");
		}
		return result;
	}

	/**
	 * 
	 * delete_blog:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param ids
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "delete_blog")
	public AjaxResponse<?> delete_blog(Integer[] ids) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			blogService.deleteBatch(ids);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除博客异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除博客异常");
		}
		return result;
	}
}
