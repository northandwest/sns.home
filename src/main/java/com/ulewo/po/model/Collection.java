package com.ulewo.po.model;

import java.util.Date;

import com.ulewo.po.enums.ArticleType;
import com.ulewo.utils.DateUtil;

public class Collection {
	private Integer userId;

	private Integer articleId;

	private Integer articleUserId;

	private String title;

	private Date createTime;

	private String showCreateTime;

	private ArticleType articleType;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getShowCreateTime() {
		this.showCreateTime = DateUtil.friendly_time(createTime);
		return showCreateTime;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public Integer getArticleUserId() {
		return articleUserId;
	}

	public void setArticleUserId(Integer articleUserId) {
		this.articleUserId = articleUserId;
	}

}
