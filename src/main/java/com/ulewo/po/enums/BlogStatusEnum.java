package com.ulewo.po.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BlogStatusEnum {
	DRAFTS(0, "草稿"), PASS(1, "已发布");
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

	BlogStatusEnum(Integer type, String desc) {

		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public static BlogStatusEnum getStatusByValue(Integer type) {
		if (null == type) {
			return null;
		}
		for (BlogStatusEnum likeType : BlogStatusEnum.values()) {
			if (likeType.getType() == type) {
				return likeType;
			}
		}
		return null;
	}
}
