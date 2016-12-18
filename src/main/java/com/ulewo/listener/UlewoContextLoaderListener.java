package com.ulewo.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ulewo.cache.CategoryCache;
import com.ulewo.init.InitTask;
import com.ulewo.utils.ServerUtils;
import com.ulewo.utils.SpringContextUtil;

public class UlewoContextLoaderListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		//初始化SpringContextUtil的context
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		SpringContextUtil.setContext(ctx);
		//设置服务器相关路径
		ServerUtils.setRealPath(context.getRealPath(""));
		//初始化分类
		CategoryCache.refreshCategoryCache();

//		InitTask initTask = new InitTask();
//		initTask.onApplicationEvent();
	}
}