package com.ulewo.po.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusEnum {
	INIT(0, "未审核"), AUDIT(1, "已审核");
	private Integer type;
	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	StatusEnum(Integer type, String desc) {

		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public static StatusEnum getStatusByValue(Integer type) {
		if (null == type) {
			return null;
		}
		for (StatusEnum s : StatusEnum.values()) {
			if (s.getType() == type) {
				return s;
			}
		}
		return null;
	}
}
