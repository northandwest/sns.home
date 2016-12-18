package com.ulewo.po.model.chart;

import java.util.List;

public class XAxis {

	private String type = "category";
	private List<String> data;

	public XAxis(List<String> data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
}
