/**
 * Project Name:ulewo-common
 * File Name:AttachementQuery.java
 * Package Name:com.ulewo.po.query
 * Date:2015年10月25日下午7:23:21
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.query;

import com.ulewo.po.enums.ArticleType;

/**
 * ClassName:AttachementQuery <br/>
 * Date:     2015年10月25日 下午7:23:21 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class AttachementQuery {
	private Integer attachmentId;
	private Integer topicId;
	private ArticleType fileTopicType;

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public ArticleType getFileTopicType() {
		return fileTopicType;
	}

	public void setFileTopicType(ArticleType fileTopicType) {
		this.fileTopicType = fileTopicType;
	}

	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

}
