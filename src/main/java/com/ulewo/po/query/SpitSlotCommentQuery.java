package com.ulewo.po.query;

/**
 * TODO: 增加描述
 * 
 * @author luohaili
 * @date 2015年9月28日 下午5:34:04
 * @version 0.1.0 
 */
public class SpitSlotCommentQuery extends BaseQuery {
	private Integer id;
	private String startTime;
	private String endTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
