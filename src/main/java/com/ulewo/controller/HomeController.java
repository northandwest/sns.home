/**
 * Project Name:ulewo-web
 * File Name:HomeController.java
 * Package Name:com.ulewo.controller
 * Date:2015年10月25日下午3:01:15
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Ask;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.model.SignInInfo;
//import com.ulewo.po.model.SolrBean;
import com.ulewo.po.model.Topic;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.ApiService;
import com.ulewo.service.AskService;
import com.ulewo.service.BlogService;
import com.ulewo.service.KnowledgeService;
import com.ulewo.service.SignInService;
//import com.ulewo.service.SolrService;
import com.ulewo.service.TopicService;

/**
 * ClassName:HomeController <br/>
 * Date:     2015年10月25日 下午3:01:15 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Controller
public class HomeController extends BaseController {
	@Resource
	private SignInService signInService;

	@Resource
	private TopicService topicService;
	@Resource
	private AskService askService;
	@Resource
	private KnowledgeService knowledgeService;
	@Resource
	private BlogService blogService;
	@Resource
	private ApiService apiService;

//	@Resource
//	private SolrService solrService;

	private Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/")
	public ModelAndView index(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/index");
		SignInInfo signInInfo = signInService.findSignInInfoByUserId(this.getUserId(session));

		List<Topic> topicList = topicService.findTopics4Index();
		List<Ask> askList = askService.findAsks4Index();
		List<Knowledge> knowledgeList = knowledgeService.findKnowledges4Index();
		List<Blog> blogList = blogService.findBlogs4Index();
		view.addObject("topicList", topicList);
		view.addObject("askList", askList);
		view.addObject("knowledgeList", knowledgeList);
		view.addObject("blogList", blogList);
		view.addObject("signInInfo", signInInfo);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/loadWeatherInfo.do")
	public AjaxResponse<Map<String, Object>> loadWeatherInfo(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		AjaxResponse<Map<String, Object>> result = new AjaxResponse<Map<String, Object>>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			String ip = getIpAddr(request);//getIpAddr(request) "59.172.71.206";
			Map<String, Object> weatherInfo = apiService.getWeatherInfo(ip);
			result.setData(weatherInfo);
		} catch (Exception e) {
			logger.error("加载天气信息失败", e);
		}
		return result;
	}

	private String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
		if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15   
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	@RequestMapping(value = "/action404")
	public ModelAndView action404() {
		ModelAndView view = new ModelAndView("/page/error/404");
		return view;
	}

	@RequestMapping(value = "/search")
	public ModelAndView search(HttpSession session, String searchType, String keyword) {
		ModelAndView view = new ModelAndView("/page/search");
		view.addObject("searchType", searchType);
		view.addObject("keyword", keyword);
		return view;
	}

//	@ResponseBody
//	@RequestMapping(value = "load_search")
//	public AjaxResponse<PaginationResult<SolrBean>> load_search(String articleType, String keyword, Integer pageNo,
//			Integer countTotal) {
//		AjaxResponse<PaginationResult<SolrBean>> result = new AjaxResponse<PaginationResult<SolrBean>>();
//		try {
//			PaginationResult<SolrBean> data = solrService.selectArticle(keyword, articleType, pageNo, countTotal);
//			result.setData(data);
//			result.setResponseCode(ResponseCode.SUCCESS);
//		} catch (BusinessException e) {
//			logger.error("搜索异常", e);
//			result.setResponseCode(ResponseCode.BUSINESSERROR);
//			result.setErrorMsg(e.getMessage());
//		} catch (Exception e) {
//			logger.error("搜索异常", e);
//			result.setResponseCode(ResponseCode.SERVERERROR);
//			result.setErrorMsg("搜索异常");
//		}
//		return result;
//	}

}
