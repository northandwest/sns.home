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

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Comment;
import com.ulewo.po.query.CommentQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.CommentService;
import com.ulewo.utils.StringTools;

/**
 * ClassName:CommentController <br/>
 * Date:     2015年11月2日 下午9:30:38 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
public class CommentController extends BaseController {
	@Resource
	private CommentService commentService;

	Logger logger = LoggerFactory.getLogger(CommentController.class);

	@ResponseBody
	@RequestMapping(value = "loadComment")
	public AjaxResponse<PaginationResult<Comment>> loadComment(CommentQuery query) {
		AjaxResponse<PaginationResult<Comment>> result = new AjaxResponse<PaginationResult<Comment>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			query.setPid(0);
			PaginationResult<Comment> data = this.commentService.findCommentByPage(query);
			result.setData(data);
		} catch (Exception e) {
			logger.error("加载评论异常，articleId:{},articleType:{}", query.getArticleId(), query.getArticleType(), e);
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "addComment.action")
	public AjaxResponse<Comment> addComment(HttpSession session, Comment comment, String richContent) {
		AjaxResponse<Comment> result = new AjaxResponse<Comment>();
		try {
			if (!StringTools.isEmpty(richContent)) {
				comment.setContent(richContent);
			}
			this.setUserBaseInfo(Comment.class, comment, session);
			this.commentService.addComment(comment);
			result.setResponseCode(ResponseCode.SUCCESS);
			result.setData(comment);
		} catch (BusinessException e) {
			logger.error("保存评论异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("保存评论异常");
		}
		return result;
	}
}
