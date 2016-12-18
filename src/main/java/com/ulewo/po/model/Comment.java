package com.ulewo.po.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ulewo.po.enums.ArticleType;
import com.ulewo.utils.DateUtil;
import com.ulewo.utils.Emotions;
import com.ulewo.utils.Emotions.Dev;

public class Comment {
	private Integer id;

	private Integer pid;

	private Integer articleId; // 主题帖Id

	private Integer userId;

	private String userName;

	private String userIcon;

	private String content;

	private String showContent;

	private Date createTime;

	private String showCreateTime;

	private ArticleType articleType;

	private List<Comment> children = new ArrayList<Comment>();

	private Integer pageNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getShowContent() {
		this.showContent = Emotions.formatEmotion(this.content, Dev.WEB);
		return showContent;
	}

	public void setShowContent(String showContent) {
		this.showContent = showContent;
	}

	public String getShowCreateTime() {
		this.showCreateTime = DateUtil.friendly_time(this.createTime);
		return showCreateTime;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public List<Comment> getChildren() {
		return children;
	}

	public void setChildren(List<Comment> children) {
		this.children = children;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
}
