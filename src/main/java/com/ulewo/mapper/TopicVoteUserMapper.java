/**
 * Project Name:ulewo-common
 * File Name:TopicVoteUserMapper.java
 * Package Name:com.ulewo.mapper
 * Date:2015年10月31日下午8:34:56
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ulewo.po.model.TopicVoteUser;

/**
 * ClassName:TopicVoteUserMapper <br/>
 * Date:     2015年10月31日 下午8:34:56 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface TopicVoteUserMapper<T, Q> extends BaseMapper<T, Q> {
	public void insertBatch(@Param("list") List<TopicVoteUser> list);
}
