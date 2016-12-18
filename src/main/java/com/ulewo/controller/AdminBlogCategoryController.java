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
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.BlogCategory;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.service.BlogCategoryService;

@Controller
@RequestMapping("/admin")
public class AdminBlogCategoryController extends BaseController {

	Logger logger = LoggerFactory.getLogger(AdminBlogCategoryController.class);

	@Resource
	private BlogCategoryService blogCategoryService;

	@RequestMapping("/blog_category.action")
	public ModelAndView category() {
		ModelAndView view = new ModelAndView("/page/admin/blog_category");
		return view;
	}

	@RequestMapping("/loadBlogCategories.action")
	@ResponseBody
	public AjaxResponse<List<BlogCategory>> loadBlogCategories(HttpSession session) {
		AjaxResponse<List<BlogCategory>> result = new AjaxResponse<List<BlogCategory>>();
		try {
			result.setResponseCode(ResponseCode.SUCCESS);
			result.setData(this.blogCategoryService.findBlogCategoryList(this.getUserId(session)));
		} catch (Exception e) {
			logger.error("加载分类异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	@RequestMapping("/saveBlogCategory.action")
	@ResponseBody
	public AjaxResponse<?> saveBlogCategory(HttpSession session, BlogCategory category) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			result.setResponseCode(ResponseCode.SUCCESS);
			this.setUserBaseInfo(BlogCategory.class, category, session);
			blogCategoryService.saveBlogCategory(category);
		} catch (BusinessException e) {
			logger.error("保存分类异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("保存分类异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	@RequestMapping("/delBlogCategory.action")
	@ResponseBody
	public AjaxResponse<?> delBlogCategory(HttpSession session, Integer categoryId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			result.setResponseCode(ResponseCode.SUCCESS);
			blogCategoryService.deleteBlogCategory(categoryId, this.getUserId(session));
		} catch (BusinessException e) {
			logger.error("删除分类异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("删除分类异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}
}
