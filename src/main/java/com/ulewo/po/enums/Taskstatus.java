package com.ulewo.po.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * ClassName: Taskstatus
 * date: 2015年8月9日 下午12:04:32 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Taskstatus {
	NORMAL(0, "可执行"), SUSPENDED(1, "暂停");

	private int status;
	private String description;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Taskstatus(int status, String description) {
		this.status = status;
		this.description = description;
	}

	public static Taskstatus getStatusByStatus(Integer status) {
		for (Taskstatus at : Taskstatus.values()) {
			if (at.status == status) {
				return at;
			}
		}
		return null;
	}
}
