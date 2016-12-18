/**
 * Project Name:ulewo-web
 * File Name:LikeController.java
 * Package Name:com.ulewo.controller
 * Date:2015年10月9日下午9:00:57
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

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Collection;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.service.CollectionService;

@Controller
public class CollectionController extends BaseController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private CollectionService collectionService;

	/**
	 * 
	 * addCollection:(收藏). <br/>
	 *
	 * @author 不错啊
	 * @param session
	 * @param collection
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "addCollection.action")
	public AjaxResponse<?> addCollection(HttpSession session, Collection collection) {
		AjaxResponse<Object> result = new AjaxResponse<Object>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			collection.setUserId(this.getUserId(session));
			collectionService.addCollection(collection);
		} catch (BusinessException e) {
			result.setErrorMsg(e.getMessage());
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			logger.error("收藏异常,articleId:{},type:{},title:{}", collection.getArticleId(), collection.getArticleType(),
					collection.getTitle(), e);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("收藏异常,articleId:{},type:{},title:{}", collection.getArticleId(), collection.getArticleType(),
					collection.getTitle(), e);
		}
		return result;
	}
}
