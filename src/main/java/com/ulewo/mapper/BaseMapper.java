/**
 * Project Name:ulewo-common
 * File Name:BaseMapper.java
 * Package Name:com.ulewo.mapper
 * Date:2015年9月19日下午4:47:36
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.mapper;

import java.util.List;

/**
 * ClassName:BaseMapper <br/>
 * Date:     2015年9月19日 下午4:47:36 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface BaseMapper<T, Q> {
	public int insert(T t);

	public List<T> selectList(Q q);

	public Integer selectCount(Q q);

	public int update(T t);

	public int delete(Q q);
}
