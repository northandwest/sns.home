package com.ulewo.po.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SloveTypeEnum {
	NOTSLOVE(0, "未解决"), SLOVED(1, "已解决");
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

	SloveTypeEnum(Integer type, String desc) {

		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public static SloveTypeEnum getTypeByValue(Integer type) {
		if (null == type) {
			return null;
		}
		for (SloveTypeEnum s : SloveTypeEnum.values()) {
			if (s.getType() == type) {
				return s;
			}
		}
		return null;
	}
}
