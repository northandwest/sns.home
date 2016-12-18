package com.ulewo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.AskMapper;
import com.ulewo.mapper.BlogMapper;
import com.ulewo.mapper.CommentMapper;
import com.ulewo.mapper.KnowledgeMapper;
import com.ulewo.mapper.MessageMapper;
import com.ulewo.mapper.SpitSlotMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.MessageType;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.StatusEnum;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.Ask;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.Comment;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.model.Message;
import com.ulewo.po.model.MessageParams;
import com.ulewo.po.model.SpitSlot;
import com.ulewo.po.model.Topic;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.query.CommentQuery;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.query.MessageQuery;
import com.ulewo.po.query.SpitSlotQuery;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.MessageService;
import com.ulewo.utils.ServerUtils;
import com.ulewo.utils.StringTools;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource
	private SpitSlotMapper<SpitSlot, SpitSlotQuery> spitSlotMapper;

	@Resource
	private TopicMapper<Topic, TopicQuery> topicMapper;

	@Resource
	private KnowledgeMapper<Knowledge, KnowledgeQuery> knowledgeMapper;

	@Resource
	private AskMapper<Ask, AskQuery> askMapper;

	@Resource
	private BlogMapper<Blog, BlogQuery> blogMapper;

	@Resource
	private MessageMapper<Message, MessageQuery> messageMapper;

	@Resource
	private CommentMapper<Comment, CommentQuery> commentMapper;

	@Override
	@Async
	public void createMessage(MessageParams params) {
		MessageType type = params.getMessageType();
		switch (type) {
		case AT_ARTICLE_MESSAGE:
			atMessage(params);
			break;
		case COMMENT_MESSAGE:
			commentMessage(params);
			break;
		case ADOPT_ANSWER:
			adoptAnswer(params);
			break;
		case SYSTEM_MARK:
			systemMessage(params);
			break;
		case SYSTEM_WARN:
			systemMessage(params);
			break;
		default:
			break;
		}
	}

	private void atMessage(MessageParams params) {
		List<Message> messageList = new ArrayList<Message>();
		Set<Integer> receiveUserIds = params.getReceiveUserIds();
		Message message = null;
		//接受人中，过滤自己
		removeUser(receiveUserIds, params.getSendUserId());
		Date curDate = new Date();
		for (Integer receiveUserId : receiveUserIds) {
			message = new Message();
			message.setReceivedUserId(receiveUserId);
			message.setUrl(getUrl4AtAndComment(params));
			message.setDescription("<span class='message-user-name'>" + params.getSendUserName() + "</span>" + "在【"
					+ params.getArticleType().getDesc() + "】中提到了你");
			message.setCreateTime(curDate);
			messageList.add(message);
		}
		if (!messageList.isEmpty()) {
			messageMapper.insertBatch(messageList);
		}
	}

	private void commentMessage(MessageParams params) {
		List<Message> messageList = new ArrayList<Message>();
		Set<Integer> receiveUserIds = params.getReceiveUserIds();
		ArticleType articleType = params.getArticleType();
		Integer articleId = params.getArticleId();
		Integer articleUserId = null;
		String title = "";
		if (articleType == ArticleType.SPITSLOT) {
			SpitSlotQuery query = new SpitSlotQuery();
			query.setId(articleId);
			SpitSlot spitSlot = spitSlotMapper.selectList(query).get(0);
			articleUserId = spitSlot.getUserId();
			String spitContent = StringTools.clearHtmlTag(spitSlot.getContent());
			spitContent = spitContent.length() > TextLengthEnum.LENGTH_200.getLength() ? spitContent.substring(0,
					TextLengthEnum.LENGTH_200.getLength().intValue()) : spitContent;
			title = spitContent;
		} else if (articleType == ArticleType.TOPIC) {
			TopicQuery query = new TopicQuery();
			query.setTopicId(articleId);
			Topic topic = topicMapper.selectList(query).get(0);
			articleUserId = topic.getUserId();
			title = topic.getTitle();
		} else if (articleType == ArticleType.KNOWLEDGE) {
			KnowledgeQuery query = new KnowledgeQuery();
			query.setTopicId(articleId);
			Knowledge knowledge = knowledgeMapper.selectList(query).get(0);
			articleUserId = knowledge.getUserId();
			title = knowledge.getTitle();
		} else if (articleType == ArticleType.ASK) {
			AskQuery query = new AskQuery();
			query.setAskId(articleId);
			Ask ask = askMapper.selectList(query).get(0);
			articleUserId = ask.getUserId();
			title = ask.getTitle();
		} else if (articleType == ArticleType.BLOG) {
			BlogQuery query = new BlogQuery();
			query.setBlogId(articleId);
			Blog blog = blogMapper.selectList(query).get(0);
			articleUserId = blog.getUserId();
			title = blog.getTitle();
		}
		//给父评论者发消息
		Comment comment = commentMapper.selectCommentNoChildren(params.getCommentId());
		//二级回复就给一级回复者发消息
		if (null != comment && comment.getPid() != null && comment.getPid() != 0) {
			comment = commentMapper.selectCommentNoChildren(comment.getPid());
			if (null != comment) {
				receiveUserIds.add(comment.getUserId());
			}
		} else {//一级回复给主题作者发消息
			receiveUserIds.add(articleUserId);
		}
		params.setArticleUserId(articleUserId);
		Message message = null;
		Date curDate = new Date();
		//接受人中，过滤自己
		removeUser(receiveUserIds, params.getSendUserId());
		for (Integer receiveUserId : receiveUserIds) {
			message = new Message();
			message.setReceivedUserId(receiveUserId);
			message.setUrl(getUrl4AtAndComment(params) + "#" + params.getPageNo() + "_" + params.getCommentId());
			message.setDescription("<span class='message-user-name'>" + params.getSendUserName() + "</span>" + "在【"
					+ articleType.getDesc() + "】<span class='message-title'>" + title + "</span>中回复了你");
			message.setCreateTime(curDate);
			messageList.add(message);
		}

		if (!messageList.isEmpty()) {
			messageMapper.insertBatch(messageList);
		}
	}

	private void systemMessage(MessageParams params) {
		List<Message> messageList = new ArrayList<Message>();
		Set<Integer> receiveUserIds = params.getReceiveUserIds();
		Message message = null;
		Date curDate = new Date();
		for (Integer receiveUserId : receiveUserIds) {
			message = new Message();
			message.setReceivedUserId(receiveUserId);
			message.setUrl("");
			message.setDescription("<span class='message-user-name'>系统消息：</span>" + params.getMessage());
			message.setCreateTime(curDate);
			messageList.add(message);
		}
		if (!messageList.isEmpty()) {
			messageMapper.insertBatch(messageList);
		}
	}

	private void removeUser(Set<Integer> receiveUserIds, Integer userId) {
		Iterator<Integer> iterator = receiveUserIds.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().intValue() == userId) {
				iterator.remove();
			}
		}
	}

	private void adoptAnswer(MessageParams params) {
		List<Message> messageList = new ArrayList<Message>();
		Set<Integer> receiveUserIds = params.getReceiveUserIds();
		Message message = null;
		//接受人中，过滤自己
		removeUser(receiveUserIds, params.getSendUserId());
		Date curDate = new Date();
		for (Integer receiveUserId : receiveUserIds) {
			message = new Message();
			message.setReceivedUserId(receiveUserId);
			message.setUrl(getUrl4AtAndComment(params));
			message.setDescription("<span class='message-user-name'>" + params.getSendUserName() + "</span>" + "在【"
					+ params.getArticleType().getDesc() + "】中将你的答案设置为最佳答案了");
			message.setCreateTime(curDate);
			messageList.add(message);
		}
		if (!messageList.isEmpty()) {
			messageMapper.insertBatch(messageList);
		}
	}

	private String getUrl4AtAndComment(MessageParams params) {
		String url = "";
		ArticleType articleType = params.getArticleType();
		if (articleType == ArticleType.BLOG || articleType == ArticleType.SPITSLOT) {
			url = "/user/" + params.getArticleUserId() + "/" + articleType.getUrl();
		} else {
			url = articleType.getUrl();
		}
		url = ServerUtils.getDomain() + url + "/" + params.getArticleId();
		return url;
	}

	@Override
	public Message getMessageById(Integer id, Integer userId) {
		MessageQuery query = new MessageQuery();
		query.setId(id);
		query.setReceivedUserId(userId);
		List<Message> list = messageMapper.selectList(query);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PaginationResult<Message> findMessageByPage(MessageQuery query) {
		int count = this.messageMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		List<Message> list = this.messageMapper.selectList(query);
		PaginationResult<Message> result = new PaginationResult<Message>(page, list);
		return result;
	}

	@Override
	public int findMessageCount(MessageQuery query) {
		return this.messageMapper.selectCount(query);
	}

	@Override
	public void update(Integer[] ids, Integer userId) throws BusinessException {
		if (null == ids || ids.length == 0) {
			throw new BusinessException("参数错误");
		}
		messageMapper.updateBatch(StatusEnum.AUDIT, userId, ids);
	}

	@Override
	public void delMessage(Integer userId, Integer[] ids) throws BusinessException {
		if (null == ids || ids.length == 0) {
			throw new BusinessException("参数错误");
		}
		messageMapper.deleteBatch(userId, ids);
	}

}
