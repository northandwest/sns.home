/**
 * Project Name:ulewo-common
 * File Name:AllowPost.java
 * Package Name:com.ulewo.po.enums
 * Date:2015年10月17日下午12:59:04
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.enums;

/**
 * ClassName:AllowPost <br/>
 * Date:     2015年10月17日 下午12:59:04 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public enum TopicGrade {
	TOP(1, "置顶"), NOTTOP(0, "普通帖子");
	private Integer type;
	private String desc;

	TopicGrade(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
