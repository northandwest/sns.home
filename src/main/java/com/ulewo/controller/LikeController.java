/**
 * Project Name:ulewo-web
 * File Name:LikeController.java
 * Package Name:com.ulewo.controller
 * Date:2015年10月9日下午9:00:57
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

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Like;
import com.ulewo.po.query.LikeQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.service.LikeService;

/**
 * ClassName:LikeController <br/>
 * Date:     2015年10月9日 下午9:00:57 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
public class LikeController extends BaseController {
	Logger logger = LoggerFactory.getLogger(LikeController.class);

	@Resource
	private LikeService likeService;

	/**
	 * 
	 * doLike:(点赞). <br/>
	 *
	 * @author 不错啊
	 * @param session
	 * @param like
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "doLike.action")
	public AjaxResponse<Like> doLike(HttpSession session, Like like) {
		AjaxResponse<Like> result = new AjaxResponse<Like>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			this.setUserBaseInfo(Like.class, like, session);
			likeService.doLike(like);
			result.setData(like);
		} catch (BusinessException e) {
			result.setErrorMsg(e.getMessage());
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			logger.error("点赞异常", e);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("点赞异常,articleId:{},articleType:{}", like.getArticleId(), like.getArticleType(), e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "getLikeList")
	public AjaxResponse<List<Like>> getLikeList(HttpSession session, LikeQuery query) {
		AjaxResponse<List<Like>> result = new AjaxResponse<List<Like>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			List<Like> likeList = likeService.findLikeList(query);
			result.setData(likeList);
		} catch (Exception e) {
			result.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			result.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("查询点赞异常", e);
		}
		return result;
	}
}
