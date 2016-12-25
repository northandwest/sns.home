/**
 * Project Name:ulewo-web
 * File Name:UserHomeController.java
 * Package Name:com.ulewo.controller
 * Date:2015年11月22日下午6:13:10
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bucuoa.ucenter.model.UlewoUser;
import com.ulewo.po.enums.BlogStatusEnum;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Ask;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.BlogCategory;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.model.SpitSlot;
import com.ulewo.po.model.Topic;
import com.ulewo.po.model.User;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.query.SpitSlotQuery;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.AskService;
import com.ulewo.service.BlogCategoryService;
import com.ulewo.service.BlogService;
import com.ulewo.service.KnowledgeService;
import com.ulewo.service.SpitSlotService;
import com.ulewo.service.TopicService;
import com.ulewo.service.UserFriendService;
import com.ulewo.service.UserService;
import com.ulewo.utils.Constants;

/**
 * ClassName:UserHomeController <br/>
 * Date: 2015年11月22日 下午6:13:10 <br/>
 * 
 * @author 不错啊 Copyright (c) 2015, bucuoa.com All Rights Reserved.
 */
@Controller
@RequestMapping(value = "/user")
public class UserHomeController extends BaseController {
	Logger logger = LoggerFactory.getLogger(UserHomeController.class);

	@Resource
	private UserService userService;

	@Resource
	private SpitSlotService spitSlotService;

	@Resource
	private TopicService topicService;

	@Resource
	private AskService askService;

	@Resource
	private KnowledgeService knowledgeService;

	@Resource
	private BlogService blogService;

	@Resource
	private BlogCategoryService blogCategoryService;

	@Resource
	private UserFriendService userFriendService;

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ModelAndView user(HttpSession session, @PathVariable Integer userId) {
		ModelAndView view = new ModelAndView("/page/user/home");
		try {
			UlewoUser user = userService.findUserByUserId(userId.toString());
			
			Integer friendId = super.getUserId(session);
			boolean isFriend = false;
			if(friendId != null){
				isFriend = userService.isFriend(userId, friendId);
			}
			user.setHaveFocus(isFriend);
			
			Integer myFriendCount = userService.myFriendCount(userId);
			
			Integer focusMeCount = userService.focusMeCount(userId);
			
			view.addObject("myFriendCount", myFriendCount);
			view.addObject("focusMeCount", focusMeCount);
			view.addObject("user", user);
		} catch (Exception e) {
			logger.error("获取用户信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	@RequestMapping(value = "/{userId}/blog", method = RequestMethod.GET)
	public ModelAndView blog(HttpSession session, @PathVariable Integer userId, BlogQuery query) {
		ModelAndView view = new ModelAndView("/page/user/blog");
		try {
			// 查询用户信息
			UlewoUser user = userService.findUserByUserId(userId.toString());//userService.findUserInfo4UserHome(userId, this.getUserId(session));
			// 查询博客分类
			List<BlogCategory> categoryList = blogCategoryService.findBlogCategoryList(userId);
			// 查询博客
			query.setStatus(BlogStatusEnum.PASS);
			PaginationResult<Blog> result = blogService.findBlogByPage(query);
			view.addObject("user", user);
			view.addObject("categoryList", categoryList);
			view.addObject("result", result);
			view.addObject("categoryId", query.getCategoryId());
		} catch (Exception e) {
			logger.error("获取博客信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	@RequestMapping(value = "/{userId}/blog/{blogId}", method = RequestMethod.GET)
	public ModelAndView blog(HttpSession session, @PathVariable Integer userId, @PathVariable Integer blogId,
			BlogQuery query) {
		ModelAndView view = new ModelAndView("/page/user/blog_detail");
		try {
			// 查询用户信息
			UlewoUser user = userService.findUserByUserId(userId.toString());//userService.findUserInfo4UserHome(userId, this.getUserId(session));
			// 查询博客详情
			query.setStatus(BlogStatusEnum.PASS);
			Blog blog = blogService.showBlog(blogId, userId);
			// 查询博客分类
			List<BlogCategory> categoryList = blogCategoryService.findBlogCategoryList(userId);
			view.addObject("user", user);
			view.addObject("topic", blog);
			view.addObject("categoryList", categoryList);
			view.addObject("categoryId", blog.getCategoryId());
		} catch (Exception e) {
			logger.error("获取博客信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	@RequestMapping(value = "/{userId}/spitslot/{id}", method = RequestMethod.GET)
	public ModelAndView blog(HttpSession session, @PathVariable Integer userId, @PathVariable Integer id) {
		ModelAndView view = new ModelAndView("/page/user/spitslot_detail");
		try {
			// 查询用户信息
			UlewoUser user = userService.findUserByUserId(userId.toString());//userService.findUserInfo4UserHome(userId, this.getUserId(session));
			view.addObject("user", user);
			view.addObject("id", id);
		} catch (Exception e) {
			logger.error("获取用户信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "loadSpitSlotDetail")
	public AjaxResponse<SpitSlot> loadSpitSlotDetail(SpitSlotQuery query) {
		AjaxResponse<SpitSlot> result = new AjaxResponse<SpitSlot>();
		try {
			PaginationResult<SpitSlot> data = spitSlotService.findSpitSlotList(query);
			result.setData(data.getList().get(0));
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载吐槽异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载吐槽异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "loadSpitSlot")
	public AjaxResponse<PaginationResult<SpitSlot>> loadSpitSlot(SpitSlotQuery query) {
		AjaxResponse<PaginationResult<SpitSlot>> result = new AjaxResponse<PaginationResult<SpitSlot>>();
		try {
			PaginationResult<SpitSlot> data = spitSlotService.findSpitSlotList(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载吐槽异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载吐槽异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "loadTopic")
	public AjaxResponse<PaginationResult<Topic>> loadTopic(TopicQuery query) {
		AjaxResponse<PaginationResult<Topic>> result = new AjaxResponse<PaginationResult<Topic>>();
		try {
			PaginationResult<Topic> data = topicService.findTopicsByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载论坛数据异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载论坛数据异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "loadAsk")
	public AjaxResponse<PaginationResult<Ask>> loadAsk(AskQuery query) {
		AjaxResponse<PaginationResult<Ask>> result = new AjaxResponse<PaginationResult<Ask>>();
		try {
			PaginationResult<Ask> data = askService.findAskByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载问答数据异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载问答数据异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "loadKnowledge")
	public AjaxResponse<PaginationResult<Knowledge>> loadKnowledge(KnowledgeQuery query) {
		AjaxResponse<PaginationResult<Knowledge>> result = new AjaxResponse<PaginationResult<Knowledge>>();
		try {
			PaginationResult<Knowledge> data = knowledgeService.findKnowledgesByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载知识库数据异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载知识库数据异常");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "load_user_info.action")
	public AjaxResponse<User> load_user_info(HttpSession session) {
		AjaxResponse<User> result = new AjaxResponse<User>();
		result.setResponseCode(ResponseCode.SUCCESS);
		Integer userId = this.getUserId(session);
		UlewoUser user = this.userService.findUserByUserId(userId.toString());
		User resultUser = new User();
		resultUser.setMark(user.getMark());
		resultUser.setFansCount(userFriendService.findFansCount(userId));
		resultUser.setFocusCount(userFriendService.findFocusCount(userId));
		result.setData(resultUser);
		return result;
	}
}
