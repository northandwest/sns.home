/**
 * Project Name:ulewo-common
 * File Name:MessageMapper.java
 * Package Name:com.ulewo.mapper
 * Date:2015年11月30日下午9:25:07
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ulewo.po.enums.StatusEnum;
import com.ulewo.po.model.Message;

/**
 * ClassName:MessageMapper <br/>
 * Date:     2015年11月30日 下午9:25:07 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface MessageMapper<T, Q> extends BaseMapper<T, Q> {

	public void insertBatch(@Param("list") List<Message> list);

	public void updateBatch(@Param("status") StatusEnum status, @Param("receivedUserId") Integer receivedUserId,
			@Param("ids") Integer[] ids);

	public void deleteBatch(@Param("receivedUserId") Integer receivedUserId, @Param("ids") Integer[] ids);
}
