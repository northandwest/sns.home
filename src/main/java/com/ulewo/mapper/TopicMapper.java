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

import com.ulewo.po.query.UpdateQuyery4ArticleCount;

/**
 * ClassName:TopicMapper <br/>
 * Date:     2015年10月25日 下午6:10:32 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface TopicMapper<T, Q> extends BaseMapper<T, Q> {
	public void updateInfoCount(UpdateQuyery4ArticleCount query);

	/**
	 * 
	 * selectTopicInfo4Category:(获取板块帖子信息)
	 * @author luohaili
	 * @param q
	 * @return
	 * @since JDK 1.7
	 */
	public List<T> selectTopicInfo4Category(Q q);

	/**
	 * 
	 * selectActiveUsers:(查询活跃用户). <br/>
	 *
	 * @author 不错啊
	 * @return
	 * @since JDK 1.7
	 */
	public List<T> selectActiveUsers();

	/**
	 * 
	 * deleteBatch:(批量删除)
	 * @author luohaili
	 * @param ids
	 * @since JDK 1.7
	 */
	public void deleteBatch(@Param("ids") Integer[] ids);
}
