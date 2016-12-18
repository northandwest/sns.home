/**
 * Project Name:ulewo-web
 * File Name:LikeController.java
 * Package Name:com.ulewo.controller
 * Date:2015年10月9日下午9:00:57
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutController extends BaseController {

	@RequestMapping("/donate")
	public ModelAndView donate() {
		ModelAndView view = new ModelAndView("/page/about/donate");
		return view;
	}

	@RequestMapping("/about")
	public ModelAndView about() {
		ModelAndView view = new ModelAndView("/page/about/about");
		return view;
	}

	@RequestMapping("/stationmaster")
	public ModelAndView stationmaster() {
		ModelAndView view = new ModelAndView("/page/about/stationmaster");
		return view;
	}

	@RequestMapping("/contact")
	public ModelAndView contact() {
		ModelAndView view = new ModelAndView("/page/about/contact");
		return view;
	}

	@RequestMapping("/faq")
	public ModelAndView faq() {
		ModelAndView view = new ModelAndView("/page/about/faq");
		return view;
	}

	@RequestMapping("/source_code")
	public ModelAndView source_code() {
		ModelAndView view = new ModelAndView("/page/about/source_code");
		return view;
	}

	@RequestMapping("/get_mark")
	public ModelAndView get_mark() {
		ModelAndView view = new ModelAndView("/page/about/get_mark");
		return view;
	}

	@RequestMapping("/set_mark")
	public ModelAndView set_mark() {
		ModelAndView view = new ModelAndView("/page/about/set_mark");
		return view;
	}
}
