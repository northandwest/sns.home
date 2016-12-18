package com.ulewo.po.query;

import com.ulewo.po.enums.BlogStatusEnum;
import com.ulewo.po.enums.OrderByEnum;
import com.ulewo.utils.StringTools;

public class BlogQuery extends BaseQuery {
	private Integer userId;
	private Integer blogId;
	private String title;
	private Integer categoryId;
	private boolean showContent;
	private String startTime;
	private String endTime;
	private OrderByEnum orderBy;
	private BlogStatusEnum status;
	private String userName;

	public Integer getBlogId() {
		return blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public boolean isShowContent() {
		return showContent;
	}

	public void setShowContent(boolean showContent) {
		this.showContent = showContent;
	}

	public OrderByEnum getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderByEnum orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		if (!StringTools.isEmpty(title)) {
			title = "%" + title + "%";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public BlogStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BlogStatusEnum status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
