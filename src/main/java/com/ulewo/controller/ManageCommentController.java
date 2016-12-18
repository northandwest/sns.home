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

import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Comment;
import com.ulewo.po.query.CommentQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.CommentService;

/**
 * 
 * ClassName: ManagerKnowledgeController
 * date: 2015年12月13日 下午9:08:51 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/manage/comment")
public class ManageCommentController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ManageCommentController.class);

	@Resource
	private CommentService commentService;

	@RequestMapping(value = "comment_list")
	public ModelAndView knowledge_list(HttpSession session, String articleType) {
		ModelAndView view = new ModelAndView("/page/manage/comment_list");
		view.addObject("articleType", articleType);
		return view;
	}

	/**
	 * 
	 * load_comment:(加载评论). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "load_comment")
	public AjaxResponse<PaginationResult<Comment>> load_comment(CommentQuery query) {
		AjaxResponse<PaginationResult<Comment>> result = new AjaxResponse<PaginationResult<Comment>>();
		try {
			PaginationResult<Comment> data = commentService.findAllCommentByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载评论异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载评论异常");
		}
		return result;
	}

	/**
	 * 
	 * delete_comment:(delete_comment). <br/>
	 *
	 * @author 不错啊
	 * @param commentId
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "delete_comment")
	public AjaxResponse<?> delete_comment(Integer commentId, ArticleType articleType) {
		AjaxResponse<?> result = new AjaxResponse<Object>();
		try {
			commentService.deleteComment(commentId, articleType);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("删除评论异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("删除评论异常");
		}
		return result;
	}
}
