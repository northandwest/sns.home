package com.ulewo.po.model.chart;

import java.util.ArrayList;
import java.util.List;

public class Series {

	private String name;
	private String type = "bar";
	private List<Integer> data = new ArrayList<Integer>();

	public Series(String name, List<Integer> data) {
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

}
