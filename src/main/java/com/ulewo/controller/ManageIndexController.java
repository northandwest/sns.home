/**
 * Project Name:ulewo-web
 * File Name:ManagerSpitSlotController.java
 * Package Name:com.ulewo.controller
 * Date:2015年12月10日下午9:38:43
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

import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.chart.Chart;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.service.AskService;
import com.ulewo.service.StatisticsService;

/**
 * 
 * ClassName: ManagerKnowledgeController
 * date: 2015年12月13日 下午9:08:51 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/manage/index")
public class ManageIndexController extends BaseController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private AskService askService;

	@Resource
	private StatisticsService statisticsService;

	@RequestMapping
	public ModelAndView statistics(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/manage/index");
		return view;
	}

	/**
	 * 
	 * load_topic:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "load_data")
	public AjaxResponse<List<Chart>> load_topic() {
		AjaxResponse<List<Chart>> result = new AjaxResponse<List<Chart>>();
		try {
			List<Chart> list = statisticsService.getChartList();
			result.setData(list);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载统计信息异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载统计信息异常");
		}
		return result;
	}
}
