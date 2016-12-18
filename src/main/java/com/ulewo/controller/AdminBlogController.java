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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.BlogStatusEnum;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.BlogCategory;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.BlogCategoryService;
import com.ulewo.service.BlogService;
import com.ulewo.service.UserService;
import com.ulewo.utils.Constants;

/**
 * ClassName:CommentController <br/>
 * Date:     2015年11月2日 下午9:30:38 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
@RequestMapping("/admin")
public class AdminBlogController extends BaseController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserService userService;

	@Resource
	private BlogCategoryService blogCategoryService;

	@Resource
	private BlogService blogService;

	@RequestMapping(value = "blog_list.action")
	public ModelAndView blog_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/admin/blog_list");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "load_blog.action")
	public AjaxResponse<PaginationResult<Blog>> load_blog(HttpSession session, BlogQuery query) {
		AjaxResponse<PaginationResult<Blog>> result = new AjaxResponse<PaginationResult<Blog>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			query.setUserId(this.getUserId(session));
			query.setStatus(BlogStatusEnum.PASS);
			PaginationResult<Blog> blogList = blogService.findBlogByPage(query);
			result.setData(blogList);
		} catch (Exception e) {
			logger.error("获取博客信息异常", e);
		}
		return result;
	}

	@RequestMapping(value = "blog_drafts_list.action")
	public ModelAndView blog_drafts_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/admin/blog_drafts_list");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "load_drafts_blog.action")
	public AjaxResponse<PaginationResult<Blog>> load_drafts_blog(HttpSession session, BlogQuery query) {
		AjaxResponse<PaginationResult<Blog>> result = new AjaxResponse<PaginationResult<Blog>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			query.setUserId(this.getUserId(session));
			query.setStatus(BlogStatusEnum.DRAFTS);
			PaginationResult<Blog> blogList = blogService.findBlogByPage(query);
			result.setData(blogList);
		} catch (Exception e) {
			logger.error("获取博客信息异常", e);
		}
		return result;
	}

	@RequestMapping(value = "add_blog.action")
	public ModelAndView add_blog(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/admin/blog_add");
		List<BlogCategory> categories = blogCategoryService.findBlogCategoryList(this.getUserId(session));
		view.addObject("categories", categories);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "save_drafts_blog.action")
	public AjaxResponse<Integer> save_drafts_blog(HttpSession session, String richContent, Blog blog) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			this.setUserBaseInfo(Blog.class, blog, session);
			blog.setContent(richContent);
			blogService.saveDraftsBlog(blog);
			result.setData(blog.getBlogId());
		} catch (Exception e) {
			logger.error("保存博客草稿异常", e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "save_blog.action")
	public AjaxResponse<Integer> save_blog(HttpSession session, Blog blog, String richContent, Attachment attachment) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();

		try {
			blog.setContent(richContent);
			this.setUserBaseInfo(Blog.class, blog, session);
			blogService.postBlog(blog, attachment);
			result.setData(blog.getBlogId());
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("新增博客异常", e);
			result.setErrorMsg(e.getMessage());
			result.setResponseCode(ResponseCode.BUSINESSERROR);
		} catch (Exception e) {
			logger.error("新增博客异常", e);
			result.setErrorMsg("系统异常");
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	@RequestMapping(value = "edit_blog.action")
	public ModelAndView edit_blog(HttpSession session, Integer blogId) {
		ModelAndView view = new ModelAndView("/page/admin/blog_add");
		Blog blog = null;
		try {
			blog = blogService.getBlogByBlogId(blogId, this.getUserId(session));
		} catch (BusinessException e) {
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		List<BlogCategory> categories = blogCategoryService.findBlogCategoryList(this.getUserId(session));
		view.addObject("categories", categories);
		view.addObject("blog", blog);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "del_blog.action")
	public AjaxResponse<Integer> del_blog(HttpSession session, Integer blogId) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();

		try {
			blogService.delBlog(blogId, this.getUserId(session));
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("删除博客异常", e);
			result.setErrorMsg(e.getMessage());
			result.setResponseCode(ResponseCode.BUSINESSERROR);
		} catch (Exception e) {
			logger.error("删除博客异常", e);
			result.setErrorMsg("系统异常");
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}
}
