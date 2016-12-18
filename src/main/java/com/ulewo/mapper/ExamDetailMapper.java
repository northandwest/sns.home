/**
 * Project Name:ulewo-common
 * File Name:ExamDetailMapper.java
 * Package Name:com.ulewo.mapper
 * Date:2015年10月10日下午9:57:42
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ulewo.po.model.Exam;

/**
 * ClassName:ExamDetailMapper <br/>
 * Date:     2015年10月10日 下午9:57:42 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Repository
public interface ExamDetailMapper<T, Q> extends BaseMapper<T, Q> {
	/**
	 * 
	 * insertBatch:(批量插入). <br/>
	 *
	 * @author 不错啊
	 * @param list
	 * @since JDK 1.7
	 */
	public void insertBatch(List<T> list);

	/**
	 * 
	 * selectListWithRightAnswer:(查询考题答案和正确答案). <br/>
	 *
	 * @author 不错啊
	 * @param examId
	 * @return
	 * @since JDK 1.7
	 */
	public List<Exam> selectListWithRightAnswer(@Param("examId") Integer examId);
}
