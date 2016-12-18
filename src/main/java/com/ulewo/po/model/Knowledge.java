package com.ulewo.po.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ulewo.cache.CategoryCache;
import com.ulewo.po.enums.StatusEnum;
import com.ulewo.utils.DateUtil;

/**
 * 
 * @Title:
 * @author luohl
 */
public class Knowledge {
	private Integer topicId; // id

	private Integer categoryId; // 栏目ID

	private Integer pCategoryId;

	private String categoryName; // 栏目名称

	private String pCategoryName; // 栏目名称

	private String title; // 标题

	private String summary;

	private String content; // 内容

	private Integer userId; // 作者

	private String userName;

	private String userIcon;

	private Date createTime; // 发布时间

	private String showCreateTime;

	private StatusEnum status;

	private String topicImage;// 保存所有的图片，图片之间用|隔开

	private String topicImageThum; // 缩略图

	private Integer readCount = 0; // 阅读次数

	private Integer commentCount = 0; // 回复数量

	private Integer likeCount = 0;

	private Integer collectionCount = 0;

	private boolean newPost;

	private List<Like> likeUsers = new ArrayList<Like>();

	private Attachment file;

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getpCategoryId() {
		return pCategoryId;
	}

	public void setpCategoryId(Integer pCategoryId) {
		this.pCategoryId = pCategoryId;
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

	public String getShowCreateTime() {
		this.showCreateTime = DateUtil.friendly_time(this.createTime);
		return showCreateTime;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
	}

	public String getTopicImage() {
		return topicImage;
	}

	public void setTopicImage(String topicImage) {
		this.topicImage = topicImage;
	}

	public String getTopicImageThum() {
		return topicImageThum;
	}

	public void setTopicImageThum(String topicImageThum) {
		this.topicImageThum = topicImageThum;
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
		this.newPost = DateUtil.isNew(this.getCreateTime());
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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Attachment getFile() {
		return file;
	}

	public void setFile(Attachment file) {
		this.file = file;
	}

	public String getCategoryName() {
		Category category = CategoryCache.getCategoryById(categoryId);
		if (null != category) {
			return category.getName();
		}
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getpCategoryName() {
		Category category = CategoryCache.getCategoryById(pCategoryId);
		if (null != category) {
			return category.getName();
		}
		return pCategoryName;
	}

	public void setpCategoryName(String pCategoryName) {
		this.pCategoryName = pCategoryName;
	}

}
