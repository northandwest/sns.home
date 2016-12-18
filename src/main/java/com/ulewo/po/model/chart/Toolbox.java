package com.ulewo.po.model.chart;

import java.util.ArrayList;
import java.util.List;
public class Toolbox {
	private boolean show = true;
	private Feature feature;

	public Toolbox() {
		feature = new Feature();
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

}

class Feature {
	private Mark mark;
	private DataView dataView;
	private MagicType magicType;
	private Restore restore;
	private SaveAsImage saveAsImage;

	public Feature() {
		mark = new Mark();
		dataView = new DataView();
		magicType = new MagicType();
		restore = new Restore();
		saveAsImage = new SaveAsImage();
	}

	public Mark getMark() {
		return mark;
	}

	public void setMark(Mark mark) {
		this.mark = mark;
	}

	public DataView getDataView() {
		return dataView;
	}

	public void setDataView(DataView dataView) {
		this.dataView = dataView;
	}

	public MagicType getMagicType() {
		return magicType;
	}

	public void setMagicType(MagicType magicType) {
		this.magicType = magicType;
	}

	public Restore getRestore() {
		return restore;
	}

	public void setRestore(Restore restore) {
		this.restore = restore;
	}

	public SaveAsImage getSaveAsImage() {
		return saveAsImage;
	}

	public void setSaveAsImage(SaveAsImage saveAsImage) {
		this.saveAsImage = saveAsImage;
	}

}

class Mark {
	private boolean show = true;

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

}

class DataView {
	private boolean show = true;
	private boolean readOnly = true;

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}

class MagicType {
	private boolean show = true;
	private List<String> type = new ArrayList<String>();

	public MagicType() {
		type.add("line");
		type.add("bar");
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

}

class Restore {
	private boolean show = true;

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

}

class SaveAsImage {
	private boolean show = true;

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

}
