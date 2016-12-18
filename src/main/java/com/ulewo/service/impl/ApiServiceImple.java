/**
 * Project Name:ulewo-zx
 * File Name:ApiServiceImple.java
 * Package Name:com.ulewo.service
 * Date:2015年9月12日上午11:16:19
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ulewo.exception.BusinessException;
import com.ulewo.service.ApiService;
import com.ulewo.utils.JacksonMapper;
import com.ulewo.utils.NetUtils;

/**
 * ClassName:ApiServiceImple <br/>
 * Date:     2015年9月12日 上午11:16:19 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("apiService")
public class ApiServiceImple implements ApiService {

	@Value("#{applicationProperties['api.baidu_key']}")
	private String BAIDU_KEY;
	@Value("#{applicationProperties['api.baidu_api_ip_url']}")
	private String BAIDU_API_IP_URL;
	@Value("#{applicationProperties['api.weather_key']}")
	private String WEATHER_KEY;
	@Value("#{applicationProperties['api.weather_api_url']}")
	private String WEATHER_API_URL;
	@Value("#{applicationProperties['api.music_url']}")
	private String music_url;

	public static Cache<String, List<Map<String, Object>>> cache = CacheBuilder.newBuilder()
			.expireAfterAccess(86400, TimeUnit.SECONDS).maximumSize(1).build();

	@SuppressWarnings("unchecked")
	public Map<String, Object> getWeatherInfo(String ip) throws BusinessException {
		//获取地区信息
		Map<String, Object> now = new HashMap<String, Object>();
		try {
			String city = getCity(ip);
			city = URLEncoder.encode(city, "utf-8");
			String url = WEATHER_API_URL + "?city=" + city + "&key=" + WEATHER_KEY;
			String jsonData = NetUtils.getRequest(url);
			List<Map<String, Object>> resultMaps = (List<Map<String, Object>>) JacksonMapper.fromJson2Map(jsonData)
					.get("HeWeather data service 3.0");
			now = (Map<String, Object>) resultMaps.get(0).get("now");

			Map<String, Object> basic = (Map<String, Object>) resultMaps.get(0).get("basic");

			now.put("city", basic.get("city"));
			now.put("loc", ((Map<String, Object>) basic.get("update")).get("loc"));
		} catch (BusinessException e) {
			throw new BusinessException("没有获取到您所在地区的天气信息");
		} catch (Exception e) {
			throw new BusinessException("没有获取到您所在地区的天气信息");
		}
		return now;
	}

	@SuppressWarnings("unchecked")
	private String getCity(String ip) throws BusinessException {
		if (StringUtils.isEmpty(ip)) {
			throw new BusinessException("获取地区信息失败");
		}
		String url = BAIDU_API_IP_URL + "?ip=" + ip;
		Map<String, String> haderParam = new HashMap<String, String>();
		haderParam.put("apikey", BAIDU_KEY);
		String jsonData = NetUtils.getRequest(url, haderParam);
		if (jsonData == null) {
			throw new BusinessException("没有获取到您所在的地区");
		}
		Map<?, ?> areaMap = JacksonMapper.fromJson2Map(jsonData);
		String city = ((Map<String, String>) areaMap.get("retData")).get("city");
		return city;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchMusic(String keyWord) throws BusinessException {
		if (StringUtils.isEmpty(keyWord)) {
			throw new BusinessException("请输入关键字");
		}
		try {
			keyWord = URLEncoder.encode(keyWord, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = music_url + "?s=" + keyWord + "&limit=20";
		Map<String, String> haderParam = new HashMap<String, String>();
		haderParam.put("apikey", BAIDU_KEY);
		String jsonData = NetUtils.getRequest(url, haderParam);
		if (jsonData == null) {
			throw new BusinessException("没有获取到音乐数据");
		}
		Map<?, ?> areaMap = JacksonMapper.fromJson2Map(jsonData);
		Map<String, Object> pData = (Map<String, Object>) areaMap.get("data");
		Map<String, Object> resultData = (Map<String, Object>) pData.get("data");
		List<Map<String, Object>> listMusic = (List<Map<String, Object>>) resultData.get("list");
		return listMusic;
	}

}
