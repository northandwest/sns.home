package com.ulewo.po.query;

import com.ulewo.po.enums.ArticleType;

/**
 * TODO: 增加描述
 * 
 * @author luohaili
 * @date 2015年9月28日 下午6:02:23
 * @version 0.1.0 
 */
public class LikeQuery extends BaseQuery {
	private Integer articleId;

	private Integer userId;

	private ArticleType articleType;

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

}
