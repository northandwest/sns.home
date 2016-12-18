/**
 * Project Name:ulewo-zx
 * File Name:ApiService.java
 * Package Name:com.ulewo.service
 * Date:2015年9月12日上午11:09:48
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.exception.BusinessException;

/**
 * ClassName:ApiService <br/>
 * Date:     2015年9月12日 上午11:09:48 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface ApiService {
	public Map<String, Object> getWeatherInfo(String ip) throws BusinessException;

	List<Map<String, Object>> searchMusic(String keyWord) throws BusinessException;
}
