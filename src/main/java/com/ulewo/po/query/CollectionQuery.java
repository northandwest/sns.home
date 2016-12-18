package com.ulewo.po.query;

import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.OrderByEnum;

/**
 * TODO: 增加描述
 * 
 * @author luohaili
 * @date 2015年9月28日 下午6:02:23
 * @version 0.1.0 
 */
public class CollectionQuery extends BaseQuery {
	private Integer articleId;

	private Integer userId;

	private ArticleType articleType;

	private String title;

	private OrderByEnum orderBy;

	private String startTime;

	private String endTime;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public OrderByEnum getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderByEnum orderBy) {
		this.orderBy = orderBy;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
