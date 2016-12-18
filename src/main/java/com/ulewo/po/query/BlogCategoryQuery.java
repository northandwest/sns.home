package com.ulewo.po.query;

import com.ulewo.po.enums.BlogStatusEnum;

public class BlogCategoryQuery extends BaseQuery {
	private Integer categoryId;

	private Integer userId;

	private BlogStatusEnum blogStatus;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BlogStatusEnum getBlogStatus() {
		return blogStatus;
	}

	public void setBlogStatus(BlogStatusEnum blogStatus) {
		this.blogStatus = blogStatus;
	}

}
