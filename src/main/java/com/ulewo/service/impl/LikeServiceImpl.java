/**
 * Project Name:ulewo-web
 * File Name:LikeServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年10月9日下午8:47:54
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

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
import com.ulewo.mapper.KnowledgeMapper;
import com.ulewo.mapper.LikeMapper;
import com.ulewo.mapper.SpitSlotMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.model.Ask;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.model.Like;
import com.ulewo.po.model.SpitSlot;
import com.ulewo.po.model.Topic;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.query.LikeQuery;
import com.ulewo.po.query.SpitSlotQuery;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.query.UpdateQuyery4ArticleCount;
import com.ulewo.service.LikeService;

/**
 * ClassName:LikeServiceImpl <br/>
 * Date:     2015年10月9日 下午8:47:54 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("likeService")
public class LikeServiceImpl implements LikeService {

	@Resource
	private LikeMapper<Like, LikeQuery> likeMapper;

	@Resource
	private SpitSlotMapper<SpitSlot, SpitSlotQuery> spitSlotMapper;

	@Resource
	private TopicMapper<Topic, TopicQuery> topicMapper;

	@Resource
	private AskMapper<Ask, AskQuery> askMapper;

	@Resource
	private KnowledgeMapper<Knowledge, KnowledgeQuery> knowledgeMapper;

	@Resource
	private BlogMapper<Blog, BlogQuery> blogMapper;

	@Override
	public List<Like> findLikeList(LikeQuery query) {
		return likeMapper.selectList(query);
	}

	@Override
	public Like findLikeByKey(Integer articleId, Integer userId, ArticleType articleType) {
		LikeQuery query = new LikeQuery();
		query.setArticleId(articleId);
		query.setUserId(userId);
		query.setArticleType(articleType);
		List<Like> likeList = likeMapper.selectList(query);
		if (!likeList.isEmpty()) {
			return likeList.get(0);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void doLike(Like like) throws BusinessException {
		if (null == like.getArticleId() || null == like.getArticleType()) {
			throw new BusinessException("参数错误");
		}
		Like _like = this.findLikeByKey(like.getArticleId(), like.getUserId(), like.getArticleType());
		if (null != _like) {
			throw new BusinessException("你已经点过赞了");
		}
		like.setCreateTime(new Date());
		//插入点赞信息
		likeMapper.insert(like);
		//更新对应的点赞数量
		//吐槽like数量
		UpdateQuyery4ArticleCount query = new UpdateQuyery4ArticleCount();
		query.setAddLikeCount(Boolean.TRUE);
		query.setTopicId(like.getArticleId());
		if (like.getArticleType() == ArticleType.SPITSLOT) {
			spitSlotMapper.updateSlotLikeCount(like.getArticleId());
		} else if (like.getArticleType() == ArticleType.TOPIC) {
			topicMapper.updateInfoCount(query);
		} else if (like.getArticleType() == ArticleType.KNOWLEDGE) {
			knowledgeMapper.updateInfoCount(query);
		} else if (like.getArticleType() == ArticleType.ASK) {
			askMapper.updateInfoCount(query);
		} else if (like.getArticleType() == ArticleType.BLOG) {
			blogMapper.updateInfoCount(query);
		} else {
			throw new BusinessException("参数错误");
		}
	}
}
