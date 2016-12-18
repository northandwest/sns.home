/**
 * Project Name:ulewo-web
 * File Name:ExamServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年10月10日下午10:07:28
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bucuoa.exam.entity.UserExamResult;
import com.bucuoa.exam.entity.UserExamTest;
import com.bucuoa.exam.service.UserExamResultService;
import com.bucuoa.exam.service.UserExamTestService;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.ExamDetailMapper;
import com.ulewo.mapper.ExamMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.po.enums.ExamChooseType;
import com.ulewo.po.enums.MarkEnum;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.StatusEnum;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.Exam;
import com.ulewo.po.model.ExamDetail;
import com.ulewo.po.model.SessionUser;
import com.ulewo.po.model.User;
import com.ulewo.po.query.ExamQuery;
import com.ulewo.po.query.UserQuery;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.ExamService;
import com.ulewo.utils.StringTools;

/**
 * ClassName:ExamServiceImpl <br/>
 * Date:     2015年10月10日 下午10:07:28 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("examService")
public class ExamServiceImpl implements ExamService {

	//最小答案数
	private final int MIN_ANSWER_COUNT = 2;
	//最大答案数
	private final int MAX_ANSWER_COUNT = 10;
	//最大正确答案数
	private final int MAX_RIGHT_ANSWER_COUNT = 1;

	private final int RIGHT_ANSWER = 1, NOT_RIGHT_ANSWER = 0;
	@Resource
	private ExamMapper<Exam, ExamQuery> examMapper;
	@Resource
	private ExamDetailMapper<ExamDetail, ExamQuery> examDetailMapper;
	@Resource
	private UserMapper<User, UserQuery> userMapper;
	@Resource
	private UserExamResultService userExamResultService;
	@Resource
	private UserExamTestService userExamTestService;
	
	@Override
	public List<Exam> findExamListRand(Integer categoryId,int max) {
		if (null != categoryId && categoryId == 0) {
			categoryId = null;
		}
		ExamQuery query = new ExamQuery();
		query.setCategoryId(categoryId);
		query.setExamMaxTitle(max);
		query.setStatus(StatusEnum.AUDIT);
		query.setShowAnalyse(Boolean.FALSE);
		return examMapper.selectExamRand(query);
	}
	
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void saveExam(Exam exam, String[] answers, Integer[] rightAnswers) throws BusinessException {
		if (null == exam.getCategoryId()//判断分类
				|| StringTools.isEmpty(exam.getExamTitle())//判断标题
				|| exam.getExamTitle().length() > TextLengthEnum.TEXT.getLength()
				|| exam.getChooseType() == null//判断题目类型
				|| answers == null//判断答案
				|| answers.length < MIN_ANSWER_COUNT
				|| answers.length > MAX_ANSWER_COUNT
				|| rightAnswers == null//判断正确答案
				|| (rightAnswers.length > MAX_RIGHT_ANSWER_COUNT && exam.getChooseType() == ExamChooseType.SINGLE)
				|| rightAnswers.length == answers.length
				|| (!StringTools.isEmpty(exam.getAnalyse()) && exam.getAnalyse().length() > TextLengthEnum.TEXT
						.getLength())) {//判断题目分析
			throw new BusinessException("参数错误");
		}

		//判断正确答案参数是否正确
		int answer_length = answers.length;
		for (Integer index : rightAnswers) {
			if (index < 0 || index > answer_length) {
				throw new BusinessException("参数错误");
			}
		}
		exam.setCreateTime(new Date());
		//插入考题
		examMapper.insert(exam);

		//将正确答案放到一个map中
		Map<Integer, Integer> rightAnswerMap = new HashMap<Integer, Integer>();
		for (Integer rightAnswer : rightAnswers) {
			rightAnswerMap.put(rightAnswer, rightAnswer);
		}

		List<ExamDetail> examDetails = new ArrayList<ExamDetail>();
		//插入答案
		for (int i = 0, _len = answers.length; i < _len; i++) {
			ExamDetail detail = new ExamDetail();
			String answer = answers[i];
			if (StringUtils.isEmpty(answer) || answer.length() > TextLengthEnum.LENGTH_500.getLength()) {
				throw new BusinessException("参数错误");
			}
			detail.setAnswer(answer);
			detail.setExamId(exam.getId());
			if (rightAnswerMap.get(i) == null) {
				detail.setIsRightAnswer(NOT_RIGHT_ANSWER);
			} else {
				detail.setIsRightAnswer(RIGHT_ANSWER);
			}
			examDetails.add(detail);
		}
		if (examDetails.isEmpty()) {
			throw new BusinessException("参数错误");
		}
		examDetailMapper.insertBatch(examDetails);
	}

	public List<Exam> doMark(String examIdstr, String rightAnswerstr,SessionUser sessionUser) throws BusinessException {
		if (StringUtils.isEmpty(examIdstr) || StringUtils.isEmpty(rightAnswerstr)) {
			throw new BusinessException("参数错误");
		}
		String[] examIds = examIdstr.split(",");
		String[] rightAnswers = rightAnswerstr.split(",");
		if (null == examIds || rightAnswers == null ) {
			throw new BusinessException("参数错误");
		}
		Map<String, String> rightAnswerMap = new HashMap<String, String>();
		for (String rightAnswer : rightAnswers) {
			if (null != rightAnswerMap) {
				rightAnswerMap.put(rightAnswer, rightAnswer);
			}
		}
		ExamQuery query = new ExamQuery();
		query.setStatus(StatusEnum.AUDIT);
		query.setExamIds(examIds);
		query.setShowAnalyse(true);
		List<Exam> examList = examMapper.selectListWithRightAnswer(query);
		
		long test_id = 0;
		Date now = new Date();
		if(examList.size() > 1) //考试和学习的区别
		{
			UserExamTest test = new UserExamTest();
			test.setCreateTime(now);
			test.setStatusx(1);
			test.setTotalQuestion(examList.size());
			test.setUserId(sessionUser.getUserId());
			test.setUserIcon(sessionUser.getUserIcon());
			test.setUserName(sessionUser.getUserName());
			
			try {
				final UserExamTest userExamTest = userExamTestService.saveWithId(test);
				test_id = userExamTest.getId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (Exam exam : examList) {
			List<ExamDetail> detailList = exam.getExamDetails();
			List<Integer> correctAnswerIds = new ArrayList<Integer>();
			exam.setCorrectAnswerIds(correctAnswerIds);
			boolean isCorrect = Boolean.TRUE;
			for (ExamDetail detail : detailList) {
				//正确答案
				if (detail.getIsRightAnswer() == RIGHT_ANSWER) {
					correctAnswerIds.add(detail.getId());
					if (rightAnswerMap.get(detail.getId().toString()) == null) {
						isCorrect = false;
					}
				} else if (rightAnswerMap.get(detail.getId().toString()) != null) {
					isCorrect = false;
				}
			}
			exam.setCorrect(isCorrect);
			
			int statusx = 0;
			UserExamResult entity = new UserExamResult();
			
			entity.setCreateTime(now);
			entity.setQuestionId(exam.getId());
			if(isCorrect)
			{
				statusx = 1;
			}
			entity.setStatusx(statusx);
			
			entity.setUserId(sessionUser.getUserId());
			entity.setUserIcon(sessionUser.getUserIcon());
			entity.setUserName(sessionUser.getUserName());
			entity.setTestId(test_id);
			try {
				userExamResultService.saveEntity(entity);//.save(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return examList;
	}

	@Override
	public PaginationResult<Exam> findsExamUsers(ExamQuery query) {
		query.setStatus(StatusEnum.AUDIT);
		int count = this.examMapper.selectExamUsersCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		List<Exam> list = this.examMapper.selectExamUsers(query);
		PaginationResult<Exam> result = new PaginationResult<Exam>(page, list);
		return result;
	}

	@Override
	public PaginationResult<Exam> findExamByPage(ExamQuery query) {
		int count = this.examMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		List<Exam> list = this.examMapper.selectList(query);
		PaginationResult<Exam> result = new PaginationResult<Exam>(page, list);
		return result;
	}

	@Override
	public Exam getExamById(Integer id) {
		ExamQuery query = new ExamQuery();
		query.setExamId(id);
		List<Exam> list = this.examMapper.selectList(query);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void auditExam(Integer[] examIds) {
		for (Integer examId : examIds) {
			Exam exam = getExamById(examId);
			if (null != exam && exam.getStatus() == StatusEnum.INIT) {
				userMapper.changeUserMark(exam.getUserId(), MarkEnum.MARK_EXAM.getMark());
			}
		}
		examMapper.updateExamStatus(StatusEnum.AUDIT, examIds);
	}

	@Override
	public void deleteExam(Integer[] examIds) {
		examMapper.deleteBatch(examIds);
	}
}
