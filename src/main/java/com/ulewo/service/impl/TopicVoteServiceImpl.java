/**
 * Project Name:ulewo-web
 * File Name:TopicVoteServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年10月25日下午6:20:22
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.TopicVoteDtlMapper;
import com.ulewo.mapper.TopicVoteMapper;
import com.ulewo.mapper.TopicVoteUserMapper;
import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.enums.VoteType;
import com.ulewo.po.model.TopicVote;
import com.ulewo.po.model.TopicVoteDtl;
import com.ulewo.po.model.TopicVoteUser;
import com.ulewo.po.query.TopicVoteDtlQuery;
import com.ulewo.po.query.TopicVoteQuery;
import com.ulewo.po.query.TopicVoteUserQuery;
import com.ulewo.service.TopicVoteService;
import com.ulewo.utils.DateUtil;
import com.ulewo.utils.StringTools;

/**
 * ClassName:TopicVoteServiceImpl <br/>
 * Date:     2015年10月25日 下午6:20:22 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("topicVoteService")
public class TopicVoteServiceImpl implements TopicVoteService {

	private static final Integer MAX_VOTE_TITLE_LENGTH = 10, MIN_VOTE_TITLE_LENGTH = 2;

	@Resource
	private TopicVoteMapper<TopicVote, TopicVoteQuery> topicVoteMapper;

	@Resource
	private TopicVoteDtlMapper<TopicVoteDtl, TopicVoteDtlQuery> topicVoteDtlMapper;

	@Resource
	private TopicVoteUserMapper<TopicVoteUser, TopicVoteUserQuery> topicVoteUserMapper;

	@Override
	public void addVote(TopicVote vote, String[] voteTitle) throws BusinessException {
		if (null == voteTitle || voteTitle.length > MAX_VOTE_TITLE_LENGTH
				|| MIN_VOTE_TITLE_LENGTH < MIN_VOTE_TITLE_LENGTH || StringTools.isEmpty(vote.getEndDateStr())
				|| vote.getVoteType() == null) {
			throw new BusinessException("参数错误");
		}
		//判断长度
		for (String voteItem : voteTitle) {
			if (StringTools.isEmpty(voteItem) || voteItem.length() > TextLengthEnum.LENGTH_200.getLength()) {
				throw new BusinessException("参数错误");
			}
		}

		if (vote.getVoteType() == null || StringTools.isEmpty(vote.getEndDateStr())) {
			throw new BusinessException("参数错误");
		}
		vote.setEndDate(DateUtil.parse(vote.getEndDateStr(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		topicVoteMapper.insert(vote);

		List<TopicVoteDtl> list = new ArrayList<TopicVoteDtl>();
		for (String title : voteTitle) {
			TopicVoteDtl dtl = new TopicVoteDtl();
			dtl.setTitle(title);
			dtl.setVoteId(vote.getVoteId());
			list.add(dtl);
		}
		topicVoteDtlMapper.insertBatch(list);
	}

	@Override
	public TopicVote getTopicVote(Integer topicId, Integer userId) {
		if (null == topicId) {
			return null;
		}
		boolean canVote = true;
		TopicVote topicVote = topicVoteMapper.selectVoteByTopicId(topicId);
		if (null != userId) {
			TopicVoteUserQuery query = new TopicVoteUserQuery();
			query.setUserId(userId);
			query.setVoteId(topicVote.getVoteId());
			List<TopicVoteUser> list = topicVoteUserMapper.selectList(query);
			topicVote.setVoteUser(list);

			if (!list.isEmpty() || !topicVote.getEndDate().after(new Date())) {
				canVote = false;
			}
		}
		List<TopicVoteDtl> dtlList = topicVote.getDtlList();
		int sumCount = 0;
		for (TopicVoteDtl dtl : dtlList) {
			sumCount = sumCount + dtl.getCount();
		}
		topicVote.setSumCount(sumCount);
		topicVote.setCanVote(canVote);
		return topicVote;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public TopicVote doVote(Integer voteId, Integer voteType, Integer[] voteDtlId, Integer userId, Integer topicId)
			throws BusinessException {
		if (null == voteId || null == VoteType.getTypeByValue(voteType) || voteDtlId == null
				|| (VoteType.getTypeByValue(voteType) == VoteType.SINGLE && voteDtlId.length > 1)) {
			throw new BusinessException("参数错误");
		}

		TopicVoteUserQuery query = new TopicVoteUserQuery();
		query.setUserId(userId);
		query.setVoteId(voteId);
		List<TopicVoteUser> userList = topicVoteUserMapper.selectList(query);
		if (!userList.isEmpty()) {
			throw new BusinessException("你已经投过票了");
		}

		Date curDate = new Date();
		TopicVote topicVote = topicVoteMapper.selectVoteByTopicId(topicId);
		if (topicVote == null) {
			throw new BusinessException("投票不存在");
		}

		if (!topicVote.getEndDate().after(curDate)) {
			throw new BusinessException("投票已经截止，不能投票");
		}

		List<TopicVoteUser> list = new ArrayList<TopicVoteUser>();
		List<Integer> voteDtlIdList = new ArrayList<Integer>();

		for (Integer dtlId : voteDtlId) {
			TopicVoteUser vote = new TopicVoteUser();
			vote.setUserId(userId);
			vote.setVoteDate(curDate);
			vote.setVoteDtlId(dtlId);
			list.add(vote);
			voteDtlIdList.add(dtlId);
		}
		topicVoteUserMapper.insertBatch(list);
		//更新投票点数
		topicVoteDtlMapper.updateVoteCountBatch(voteDtlIdList);

		return getTopicVote(topicId, userId);
	}
}
