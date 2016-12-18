/**
 * Project Name:ulewo-web
 * File Name:SpitSlotServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年9月26日下午7:58:49
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bucuoa.mark.entity.UserMark;
import com.bucuoa.mark.service.UserMarkService;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.SpitSlotCommentMapper;
import com.ulewo.mapper.SpitSlotMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.MarkEnum;
import com.ulewo.po.enums.MarkTypeEnum;
import com.ulewo.po.enums.MessageType;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.MessageParams;
import com.ulewo.po.model.SpitSlot;
import com.ulewo.po.model.SpitSlotComment;
import com.ulewo.po.query.SpitSlotQuery;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.MessageService;
import com.ulewo.service.SpitSlotService;
import com.ulewo.service.UserService;
import com.ulewo.utils.ImageUtils;
import com.ulewo.utils.StringTools;

/**
 * ClassName:SpitSlotServiceImpl <br/>
 * Date:     2015年9月26日 下午7:58:49 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("spitSlotService")
public class SpitSlotServiceImpl implements SpitSlotService {

	@Resource
	private SpitSlotMapper<SpitSlot, SpitSlotQuery> spitSlotMapper;

	@Resource
	private SpitSlotCommentMapper<SpitSlotComment, SpitSlotQuery> spitSlotCommentMapper;

	@Resource
	private UserService userService;
	@Resource
	private UserMarkService userMarkService;
	@Resource
	private FormatAtService formatAtService;

	@Resource
	private MessageService messageService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addSpitSlot(SpitSlot slot) throws BusinessException {
		String content = slot.getContent();
		if (StringTools.isEmpty(content) || content.length() > TextLengthEnum.TEXT.getLength()) {
			throw new BusinessException("参数错误");
		}
		content = StringTools.addLink(content);
		
		Set<Integer> receiveUserIds = new HashSet<Integer>();
		String formatContent = formatAtService.GenerateRefererLinks(content, receiveUserIds);
		slot.setContent(formatContent);
		String thumbnail = ImageUtils.createThumbnail(slot.getImageUrl(), false);
		slot.setImageUrlSmall(thumbnail);
		slot.setCreateTime(new Date());
		slot.setLikeCount(0);
		slot.setCommentCount(0);
		this.spitSlotMapper.insert(slot);
		
		//积分
		userService.changeMark(slot.getUserId(), MarkEnum.MARK_SPIT_SLOT.getMark());
		
		UserMark mark = new UserMark();
		mark.setCreateTime(new Date());
		mark.setMark(MarkEnum.MARK_SPIT_SLOT.getMark());
		mark.setStatus(1);
		mark.setTypex(MarkTypeEnum.MARK_SPIT_SLOT.getType());
		mark.setUserId(slot.getUserId());
		mark.setCurrent(100); //当前积分，需要计算
		try {
			userMarkService.saveEntity(mark);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//异步发送消息
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(slot.getId());
		messageParams.setArticleUserId(slot.getUserId());
		messageParams.setMessageType(MessageType.AT_ARTICLE_MESSAGE);
		messageParams.setArticleType(ArticleType.SPITSLOT);
		messageParams.setReceiveUserIds(receiveUserIds);
		messageParams.setSendUserName(slot.getUserName());
		messageParams.setSendUserId(slot.getUserId());
		messageService.createMessage(messageParams);
	}

	@Override
	public PaginationResult<SpitSlot> findSpitSlotList(SpitSlotQuery query) {
		int count = this.spitSlotMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		List<SpitSlot> list = this.spitSlotMapper.selectList(query);
		PaginationResult<SpitSlot> result = new PaginationResult<SpitSlot>(page, list);
		return result;
	}

	@Override
	public SpitSlot findSpitSlot(Integer userId, Integer id) {
		if (null == userId || null == id) {
			return null;
		}
		SpitSlotQuery query = new SpitSlotQuery();
		query.setUserId(userId);
		query.setId(id);
		List<SpitSlot> list = this.spitSlotMapper.selectList(query);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addSpitSlotComment(SpitSlotComment comment) throws BusinessException {
		String content = comment.getContent();
		if (null == content || content.length() > TextLengthEnum.TEXT.getLength() || comment.getSpitSlotId() == null) {
			throw new BusinessException("参数错误");
		}
		content = StringTools.addLink(content);
		Set<Integer> receiveUserIds = new HashSet<Integer>();
		String formatContent = formatAtService.GenerateRefererLinks(content, receiveUserIds);
		comment.setContent(formatContent);
		comment.setCreateTime(new Date());
		this.spitSlotCommentMapper.insert(comment);
		//更新吐槽评论数
		this.spitSlotMapper.updateSlotCommentCount(comment.getSpitSlotId(), 1);
		//加积分
		userService.changeMark(comment.getUserId(), MarkEnum.MARK_SPIT_SLOT_COMMENT.getMark());

		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(comment.getSpitSlotId());
		messageParams.setCommentId(comment.getId());
		messageParams.setMessageType(MessageType.COMMENT_MESSAGE);
		messageParams.setArticleType(ArticleType.SPITSLOT);
		messageParams.setReceiveUserIds(receiveUserIds);
		messageParams.setSendUserName(comment.getUserName());
		messageParams.setSendUserId(comment.getUserId());
		messageService.createMessage(messageParams);
	}

	@Override
	public List<SpitSlotComment> loadSpitSlotComment(Integer spitSlotId) {
		SpitSlotQuery query = new SpitSlotQuery();
		query.setSpitSlotId(spitSlotId);
		return spitSlotCommentMapper.selectList(query);
	}

	@Override
	public List<SpitSlot> findActiveUser4SpitSlot() {
		SpitSlotQuery query = new SpitSlotQuery();
		query.setPage(new SimplePage(0, PageSize.SIZE20.getSize()));
		return spitSlotMapper.selectActiveUser4SpitSlot(query);
	}

	@Override
	public PaginationResult<SpitSlotComment> findSpitSlotCommentList(SpitSlotQuery query) {
		int count = this.spitSlotCommentMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		List<SpitSlotComment> list = this.spitSlotCommentMapper.selectList(query);
		PaginationResult<SpitSlotComment> result = new PaginationResult<SpitSlotComment>(page, list);
		return result;
	}

	@Override
	public void deleteSpitSlot(Integer spitSlotId) {
		SpitSlotQuery query = new SpitSlotQuery();
		query.setId(spitSlotId);
		spitSlotMapper.delete(query);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteSpitSlotComment(Integer spitSlotId, Integer commentId) {
		SpitSlotQuery query = new SpitSlotQuery();
		query.setId(commentId);
		spitSlotCommentMapper.delete(query);
		spitSlotMapper.updateSlotCommentCount(spitSlotId, -1);
	}
}
