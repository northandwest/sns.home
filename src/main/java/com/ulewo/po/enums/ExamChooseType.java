package com.ulewo.po.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExamChooseType {
	SINGLE(1, "单选"), MANAGE(2, "多选");
	private Integer type;
	private String desc;

	ExamChooseType(Integer type, String desc) {
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

	public static ExamChooseType getExamChooseTypeByValue(Integer type) {
		if (null == type) {
			return null;
		}
		for (ExamChooseType likeType : ExamChooseType.values()) {
			if (likeType.getType() == type) {
				return likeType;
			}
		}
		return null;
	}
}
