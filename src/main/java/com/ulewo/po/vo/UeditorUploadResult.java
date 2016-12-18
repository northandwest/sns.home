/**
 * Project Name:ulewo-common
 * File Name:UeditorUploadResult.java
 * Package Name:com.ulewo.po.vo
 * Date:2015年10月19日下午9:27:14
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.vo;

/**
 * ClassName:UeditorUploadResult <br/>
 * Date:     2015年10月19日 下午9:27:14 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class UeditorUploadResult {
	private String original;
	private String name;
	private String url;
	private long size;
	private String type;
	private String state;

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
