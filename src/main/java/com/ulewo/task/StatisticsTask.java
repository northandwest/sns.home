/**
 * Project Name:ulewo-web
 * File Name:StatisticsTask.java
 * Package Name:com.ulewo.task
 * Date:2015年12月17日下午9:28:27
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.task;

import com.ulewo.service.StatisticsService;
import com.ulewo.utils.SpringContextUtil;

/**
 * ClassName:StatisticsTask <br/>
 * Date:     2015年12月17日 下午9:28:27 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class StatisticsTask {
	private StatisticsService statisticsService = (StatisticsService) SpringContextUtil.getBean("statisticsService");

	public void statisticsInfo() {
		statisticsService.statistAllInfo();
	}
}
