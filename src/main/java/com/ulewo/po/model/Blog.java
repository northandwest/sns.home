package com.ulewo.po.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ulewo.po.enums.BlogStatusEnum;
import com.ulewo.utils.DateUtil;

/**
 * 
 * @Title:
 * @Description: 博客
 * @author luohl
 * @date 2013-1-4
 * @version V1.0
 */
public class Blog {
	private Integer blogId;

	private Integer userId;

	private String userIcon;

	private String userName;

	private Integer categoryId;

	private String categoryName;

	private String title;

	private String summary;

	private String content;

	private Integer readCount = 0;

	private Integer commentCount;

	private Date createTime;

	private String showCreateTime;

	private String blogImage;// 保存所有的图片，图片之间用|隔开

	private String blogImageThum; // 缩略图

	private Integer likeCount = 0;

	private Integer collectionCount = 0;

	private boolean newPost;

	private List<Like> likeUsers = new ArrayList<Like>();

	private Attachment file;

	private BlogStatusEnum status;

	public Integer getBlogId() {
		return blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getShowCreateTime() {
		this.showCreateTime = DateUtil.friendly_time(this.createTime);
		return showCreateTime;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
	}

	public String getBlogImage() {
		return blogImage;
	}

	public void setBlogImage(String blogImage) {
		this.blogImage = blogImage;
	}

	public String getBlogImageThum() {
		return blogImageThum;
	}

	public void setBlogImageThum(String blogImageThum) {
		this.blogImageThum = blogImageThum;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(Integer collectionCount) {
		this.collectionCount = collectionCount;
	}

	public boolean isNewPost() {
		this.newPost = DateUtil.isNew(createTime);
		return newPost;
	}

	public void setNewPost(boolean newPost) {
		this.newPost = newPost;
	}

	public List<Like> getLikeUsers() {
		return likeUsers;
	}

	public void setLikeUsers(List<Like> likeUsers) {
		this.likeUsers = likeUsers;
	}

	public Attachment getFile() {
		return file;
	}

	public void setFile(Attachment file) {
		this.file = file;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BlogStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BlogStatusEnum status) {
		this.status = status;
	}

}
