package com.ulewo.po.model;

import java.util.Date;

import com.ulewo.po.enums.ArticleType;

public class Attachment {
	private Integer attachmentId;

	private Integer topicId;

	private String fileName;

	private String fileUrl;

	private Integer downloadMark;

	private Integer downloadCount;

	private Date createTime;

	private ArticleType fileTopicType;

	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Integer getDownloadMark() {
		return downloadMark;
	}

	public void setDownloadMark(Integer downloadMark) {
		this.downloadMark = downloadMark;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ArticleType getFileTopicType() {
		return fileTopicType;
	}

	public void setFileTopicType(ArticleType fileTopicType) {
		this.fileTopicType = fileTopicType;
	}

}
