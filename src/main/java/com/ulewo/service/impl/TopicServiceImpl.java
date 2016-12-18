/**
 * Project Name:ulewo-web
 * File Name:BbsServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年10月24日下午8:35:38
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

//import org.apache.http.impl.cookie.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.cache.CategoryCache;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.enums.MarkEnum;
import com.ulewo.po.enums.MessageType;
import com.ulewo.po.enums.OrderByEnum;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.enums.TopicEssence;
import com.ulewo.po.enums.TopicGrade;
import com.ulewo.po.enums.TopicType;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.Category;
import com.ulewo.po.model.Like;
import com.ulewo.po.model.MessageParams;
//import com.ulewo.po.model.SolrBean;
import com.ulewo.po.model.Topic;
import com.ulewo.po.model.TopicVote;
import com.ulewo.po.query.LikeQuery;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.query.UpdateQuyery4ArticleCount;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.AttachementService;
import com.ulewo.service.LikeService;
import com.ulewo.service.MessageService;
//import com.ulewo.service.SolrService;
import com.ulewo.service.TopicService;
import com.ulewo.service.TopicVoteService;
import com.ulewo.service.UserService;
import com.ulewo.utils.ImageUtils;
import com.ulewo.utils.StringTools;

/**
 * ClassName:BbsServiceImpl <br/>
 * Date:     2015年10月24日 下午8:35:38 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("topicService")
public class TopicServiceImpl implements TopicService {

	@Resource
	private FormatAtService formatAtService;

	@Resource
	private TopicMapper<Topic, TopicQuery> topicMapper;

	@Resource
	private UserService userService;

	@Resource
	private AttachementService attachementService;

	@Resource
	private TopicVoteService topicVoteService;

	@Resource
	private LikeService likeService;

//	@Resource
//	private SolrService solrService;

	@Resource
	private MessageService messageService;

	public List<Topic> findTopics4Index() {
		TopicQuery query = new TopicQuery();
		SimplePage page = new SimplePage(0, PageSize.SIZE15.getSize(), PageSize.SIZE15.getSize());
		query.setPage(page);
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		List<Topic> list = this.topicMapper.selectList(query);
		return list;
	}

	@Override
	public PaginationResult<Topic> findTopicsByPage(TopicQuery query) {
		int count = this.topicMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		query.setOrderBy(OrderByEnum.LAST_COMMENT_TIME_DESC_CREATE_TIME_DESC);
		List<Topic> list = this.topicMapper.selectList(query);
		PaginationResult<Topic> result = new PaginationResult<Topic>(page, list);
		return result;
	}

	@Override
	public int findCount(TopicQuery query) {
		int count = this.topicMapper.selectCount(query);
		return count;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addTopic(Topic topic, TopicVote vote, String[] voteTitle, Attachment attachment)
			throws BusinessException {
		if (topic.getTopicType() == null || topic.getTitle() == null
				|| topic.getTitle().length() > TextLengthEnum.LENGTH_150.getLength() || topic.getCategoryId() == null
				|| topic.getpCategoryId() == null || StringTools.isEmpty(topic.getContent())
				|| topic.getContent().length() > TextLengthEnum.LONGTEXT.getLength()) {
			throw new BusinessException("参数错误");
		}

		String content = topic.getContent();
		String summary = StringTools.clearHtmlTag(content);
		if (summary.length() > TextLengthEnum.LENGTH_200.getLength()) {
			summary = summary.substring(0, TextLengthEnum.LENGTH_200.getLength().intValue()) + "......";
		}
		Set<Integer> receiveUserIds = new HashSet<Integer>();
		String formatContent = formatAtService.GenerateRefererLinks(content, receiveUserIds);
		topic.setSummary(summary);
		topic.setContent(formatContent);
		String topicImage = ImageUtils.getImages(content);
		topic.setTopicImage(topicImage);
		String topicImageSmall = ImageUtils.createThumbnail(topicImage, true);
		topic.setTopicImageThum(topicImageSmall);

		Date curDate = new Date();
		topic.setCreateTime(curDate);
		topic.setLastCommentTime(curDate);
		//插入帖子
		this.topicMapper.insert(topic);
		//加分
		this.userService.changeMark(topic.getUserId(), MarkEnum.MARK_TOPIC.getMark());
		// 判断是否是投票帖子
		if (topic.getTopicType() == TopicType.VOTE) {
			vote.setTopicId(topic.getTopicId());
			topicVoteService.addVote(vote, voteTitle);
		}
		//发布附件
		attachment.setCreateTime(curDate);
		if (!StringTools.isEmpty(attachment.getFileName()) && !StringTools.isEmpty(attachment.getFileUrl())) {
			attachment.setTopicId(topic.getTopicId());
			attachment.setFileTopicType(ArticleType.TOPIC);
			attachementService.addAttachement(attachment);
		}

//		SolrBean solr = new SolrBean();
//		solr.setId(topic.getTopicId().toString() + "_" + ArticleType.TOPIC.getType());
//		solr.setContent(StringTools.clearHtmlTag(topic.getContent()));
//		solr.setTitle(topic.getTitle());
//		solr.setSummary(StringTools.clearHtmlTag(topic.getSummary()));
//		solr.setUserId(topic.getUserId().toString());
//		solr.setUserName(topic.getUserName());
//		solr.setCreateTime(DateUtils.formatDate(topic.getCreateTime(),
//				DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
//		solr.setArticleType(ArticleType.TOPIC.getType());
//		solrService.addArticle(solr);

		//发送消息
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(topic.getTopicId());
		messageParams.setArticleUserId(topic.getUserId());
		messageParams.setMessageType(MessageType.AT_ARTICLE_MESSAGE);
		messageParams.setArticleType(ArticleType.TOPIC);
		messageParams.setReceiveUserIds(receiveUserIds);
		messageParams.setSendUserName(topic.getUserName());
		messageParams.setSendUserId(topic.getUserId());
		messageService.createMessage(messageParams);
	}

	@Override
	public Topic showTopic(Integer topicId) throws BusinessException {
		Topic topic = getTopicById(topicId);
		if (topic == null) {
			throw new BusinessException("帖子不存在或者已经删除");
		}
		topic.setFile(attachementService.getAttachmentByTopicIdAndFileType(topic.getTopicId(), ArticleType.TOPIC));
		//阅读量加1
		UpdateQuyery4ArticleCount query = new UpdateQuyery4ArticleCount();
		query.setAddReadCount(Boolean.TRUE);
		query.setTopicId(topicId);
		topicMapper.updateInfoCount(query);

		LikeQuery likeQuery = new LikeQuery();
		likeQuery.setArticleId(topicId);
		likeQuery.setArticleType(ArticleType.TOPIC);
		List<Like> likeUsers = likeService.findLikeList(likeQuery);
		topic.setLikeUsers(likeUsers);
		return topic;
	}

	@Override
	public Topic getTopicById(Integer topicId) {
		TopicQuery query = new TopicQuery();
		query.setShowContent(Boolean.TRUE);
		query.setTopicId(topicId);
		List<Topic> topicList = topicMapper.selectList(query);
		if (topicList.isEmpty()) {
			return null;
		}
		return topicList.get(0);
	}

	@Override
	public List<Topic> findActiveUsers() {
		return topicMapper.selectActiveUsers();
	}

	public Category getPcategoryByPCategoryId(Integer pcategoryId) {
		List<Category> list = CategoryCache.getBbsCategory();
		for (Category c : list) {
			if (c.getCategoryId().intValue() == pcategoryId) {
				return c;
			}
		}
		return null;
	}

	public Category getPcategoryByCategoryId(Integer categoryId) {
		List<Category> list = CategoryCache.getBbsCategory();
		for (Category c : list) {
			List<Category> children = c.getChildren();
			for (Category ch : children) {
				if (ch.getCategoryId().intValue() == categoryId) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public void deleteTopic(Integer[] ids) {
		topicMapper.deleteBatch(ids);
	}

	@Override
	public void changeCategory(Integer topicId, Integer pCategoryId, Integer categoryId) {
		Topic topic = new Topic();
		topic.setTopicId(topicId);
		topic.setCategoryId(categoryId);
		topic.setpCategoryId(pCategoryId);
		topicMapper.update(topic);
	}

	@Override
	public void setTop(Integer topicId) {
		Topic topic = new Topic();
		topic.setTopicId(topicId);
		topic.setGrade(TopicGrade.TOP.getType());
		topicMapper.update(topic);
	}

	@Override
	public void cancelTop(Integer topicId) {
		Topic topic = new Topic();
		topic.setTopicId(topicId);
		topic.setGrade(TopicGrade.NOTTOP.getType());
		topicMapper.update(topic);
	}

	@Override
	public void setEssence(Integer topicId) {
		Topic topic = new Topic();
		topic.setTopicId(topicId);
		topic.setEssence(TopicEssence.Essence.getType());
		topicMapper.update(topic);
	}

	@Override
	public void cancelEssence(Integer topicId) {
		Topic topic = new Topic();
		topic.setTopicId(topicId);
		topic.setEssence(TopicEssence.NOESSENCE.getType());
		topicMapper.update(topic);
	}
}
