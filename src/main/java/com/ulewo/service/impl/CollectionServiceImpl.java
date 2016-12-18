package com.ulewo.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.AskMapper;
import com.ulewo.mapper.BlogMapper;
import com.ulewo.mapper.CollectionMapper;
import com.ulewo.mapper.KnowledgeMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.OrderByEnum;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.Ask;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.Collection;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.model.Topic;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.query.CollectionQuery;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.query.UpdateQuyery4ArticleCount;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.CollectionService;
import com.ulewo.utils.StringTools;

@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {

	@Resource
	private CollectionMapper<Collection, CollectionQuery> collectionMapper;

	@Resource
	private TopicMapper<Topic, TopicQuery> topicMapper;

	@Resource
	private KnowledgeMapper<Knowledge, KnowledgeQuery> knowledgeMapper;

	@Resource
	private AskMapper<Ask, AskQuery> askMapper;

	@Resource
	private BlogMapper<Blog, BlogQuery> blogMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addCollection(Collection collection) throws BusinessException {
		if (null == collection.getArticleId() || null == collection.getArticleType()
				|| StringTools.isEmpty(collection.getTitle())
				|| collection.getTitle().length() > TextLengthEnum.LENGTH_300.getLength()) {
			throw new BusinessException("参数错误");
		}
		Collection _collection = this.findCollectionByKey(collection.getArticleId(), collection.getUserId(),
				collection.getArticleType());
		if (null != _collection) {
			throw new BusinessException("你已经收藏过了");
		}
		collection.setCreateTime(new Date());
		//插入收藏信息
		//博客要保存文章发布者
		if (collection.getArticleType() == ArticleType.BLOG) {
			BlogQuery blogQuery = new BlogQuery();
			blogQuery.setBlogId(collection.getArticleId());
			Blog blog = blogMapper.selectList(blogQuery).get(0);
			collection.setArticleUserId(blog.getUserId());
		}
		collectionMapper.insert(collection);
		//更新对应的收藏数量
		UpdateQuyery4ArticleCount query = new UpdateQuyery4ArticleCount();
		query.setAddCollectionCount(Boolean.TRUE);
		query.setTopicId(collection.getArticleId());
		if (collection.getArticleType() == ArticleType.TOPIC) {
			topicMapper.updateInfoCount(query);
		} else if (collection.getArticleType() == ArticleType.KNOWLEDGE) {
			knowledgeMapper.updateInfoCount(query);
		} else if (collection.getArticleType() == ArticleType.ASK) {
			askMapper.updateInfoCount(query);
		} else if (collection.getArticleType() == ArticleType.BLOG) {
			blogMapper.updateInfoCount(query);
		} else {
			throw new BusinessException("参数错误");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void delCollection(Collection collection) throws BusinessException {
		if (collection.getArticleId() == null || collection.getArticleType() == null) {
			throw new BusinessException("参数错误");
		}
		CollectionQuery collectionQuery = new CollectionQuery();
		collectionQuery.setArticleId(collection.getArticleId());
		collectionQuery.setUserId(collection.getUserId());
		collectionQuery.setArticleType(collection.getArticleType());
		int count = collectionMapper.delete(collectionQuery);
		if (count == 0) {
			throw new BusinessException("删除的文章不存在");
		}
		UpdateQuyery4ArticleCount query = new UpdateQuyery4ArticleCount();
		query.setAddCollectionCount(Boolean.TRUE);
		query.setTopicId(collection.getArticleId());
		query.setCount(-1);
		if (collection.getArticleType() == ArticleType.TOPIC) {
			topicMapper.updateInfoCount(query);
		} else if (collection.getArticleType() == ArticleType.KNOWLEDGE) {
			knowledgeMapper.updateInfoCount(query);
		} else if (collection.getArticleType() == ArticleType.ASK) {
			askMapper.updateInfoCount(query);
		} else if (collection.getArticleType() == ArticleType.BLOG) {
			blogMapper.updateInfoCount(query);
		} else {
			throw new BusinessException("参数错误");
		}
	}

	@Override
	public Collection findCollectionByKey(Integer articleId, Integer userId, ArticleType articleType) {
		CollectionQuery query = new CollectionQuery();
		query.setArticleId(articleId);
		query.setUserId(userId);
		query.setArticleType(articleType);
		List<Collection> list = collectionMapper.selectList(query);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PaginationResult<Collection> findCollectionByPage(CollectionQuery query) {
		int count = this.collectionMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		List<Collection> list = this.collectionMapper.selectList(query);
		PaginationResult<Collection> result = new PaginationResult<Collection>(page, list);
		return result;
	}

}
