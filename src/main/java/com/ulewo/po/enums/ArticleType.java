package com.ulewo.po.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ulewo.utils.StringTools;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ArticleType {
	TOPIC("T", "/bbs", "讨论区"), BLOG("B", "blog", "博客"), SPITSLOT("S", "/spitslot", "吐槽"), KNOWLEDGE("K", "/knowledge",
			"知识库"), ASK("A", "/ask", "问答"), ASK_Z("Z", "/ask", "问答搜索");
	;
	private String type;
	private String desc;
	private String url;

	ArticleType(String type, String url, String desc) {
		this.type = type;
		this.desc = desc;
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static ArticleType getArticleTypeByType(String type) {
		if (StringTools.isEmpty(type)) {
			return null;
		}
		for (ArticleType likeType : ArticleType.values()) {
			if (likeType.getType().equals(type)) {
				return likeType;
			}
		}
		return null;
	}
}
