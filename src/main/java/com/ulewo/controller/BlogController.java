package com.ulewo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.po.enums.BlogStatusEnum;
import com.ulewo.po.model.Blog;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.BlogService;

@Controller
public class BlogController extends BaseController {
	@Resource
	private BlogService blogService;

	@RequestMapping("/blog")
	public ModelAndView blogList(HttpSession session, BlogQuery query) {
		ModelAndView view = new ModelAndView("/page/blog/blog");
		query.setStatus(BlogStatusEnum.PASS);
		PaginationResult<Blog> result = blogService.findBlogByPage(query);
		view.addObject("result", result);
		return view;
	}
}
