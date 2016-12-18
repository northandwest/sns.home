package com.ulewo.po.query;

/**
 * TODO: 增加描述
 * 
 * @author luohaili
 * @date 2015年10月30日 下午3:20:37
 * @version 0.1.0 
 */
public class AttachmentDownloadQuery {
	private Integer attachmentId;
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

}
