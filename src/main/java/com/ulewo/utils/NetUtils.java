/**
 * Project Name:ulewo-zx
 * File Name:NetUtils.java
 * Package Name:com.ulewo.util
 * Date:2015年9月12日上午11:12:31
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:NetUtils <br/>
 * Date:     2015年9月12日 上午11:12:31 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class NetUtils {

	private static Logger logger = LoggerFactory.getLogger(NetUtils.class);

//	private static DefaultHttpClient httpclient = new DefaultHttpClient();
//
//	public static String getHttpClientRequest(String url) {
//
//		HttpGet httpGet = new HttpGet(url);
//		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		httpGet.addHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//		httpGet.addHeader("Connection", "Keep-Alive");
//		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
//		String html = null;
//		try {
//			HttpResponse response = httpclient.execute(httpGet);
//			InputStream in = response.getEntity().getContent();
//			html = convertStreamToString(in, "utf-8");
//		} catch (Exception e) {
//			logger.error("请求API:{}异常", url, e);
//		}
//		return html;
//	}
//
//	public static String getHttpClientRequest(String url, Map<String, String> haderParam) {
//
//		HttpGet httpGet = new HttpGet(url);
//		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		httpGet.addHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//		httpGet.addHeader("Connection", "Keep-Alive");
//		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
//
//		for (Map.Entry<String, String> entry : haderParam.entrySet()) {
//			httpGet.addHeader(entry.getKey(), entry.getValue());
//		}
//		String html = null;
//		try {
//			HttpResponse response = httpclient.execute(httpGet);
//			InputStream in = response.getEntity().getContent();
//			html = convertStreamToString(in, "utf-8");
//		} catch (Exception e) {
//			logger.error("请求API:{}异常", url, e);
//		}
//		return html;
//	}

	public static String getRequest(String httpUrl) {
		String html = null;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream in = connection.getInputStream();
			html = convertStreamToString(in, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}

	public static String getRequest(String httpUrl, Map<String, String> haderParam) {
		String html = null;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			for (Map.Entry<String, String> entry : haderParam.entrySet()) {
				connection.setRequestProperty(entry.getKey(), entry.getValue());
			}
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream in = connection.getInputStream();
			html = convertStreamToString(in, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}

	public static String convertStreamToString(InputStream is, String charset) throws UnsupportedEncodingException {
		InputStreamReader inreader = null;
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			inreader = new InputStreamReader(is, charset);
			reader = new BufferedReader(inreader);
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (null != inreader) {
					inreader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
