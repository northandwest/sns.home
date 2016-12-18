package com.ulewo.po.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ulewo.po.enums.SloveTypeEnum;
import com.ulewo.utils.DateUtil;

/**
 * 
 * @Title:
 * @author luohl
 */
public class Ask {
	private Integer askId; // id

	private Integer pCategoryId;

	private Integer categoryId; // 栏目ID

	private String categoryName; // 栏目名称

	private String title; // 标题

	private String summary;

	private String content; // 内容

	private Integer userId; // 作者

	private String userName;

	private String userIcon;

	private Date createTime; // 发布时间

	private String showCreateTime;

	private Integer readCount = 0; // 阅读次数

	private Integer commentCount = 0; // 回复数量

	private Integer likeCount = 0;

	private Integer collectionCount = 0;

	private String askImage;// 保存所有的图片，图片之间用|隔开

	private String askImageThum; // 缩略图

	private Integer mark = 0; //赏分

	private Integer bestAnswerId; //最佳答案id

	private Comment bestAnswer; //最佳答案

	private Integer sloveCount;

	private boolean newPost;

	private SloveTypeEnum sloveType;

	private List<Like> likeUsers = new ArrayList<Like>();

	public Integer getAskId() {
		return askId;
	}

	public void setAskId(Integer askId) {
		this.askId = askId;
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

	public String getAskImage() {
		return askImage;
	}

	public void setAskImage(String askImage) {
		this.askImage = askImage;
	}

	public String getAskImageThum() {
		return askImageThum;
	}

	public void setAskImageThum(String askImageThum) {
		this.askImageThum = askImageThum;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Integer getBestAnswerId() {
		return bestAnswerId;
	}

	public void setBestAnswerId(Integer bestAnswerId) {
		this.bestAnswerId = bestAnswerId;
	}

	public Comment getBestAnswer() {
		return bestAnswer;
	}

	public void setBestAnswer(Comment bestAnswer) {
		this.bestAnswer = bestAnswer;
	}

	public boolean isNewPost() {
		this.newPost = DateUtil.isNew(this.getCreateTime());
		return newPost;
	}

	public void setNewPost(boolean newPost) {
		this.newPost = newPost;
	}

	public Integer getSloveCount() {
		return sloveCount;
	}

	public void setSloveCount(Integer sloveCount) {
		this.sloveCount = sloveCount;
	}

	public List<Like> getLikeUsers() {
		return likeUsers;
	}

	public void setLikeUsers(List<Like> likeUsers) {
		this.likeUsers = likeUsers;
	}

	public SloveTypeEnum getSloveType() {
		return sloveType;
	}

	public void setSloveType(SloveTypeEnum sloveType) {
		this.sloveType = sloveType;
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

}
