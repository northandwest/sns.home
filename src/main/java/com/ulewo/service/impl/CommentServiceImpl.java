package com.ulewo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.AskMapper;
import com.ulewo.mapper.BlogMapper;
import com.ulewo.mapper.CommentMapper;
import com.ulewo.mapper.KnowledgeMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.MarkEnum;
import com.ulewo.po.enums.MessageType;
import com.ulewo.po.enums.OrderByEnum;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.Ask;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.Comment;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.model.MessageParams;
import com.ulewo.po.model.Topic;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.query.CommentQuery;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.query.UpdateQuyery4ArticleCount;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.CommentService;
import com.ulewo.service.MessageService;
import com.ulewo.service.UserService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper<Comment, CommentQuery> commentMapper;

	@Resource
	private TopicMapper<Topic, TopicQuery> topicMapper;

	@Resource
	private FormatAtService formatAtService;

	@Resource
	private UserService userService;

	@Resource
	private AskMapper<Ask, AskQuery> askMapper;

	@Resource
	private KnowledgeMapper<Knowledge, KnowledgeQuery> knowledgeMapper;

	@Resource
	private BlogMapper<Blog, BlogQuery> blogMapper;

	@Resource
	private MessageService messageService;

	@Override
	public Comment getCommentById(Integer commentId) {
		CommentQuery query = new CommentQuery();
		query.setCommentId(commentId);
		List<Comment> list = this.commentMapper.selectList(query);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public PaginationResult<Comment> findCommentByPage(CommentQuery query) {
		if (query.getArticleId() == null || query.getArticleType() == null) {
			return new PaginationResult<Comment>(new SimplePage(), new ArrayList<Comment>());
		}
		int count = this.commentMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		query.setOrderBy(OrderByEnum.CREATE_TIME_ASC);
		List<Comment> list = this.commentMapper.selectList(query);
		PaginationResult<Comment> result = new PaginationResult<Comment>(page, list);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addComment(Comment comment) throws BusinessException {
		String content = comment.getContent();
		if (StringUtils.isEmpty(content) || content.length() > TextLengthEnum.LONGTEXT.getLength()
				|| comment.getArticleId() == null || comment.getArticleType() == null) {
			throw new BusinessException("参数错误");
		}
		Integer pid = comment.getPid();
		pid = pid == null ? 0 : pid;
		//二级回复长度不能超过500
		if (pid != 0 && content.length() > TextLengthEnum.LENGTH_500.getLength()) {
			throw new BusinessException("参数错误");
		}
		Set<Integer> receiveUserIds = new HashSet<Integer>();
		String formatContent = formatAtService.GenerateRefererLinks(content, receiveUserIds);

		comment.setContent(formatContent);
		comment.setCreateTime(new Date());
		this.commentMapper.insert(comment);
		//加分
		this.userService.changeMark(comment.getUserId(), MarkEnum.COMMENT.getMark());

		//更新主题帖的回帖数
		UpdateQuyery4ArticleCount query = new UpdateQuyery4ArticleCount();
		query.setAddCommentCount(Boolean.TRUE);
		query.setTopicId(comment.getArticleId());
		if (comment.getArticleType() == ArticleType.TOPIC) {//讨论区回复
			topicMapper.updateInfoCount(query);
			Topic temp = new Topic();
			temp.setTopicId(comment.getArticleId());
			temp.setLastCommentTime(new Date());
			topicMapper.update(temp);
		} else if (comment.getArticleType() == ArticleType.KNOWLEDGE) {
			knowledgeMapper.updateInfoCount(query);
		} else if (comment.getArticleType() == ArticleType.ASK) {
			askMapper.updateInfoCount(query);
		} else if (comment.getArticleType() == ArticleType.BLOG) {
			blogMapper.updateInfoCount(query);
		} else {
			throw new BusinessException("参数错误");
		}

		MessageParams messageParams = new MessageParams();
		messageParams.setPageNo(comment.getPageNo());
		messageParams.setArticleId(comment.getArticleId());
		messageParams.setCommentId(comment.getId());
		messageParams.setMessageType(MessageType.COMMENT_MESSAGE);
		messageParams.setArticleType(comment.getArticleType());
		messageParams.setReceiveUserIds(receiveUserIds);
		messageParams.setSendUserName(comment.getUserName());
		messageParams.setSendUserId(comment.getUserId());
		messageService.createMessage(messageParams);
	}

	@Override
	public PaginationResult<Comment> findAllCommentByPage(CommentQuery query) {
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		int count = this.commentMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		List<Comment> list = this.commentMapper.selectListNoChildren(query);
		PaginationResult<Comment> result = new PaginationResult<Comment>(page, list);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteComment(Integer commentId, ArticleType articleType) {
		Comment comment = this.commentMapper.selectCommentNoChildren(commentId);
		if (null != comment) {
			int count = this.commentMapper.deleteComment(comment.getId());
			UpdateQuyery4ArticleCount query = new UpdateQuyery4ArticleCount();
			query.setAddCommentCount(Boolean.TRUE);
			query.setCount(-count);
			query.setTopicId(comment.getArticleId());
			if (articleType == ArticleType.ASK) {
				askMapper.updateInfoCount(query);
			} else if (articleType == ArticleType.BLOG) {
				blogMapper.updateInfoCount(query);
			} else if (articleType == ArticleType.KNOWLEDGE) {
				knowledgeMapper.updateInfoCount(query);
			} else if (articleType == ArticleType.TOPIC) {
				topicMapper.updateInfoCount(query);
			}
		}

	}
}
