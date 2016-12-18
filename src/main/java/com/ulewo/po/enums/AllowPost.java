/**
 * Project Name:ulewo-common
 * File Name:AllowPost.java
 * Package Name:com.ulewo.po.enums
 * Date:2015年10月17日下午12:59:04
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.enums;

import com.ulewo.utils.StringTools;

/**
 * ClassName:AllowPost <br/>
 * Date:     2015年10月17日 下午12:59:04 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public enum AllowPost {
	ALLOW("Y", "允许发布"), NOTALLOW("N", "不允许发帖");

	private String type;
	private String desc;

	AllowPost(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static AllowPost getAllowPostByType(String type) {
		if (StringTools.isEmpty(type)) {
			return null;
		}
		for (AllowPost at : AllowPost.values()) {
			if (at.getType().equals(type)) {
				return at;
			}
		}
		return null;
	}
}
