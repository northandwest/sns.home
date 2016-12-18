package com.ulewo.po.enums;

/**
 * 
 * ClassName: ResponseCode
 * date: 2015年8月9日 下午12:04:20 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
public enum OrderByEnum {
	/**
	 * code:200<br>
	 * 请求成功
	 */
	LAST_COMMENT_TIME_DESC_CREATE_TIME_DESC("grade desc,last_comment_time desc,create_time desc", "根据帖子等级，最后回复时间，创建时间"), CREATE_TIME_DESC(
			"create_time desc", "创建时间倒序"), CREATE_TIME_ASC("create_time asc", "创建时间顺序");
	private String orderBy;
	private String desc;

	private OrderByEnum(String orderBy, String desc) {
		this.orderBy = orderBy;
		this.desc = desc;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
