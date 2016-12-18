/**
 * Project Name:ulewo-common
 * File Name:ExamMapper.java
 * Package Name:com.ulewo.mapper
 * Date:2015年10月10日下午9:56:32
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ulewo.po.enums.StatusEnum;

/**
 * ClassName:ExamMapper <br/>
 * Date:     2015年10月10日 下午9:56:32 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Repository
public interface ExamMapper<T, Q> extends BaseMapper<T, Q> {
	public List<T> selectExamRand(Q q);

	public List<T> selectExamUsers(Q q);

	public int selectExamUsersCount(Q q);

	public void updateExamStatus(@Param("status") StatusEnum status, @Param("ids") Integer[] ids);

	public List<T> selectListWithRightAnswer(Q q);

	public void deleteBatch(@Param("ids") Integer[] ids);
}
