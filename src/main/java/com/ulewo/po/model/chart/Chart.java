package com.ulewo.po.model.chart;

import java.util.ArrayList;
import java.util.List;

public class Chart {
	private ToolTip tooltip;
	private Legend legend;
	private Toolbox toolbox;
	private boolean calculable = true;
	private List<YAxix> yAxis = new ArrayList<YAxix>();
	private List<XAxis> xAxis = new ArrayList<XAxis>();
	private List<Series> series = new ArrayList<Series>();

	public Chart(List<String> xAxis, String[] legendData) {
		this.tooltip = new ToolTip();
		this.toolbox = new Toolbox();
		this.yAxis.add(new YAxix());
		this.legend = new Legend(legendData);
		this.xAxis.add(new XAxis(xAxis));
	}

	public ToolTip getTooltip() {
		return tooltip;
	}

	public Toolbox getToolbox() {
		return toolbox;
	}

	public boolean isCalculable() {
		return calculable;
	}

	public List<XAxis> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<XAxis> xAxis) {
		this.xAxis = xAxis;
	}

	public List<YAxix> getyAxis() {
		return yAxis;
	}

	public Legend getLegend() {
		return legend;
	}

	public void setLegend(Legend legend) {
		this.legend = legend;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

}
