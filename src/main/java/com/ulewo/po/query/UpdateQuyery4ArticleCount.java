/**
 * Project Name:ulewo-common
 * File Name:Update4ArticleCount.java
 * Package Name:com.ulewo.po.model
 * Date:2015年10月31日上午9:51:33
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.query;

/**
 * ClassName:Update4ArticleCount <br/>
 * Date:     2015年10月31日 上午9:51:33 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class UpdateQuyery4ArticleCount {
	private boolean addReadCount;

	private boolean addLikeCount;

	private boolean addCommentCount;

	private boolean addCollectionCount;

	private Integer topicId;

	private Integer count = 1;

	public boolean isAddReadCount() {
		return addReadCount;
	}

	public void setAddReadCount(boolean addReadCount) {
		this.addReadCount = addReadCount;
	}

	public boolean isAddLikeCount() {
		return addLikeCount;
	}

	public void setAddLikeCount(boolean addLikeCount) {
		this.addLikeCount = addLikeCount;
	}

	public boolean isAddCommentCount() {
		return addCommentCount;
	}

	public void setAddCommentCount(boolean addCommentCount) {
		this.addCommentCount = addCommentCount;
	}

	public boolean isAddCollectionCount() {
		return addCollectionCount;
	}

	public void setAddCollectionCount(boolean addCollectionCount) {
		this.addCollectionCount = addCollectionCount;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
