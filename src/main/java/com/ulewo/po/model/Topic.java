package com.ulewo.po.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ulewo.cache.CategoryCache;
import com.ulewo.po.enums.TopicType;
import com.ulewo.utils.DateUtil;
import com.ulewo.utils.StringTools;

/**
 * 
 * @Title:
 * @Description: 主题文章bean
 * @author luohl
 */
public class Topic {
	private Integer topicId; // id

	private TopicType topicType;// 类型 0:普通帖子 1:投票贴

	private Integer pCategoryId;

	private Integer categoryId; // 栏目ID

	private String title; // 标题

	private String summary;

	private String content; // 内容

	private Integer userId; // 作者

	private String userName;

	private String userIcon;

	private Date createTime; // 发布时间

	private String showTime;

	private Date lastCommentTime; // 最后回复时间

	private Integer readCount = 0; // 阅读次数

	private Integer commentCount = 0; // 回复数量

	private Integer grade; // 帖子等级

	private Integer essence; // 精华

	private String topicImage;// 保存所有的图片，图片之间用|隔开

	private String topicImageThum; // 缩略图

	private String categoryName; // 栏目名称

	private String pCategoryName; // 栏目名称

	private Attachment file;

	private boolean newPost;

	private String[] topicImageThumArray;

	private String showCreateTime;

	private Integer likeCount = 0;

	private Integer collectionCount = 0;

	private Integer topicCount;

	private List<Like> likeUsers = new ArrayList<Like>();

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public TopicType getTopicType() {
		return topicType;
	}

	public void setTopicType(TopicType topicType) {
		this.topicType = topicType;
	}

	public Integer getpCategoryId() {
		return pCategoryId;
	}

	public void setpCategoryId(Integer pCategoryId) {
		this.pCategoryId = pCategoryId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	public Date getLastCommentTime() {
		return lastCommentTime;
	}

	public void setLastCommentTime(Date lastCommentTime) {
		this.lastCommentTime = lastCommentTime;
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getEssence() {
		return essence;
	}

	public void setEssence(Integer essence) {
		this.essence = essence;
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

	public String getCategoryName() {
		Category category = CategoryCache.getCategoryById(categoryId);
		if (null != category) {
			return category.getName();
		}
		return categoryName;
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

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Attachment getFile() {
		return file;
	}

	public void setFile(Attachment file) {
		this.file = file;
	}

	public boolean isNewPost() {
		this.newPost = DateUtil.isNew(this.getCreateTime());
		return newPost;
	}

	public void setNewPost(boolean newPost) {
		this.newPost = newPost;
	}

	public String[] getTopicImageThumArray() {
		if (!StringTools.isEmpty(topicImageThum)) {
			topicImageThumArray = topicImageThum.split("\\|");
		}
		return topicImageThumArray;
	}

	public void setTopicImageThumArray(String[] topicImageThumArray) {
		this.topicImageThumArray = topicImageThumArray;
	}

	public String getShowCreateTime() {
		this.showCreateTime = DateUtil.friendly_time(createTime);
		return showCreateTime;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
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

	public List<Like> getLikeUsers() {
		return likeUsers;
	}

	public void setLikeUsers(List<Like> likeUsers) {
		this.likeUsers = likeUsers;
	}

	public String getShowTime() {
		if (null != this.getCreateTime()) {
			this.showTime = DateUtil.friendly_time(this.getCreateTime());
		}
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public Integer getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

}
