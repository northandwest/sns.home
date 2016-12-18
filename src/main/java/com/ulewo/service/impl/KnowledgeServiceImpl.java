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

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.KnowledgeMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.enums.MarkEnum;
import com.ulewo.po.enums.MessageType;
import com.ulewo.po.enums.OrderByEnum;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.StatusEnum;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.model.Like;
import com.ulewo.po.model.MessageParams;
//import com.ulewo.po.model.SolrBean;
import com.ulewo.po.model.User;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.query.LikeQuery;
import com.ulewo.po.query.UpdateQuyery4ArticleCount;
import com.ulewo.po.query.UserQuery;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.AttachementService;
import com.ulewo.service.KnowledgeService;
import com.ulewo.service.LikeService;
import com.ulewo.service.MessageService;
//import com.ulewo.service.SolrService;
import com.ulewo.service.UserService;
import com.ulewo.utils.ImageUtils;
import com.ulewo.utils.StringTools;

@Service("knowledgeService")
public class KnowledgeServiceImpl implements KnowledgeService {

	@Resource
	private KnowledgeMapper<Knowledge, KnowledgeQuery> knowledgeMapper;

	@Resource
	private UserMapper<User, UserQuery> userMapper;

	@Resource
	private FormatAtService formatAtService;

	@Resource
	private UserService userService;

	@Resource
	private AttachementService attachementService;

	@Resource
	private LikeService likeService;

//	@Resource
//	private SolrService solrService;

	@Resource
	private MessageService messageService;

	@Override
	public List<Knowledge> findKnowledges4Index() {
		KnowledgeQuery query = new KnowledgeQuery();
		SimplePage page = new SimplePage(0, PageSize.SIZE15.getSize(), PageSize.SIZE15.getSize());
		query.setPage(page);
		query.setStatus(StatusEnum.AUDIT);
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		List<Knowledge> list = this.knowledgeMapper.selectList(query);
		return list;
	}

	@Override
	public Knowledge getKnowledgeById(Integer knowledgeId) {
		KnowledgeQuery query = new KnowledgeQuery();
		query.setTopicId(knowledgeId);
		query.setShowContent(Boolean.TRUE);
		List<Knowledge> list = this.knowledgeMapper.selectList(query);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public PaginationResult<Knowledge> findKnowledgesByPage(KnowledgeQuery query) {
		int count = this.knowledgeMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		List<Knowledge> list = this.knowledgeMapper.selectList(query);
		PaginationResult<Knowledge> result = new PaginationResult<Knowledge>(page, list);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addKnowledge(Knowledge knowledge, Attachment attachment) throws BusinessException {
		if (knowledge.getTitle() == null || knowledge.getTitle().length() > TextLengthEnum.LENGTH_150.getLength()
				|| knowledge.getCategoryId() == null || StringUtils.isEmpty(knowledge.getContent())
				|| knowledge.getContent().length() > TextLengthEnum.LONGTEXT.getLength()) {
			throw new BusinessException("参数错误");
		}

		String content = knowledge.getContent();
		String summary = StringTools.clearHtmlTag(content);
		if (summary.length() > TextLengthEnum.LENGTH_300.getLength()) {
			summary = summary.substring(0, TextLengthEnum.LENGTH_300.getLength().intValue()) + "......";
		}
		Set<Integer> receiveUserIds = new HashSet<Integer>();
		String formatContent = formatAtService.GenerateRefererLinks(content, receiveUserIds);
		knowledge.setSummary(summary);
		knowledge.setContent(formatContent);
		String topicImage = ImageUtils.getImages(content);
		knowledge.setTopicImage(topicImage);
		String topicImageSmall = ImageUtils.createThumbnail(topicImage, true);
		knowledge.setTopicImageThum(topicImageSmall);
		Date curDate = new Date();
		knowledge.setCreateTime(curDate);
		this.knowledgeMapper.insert(knowledge);

		//发布附件
		attachment.setCreateTime(curDate);
		if (!StringTools.isEmpty(attachment.getFileName()) && !StringTools.isEmpty(attachment.getFileUrl())) {
			attachment.setTopicId(knowledge.getTopicId());
			attachment.setFileTopicType(ArticleType.KNOWLEDGE);
			attachementService.addAttachement(attachment);
		}

//		SolrBean solr = new SolrBean();
//		solr.setId(knowledge.getTopicId().toString() + "_" + ArticleType.KNOWLEDGE.getType());
//		solr.setContent(StringTools.clearHtmlTag(knowledge.getContent()));
//		solr.setTitle(knowledge.getTitle());
//		solr.setSummary(StringTools.clearHtmlTag(knowledge.getSummary()));
//		solr.setUserId(knowledge.getUserId().toString());
//		solr.setUserName(knowledge.getUserName());
//		solr.setCreateTime(DateUtils.formatDate(knowledge.getCreateTime(),
//				DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
//		solr.setArticleType(ArticleType.KNOWLEDGE.getType());
//		solrService.addArticle(solr);

		//发送消息
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(knowledge.getTopicId());
		messageParams.setArticleUserId(knowledge.getUserId());
		messageParams.setMessageType(MessageType.AT_ARTICLE_MESSAGE);
		messageParams.setArticleType(ArticleType.KNOWLEDGE);
		messageParams.setReceiveUserIds(receiveUserIds);
		messageParams.setSendUserName(knowledge.getUserName());
		messageParams.setSendUserId(knowledge.getUserId());
		messageService.createMessage(messageParams);
	}

	@Override
	public Knowledge showKnowledge(Integer knowledgeId, Integer userId) throws BusinessException {
		Knowledge knowledge = getKnowledgeById(knowledgeId);
		if (knowledge == null
				|| (knowledge.getStatus() == StatusEnum.INIT && knowledge.getUserId().intValue() != userId)) {
			throw new BusinessException("文章不存在或者已经删除");
		}
		knowledge.setFile(attachementService.getAttachmentByTopicIdAndFileType(knowledge.getTopicId(),
				ArticleType.KNOWLEDGE));
		//阅读量加1
		UpdateQuyery4ArticleCount query = new UpdateQuyery4ArticleCount();
		query.setTopicId(knowledgeId);
		query.setAddReadCount(Boolean.TRUE);
		knowledgeMapper.updateInfoCount(query);

		LikeQuery likeQuery = new LikeQuery();
		likeQuery.setArticleId(knowledgeId);
		likeQuery.setArticleType(ArticleType.KNOWLEDGE);
		List<Like> likeUsers = likeService.findLikeList(likeQuery);
		knowledge.setLikeUsers(likeUsers);
		return knowledge;
	}

	@Override
	public void auditKnowledge(Integer[] ids) {
		for (Integer id : ids) {
			Knowledge knowledge = getKnowledgeById(id);
			if (null != knowledge && knowledge.getStatus() == StatusEnum.INIT) {
				userMapper.changeUserMark(knowledge.getUserId(), MarkEnum.MARK_KNOWLEDGE.getMark());
			}
		}
		knowledgeMapper.updateStatus(StatusEnum.AUDIT, ids);
	}

	@Override
	public void deleteKnowledge(Integer[] ids) {
		knowledgeMapper.deleteBatch(ids);
	}

}
