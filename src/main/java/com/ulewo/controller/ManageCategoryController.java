package com.ulewo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.cache.CategoryCache;
import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Category;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.service.CategoryService;

@Controller
@RequestMapping("/manage/category")
public class ManageCategoryController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ManageCategoryController.class);

	@Resource
	private CategoryService categoryService;

	@RequestMapping
	public ModelAndView category() {
		ModelAndView view = new ModelAndView("/page/manage/category");
		return view;
	}

	@RequestMapping("/loadCategories")
	@ResponseBody
	public AjaxResponse<List<Category>> loadCategories() {
		AjaxResponse<List<Category>> result = new AjaxResponse<List<Category>>();
		try {
			result.setResponseCode(ResponseCode.SUCCESS);
			result.setData(this.categoryService.findCategroyList(null));
		} catch (Exception e) {
			logger.error("加载分类异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	@RequestMapping("/saveCategory.action")
	@ResponseBody
	public AjaxResponse<?> saveCategory(Category category) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			result.setResponseCode(ResponseCode.SUCCESS);
			categoryService.saveCategory(category);
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

	@RequestMapping("/delCategory")
	@ResponseBody
	public AjaxResponse<?> delCategory(Integer categoryId) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			result.setResponseCode(ResponseCode.SUCCESS);
			categoryService.delCategory(categoryId);
		} catch (Exception e) {
			logger.error("删除分类异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	@RequestMapping("/refreshCategory")
	@ResponseBody
	public AjaxResponse<?> refreshCategory(Category category) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			result.setResponseCode(ResponseCode.SUCCESS);
			CategoryCache.refreshCategoryCache();
		} catch (Exception e) {
			logger.error("刷新分类异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}
}
