package com.ulewo.po.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TopicType {
	NORMAL(0, "普通帖"), VOTE(1, "投票帖");
	private Integer type;
	private String desc;

	TopicType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public static TopicType getTopicTypeByValue(Integer type) {
		if (null == type) {
			return null;
		}
		for (TopicType s : TopicType.values()) {
			if (s.getType() == type) {
				return s;
			}
		}
		return null;
	}
}
