package com.ulewo.po.model;

import java.util.Set;

import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.MessageType;

public class MessageParams {
	private Integer articleId;
	private Integer articleUserId;
	private MessageType messageType;
	private ArticleType articleType;
	private Set<Integer> receiveUserIds;
	private Integer sendUserId;
	private String sendUserName;
	private Integer commentId;
	private Integer pageNo;
	private String message;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public Set<Integer> getReceiveUserIds() {
		return receiveUserIds;
	}

	public void setReceiveUserIds(Set<Integer> receiveUserIds) {
		this.receiveUserIds = receiveUserIds;
	}

	public Integer getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Integer sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public Integer getArticleUserId() {
		return articleUserId;
	}

	public void setArticleUserId(Integer articleUserId) {
		this.articleUserId = articleUserId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
