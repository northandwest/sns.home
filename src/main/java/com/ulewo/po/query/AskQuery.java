package com.ulewo.po.query;

import com.ulewo.po.enums.OrderByEnum;
import com.ulewo.po.enums.SloveTypeEnum;

public class AskQuery extends BaseQuery {
	private Integer userId;
	private Integer askId;
	private Integer categoryId;
	private boolean showContent;
	private OrderByEnum orderBy;
	private SloveTypeEnum sloveType;
	private String startDate;
	private String endDate;
	private String userName;

	public Integer getAskId() {
		return askId;
	}

	public void setAskId(Integer askId) {
		this.askId = askId;
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

	public SloveTypeEnum getSloveType() {
		return sloveType;
	}

	public void setSloveType(SloveTypeEnum sloveType) {
		this.sloveType = sloveType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
