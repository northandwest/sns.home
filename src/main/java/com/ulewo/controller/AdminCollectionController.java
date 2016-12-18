/**
 * Project Name:ulewo-web
 * File Name:CommentController.java
 * Package Name:com.ulewo.controller
 * Date:2015年11月2日下午9:30:38
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

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Collection;
import com.ulewo.po.query.CollectionQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.CollectionService;

/**
 * ClassName:CommentController <br/>
 * Date:     2015年11月2日 下午9:30:38 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
@RequestMapping("/admin")
public class AdminCollectionController extends BaseController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private CollectionService collectionService;

	@RequestMapping(value = "collection_list.action")
	public ModelAndView collection_list(HttpSession session, String articleType) {
		ModelAndView view = new ModelAndView("/page/admin/collection_list");
		view.addObject("articleType", articleType);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "load_collection.action")
	public AjaxResponse<PaginationResult<Collection>> load_collection(HttpSession session, CollectionQuery query) {
		AjaxResponse<PaginationResult<Collection>> result = new AjaxResponse<PaginationResult<Collection>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			query.setUserId(this.getUserId(session));
			PaginationResult<Collection> list = collectionService.findCollectionByPage(query);
			result.setData(list);
		} catch (Exception e) {
			logger.error("获取收藏信息异常", e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "del_collection.action")
	public AjaxResponse<?> del_collection(HttpSession session, Collection collection) {
		AjaxResponse<Object> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			collection.setUserId(this.getUserId(session));
			collectionService.delCollection(collection);
		} catch (BusinessException e) {
			logger.error("删除收藏异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("获取收藏信息异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}
}
