package com.ulewo.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
//import org.apache.http.impl.cookie.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bucuoa.ucenter.model.UlewoUser;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.AskMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.MessageType;
import com.ulewo.po.enums.OrderByEnum;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.SloveTypeEnum;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.Ask;
import com.ulewo.po.model.Comment;
import com.ulewo.po.model.Like;
import com.ulewo.po.model.MessageParams;
//import com.ulewo.po.model.SolrBean;
import com.ulewo.po.model.User;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.query.LikeQuery;
import com.ulewo.po.query.UpdateQuyery4ArticleCount;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.AskService;
import com.ulewo.service.CommentService;
import com.ulewo.service.LikeService;
import com.ulewo.service.MessageService;
//import com.ulewo.service.SolrService;
import com.ulewo.service.UserService;
import com.ulewo.utils.ImageUtils;
import com.ulewo.utils.StringTools;

@Service("askService")
public class AskServiceImpl implements AskService {

	@Resource
	private AskMapper<Ask, AskQuery> askMapper;

	@Resource
	private FormatAtService formatAtService;

	@Resource
	private UserService userService;

	@Resource
	private CommentService commentService;

	@Resource
	private LikeService likeService;

	@Resource
	private MessageService messageService;


	public List<Ask> findAsks4Index() {
		AskQuery query = new AskQuery();
		SimplePage page = new SimplePage(0, PageSize.SIZE15.getSize(), PageSize.SIZE15.getSize());
		query.setPage(page);
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		List<Ask> list = this.askMapper.selectList(query);
		return list;
	}

	@Override
	public PaginationResult<Ask> findAskByPage(AskQuery query) {
		int count = this.askMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		List<Ask> list = this.askMapper.selectList(query);
		PaginationResult<Ask> result = new PaginationResult<Ask>(page, list);
		return result;
	}

	@Override
	public int findCount(AskQuery query) {
		return this.askMapper.selectCount(query);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addAsk(Ask ask) throws BusinessException {
		if (ask.getTitle() == null || ask.getTitle().length() > TextLengthEnum.LENGTH_150.getLength()
				|| StringUtils.isEmpty(ask.getContent())
				|| ask.getContent().length() > TextLengthEnum.LONGTEXT.getLength()) {
			throw new BusinessException("参数错误");
		}

		if (ask.getMark() != null && ask.getMark() > 0) {
			UlewoUser user = userService.findUserByUserId(ask.getUserId().toString());
			if (user.getMark() < ask.getMark()) {
				throw new BusinessException("你的积分不够");
			}
		}

		String content = ask.getContent();
		String summary = StringTools.clearHtmlTag(content);
		if (summary.length() > TextLengthEnum.LENGTH_200.getLength()) {
			summary = summary.substring(0, TextLengthEnum.LENGTH_200.getLength().intValue()) + "......";
		}
		Set<Integer> receiveUserIds = new HashSet<Integer>();
		String formatContent = formatAtService.GenerateRefererLinks(content, receiveUserIds);
		ask.setSummary(summary);
		ask.setContent(formatContent);
		String askImage = ImageUtils.getImages(content);
		ask.setAskImage(askImage);
		String askImageThum = ImageUtils.createThumbnail(askImage, true);
		ask.setAskImageThum(askImageThum);
		Date curDate = new Date();
		ask.setCreateTime(curDate);
		this.askMapper.insert(ask);

//		//添加搜索索引
//		SolrBean solr = new SolrBean();
//		solr.setId(ask.getAskId().toString() + "_" + ArticleType.ASK_Z.getType());
//		solr.setContent(StringTools.clearHtmlTag(ask.getContent()));
//		solr.setTitle(ask.getTitle());
//		solr.setSummary(StringTools.clearHtmlTag(ask.getSummary()));
//		solr.setUserId(ask.getUserId().toString());
//		solr.setUserName(ask.getUserName());
//		solr.setCreateTime(DateUtils.formatDate(ask.getCreateTime(),
//				DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
//		solr.setArticleType(ArticleType.ASK_Z.getType());
//		solrService.addArticle(solr);
		//发送消息
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(ask.getAskId());
		messageParams.setArticleUserId(ask.getUserId());
		messageParams.setMessageType(MessageType.AT_ARTICLE_MESSAGE);
		messageParams.setArticleType(ArticleType.ASK);
		messageParams.setReceiveUserIds(receiveUserIds);
		messageParams.setSendUserName(ask.getUserName());
		messageParams.setSendUserId(ask.getUserId());
		messageService.createMessage(messageParams);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void setBestAnswer(Integer bestAnswerId, Integer askId, Integer userId) throws BusinessException {
		Ask ask = getAskById(askId);
		if (null == ask || ask.getSloveType() == SloveTypeEnum.SLOVED) {
			throw new BusinessException("请求参数错误");
		}
		Comment comment = commentService.getCommentById(bestAnswerId);
		if (null == comment || comment.getArticleId().intValue() != askId) {
			throw new BusinessException("请求参数错误");
		}

		this.askMapper.updateBestAnswer(bestAnswerId, comment.getUserId(), comment.getUserName(), SloveTypeEnum.SLOVED,
				askId, userId);

		//减去作者的分，给回答者
		UlewoUser topicUser = userService.findUserByUserId(ask.getUserId().toString());
		if (topicUser.getMark() < ask.getMark()) {
			userService.changeMark(ask.getUserId(), -topicUser.getMark());
		}
		//给回答者加分
		userService.changeMark(bestAnswerId, ask.getMark());

		//发送消息
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(askId);
		messageParams.setMessageType(MessageType.ADOPT_ANSWER);
		messageParams.setArticleType(ArticleType.ASK);
		Set<Integer> receiveUserIds = new HashSet<Integer>();
		receiveUserIds.add(comment.getUserId());
		messageParams.setReceiveUserIds(receiveUserIds);
		messageParams.setSendUserName(topicUser.getUserName());
		messageParams.setSendUserId(topicUser.getUserId().intValue());
		messageService.createMessage(messageParams);
	}

	@Override
	public Ask getAskById(Integer askId) {
		AskQuery query = new AskQuery();
		query.setAskId(askId);
		query.setShowContent(Boolean.TRUE);
		List<Ask> list = this.askMapper.selectList(query);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Ask showAsk(Integer askId) throws BusinessException {
		Ask ask = getAskById(askId);
		if (ask == null) {
			throw new BusinessException("问题不存在或者已经删除");
		}
		//查询最佳答案
		if (ask.getSloveType() == SloveTypeEnum.SLOVED) {
			ask.setBestAnswer(commentService.getCommentById(ask.getBestAnswerId()));
		}

		//阅读量加1
		UpdateQuyery4ArticleCount query = new UpdateQuyery4ArticleCount();
		query.setAddReadCount(Boolean.TRUE);
		query.setTopicId(askId);
		this.askMapper.updateInfoCount(query);

		LikeQuery likeQuery = new LikeQuery();
		likeQuery.setArticleId(askId);
		likeQuery.setArticleType(ArticleType.ASK);
		List<Like> likeUsers = likeService.findLikeList(likeQuery);
		ask.setLikeUsers(likeUsers);
		return ask;
	}

	public List<Ask> selectTopUsers() {
		return this.askMapper.selectTopUsers(PageSize.SIZE10.getSize());
	}

	@Override
	public void deleteBatch(Integer[] ids) {
		this.askMapper.deleteBatch(ids);
	}

}
