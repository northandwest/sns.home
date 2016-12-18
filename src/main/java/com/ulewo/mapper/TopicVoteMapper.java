/**
 * Project Name:ulewo-common
 * File Name:TopicMapper.java
 * Package Name:com.ulewo.mapper
 * Date:2015年10月25日下午6:10:32
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.mapper;

import com.ulewo.po.model.TopicVote;

/**
 * ClassName:TopicMapper <br/>
 * Date:     2015年10月25日 下午6:10:32 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface TopicVoteMapper<T, Q> extends BaseMapper<T, Q> {
	public TopicVote selectVoteByTopicId(Integer topicId);
}
