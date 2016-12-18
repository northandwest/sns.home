/**
 * Project Name:ulewo-web
 * File Name:VoteService.java
 * Package Name:com.ulewo.service
 * Date:2015年10月25日下午4:23:43
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.TopicVote;

/**
 * ClassName:VoteService <br/>
 * Date:     2015年10月25日 下午4:23:43 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface TopicVoteService {
	/**
	 * addVote:(新增调查). <br/>
	 *
	 * @author 不错啊
	 * @param vote
	 * @param voteTitle
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void addVote(TopicVote vote, String[] voteTitle) throws BusinessException;

	/**
	 * 
	 * getTopicVote:(获取调查). <br/>
	 *
	 * @author 不错啊
	 * @param topicId
	 * @return
	 * @since JDK 1.7
	 */
	public TopicVote getTopicVote(Integer topicId, Integer userId);

	/**
	 * 投票
	 * doVote:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author 不错啊
	 * @param voteId
	 * @param voteType
	 * @param voteDtlId
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public TopicVote doVote(Integer voteId, Integer voteType, Integer[] voteDtlId, Integer userId, Integer topicId)
			throws BusinessException;
}
