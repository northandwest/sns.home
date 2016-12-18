/**
 * Project Name:ulewo-common
 * File Name:TopicMapper.java
 * Package Name:com.ulewo.mapper
 * Date:2015年10月25日下午6:10:32
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ulewo.po.model.TopicVoteDtl;

/**
 * ClassName:TopicMapper <br/>
 * Date:     2015年10月25日 下午6:10:32 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface TopicVoteDtlMapper<T, Q> extends BaseMapper<T, Q> {
	public void insertBatch(@Param("dtlList") List<TopicVoteDtl> list);

	public void updateVoteCountBatch(@Param("list") List<Integer> voteDtlIdList);
}
