/**
 * Project Name:ulewo-common
 * File Name:StatisticsService.java
 * Package Name:com.ulewo.service
 * Date:2015年12月17日下午9:29:14
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.po.model.chart.Chart;

/**
 * ClassName:StatisticsService <br/>
 * Date:     2015年12月17日 下午9:29:14 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface StatisticsService {
	public void statistAllInfo();

	public List<Chart> getChartList();
}
