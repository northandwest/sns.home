package com.ulewo.po.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 投票类型
 * ClassName: VoteType
 * date: 2015年10月24日 下午7:57:32 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum VoteType {
	SINGLE(1, "单选"), MANAGE(2, "多选");
	private Integer type;
	private String desc;

	VoteType(Integer type, String desc) {
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

	public static VoteType getTypeByValue(Integer type) {
		if (null == type) {
			return null;
		}
		for (VoteType s : VoteType.values()) {
			if (s.getType() == type) {
				return s;
			}
		}
		return null;
	}
}
