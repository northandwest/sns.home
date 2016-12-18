/**
 * Project Name:ulewo-web
 * File Name:ExamService.java
 * Package Name:com.ulewo.service
 * Date:2015年10月10日下午10:05:04
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.Exam;
import com.ulewo.po.model.SessionUser;
import com.ulewo.po.query.ExamQuery;
import com.ulewo.po.vo.PaginationResult;

/**
 * ClassName:ExamService <br/>
 * Date:     2015年10月10日 下午10:05:04 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface ExamService {
	/**
	 * 
	 * findExamListRand:(随机生成考题). <br/>
	 *
	 * @author 不错啊
	 * @param categoryId
	 * @return
	 * @since JDK 1.7
	 */
//	public List<Exam> findExamListRand(Integer categoryId);
	public List<Exam> findExamListRand(Integer categoryId,int max);

	/**
	 * 
	 * saveExam:(保存考题). <br/>
	 *
	 * @author 不错啊
	 * @param exam
	 * @param answer
	 * @param rightAnswer
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void saveExam(Exam exam, String[] answer, Integer[] rightAnswer) throws BusinessException;

	/**
	 * 
	 * doMark:(打分). <br/>
	 *
	 * @author 不错啊
	 * @param examIds
	 * @param rightAnswers
	 * @return
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public List<Exam> doMark(String examIds, String rightAnswers,SessionUser sessionUser) throws BusinessException;

	/**
	 * 
	 * findsExamUsers:(分页查询发题人). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public PaginationResult<Exam> findsExamUsers(ExamQuery query);

	/**
	 * 
	 * findExamByPage:(分页查询考题). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public PaginationResult<Exam> findExamByPage(ExamQuery query);

	/**
	 * 
	 * auditExam:(审核). <br/>
	 *
	 * @author 不错啊
	 * @param examIds
	 * @since JDK 1.7
	 */
	public void auditExam(Integer[] examIds);

	/**
	 * 
	 * deleteExam:(删除考题). <br/>
	 *
	 * @author 不错啊
	 * @param examIds
	 * @since JDK 1.7
	 */
	public void deleteExam(Integer[] examIds);

	/**
	 * 
	 * getExamById:(通过Id查询考题). <br/>
	 *
	 * @author 不错啊
	 * @param id
	 * @return
	 * @since JDK 1.7
	 */
	public Exam getExamById(Integer id);
}
