/**
 * Project Name:ulewo-common
 * File Name:StatisticsServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年12月17日下午9:31:08
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ulewo.mapper.AskMapper;
import com.ulewo.mapper.BlogMapper;
import com.ulewo.mapper.CommentMapper;
import com.ulewo.mapper.ExamMapper;
import com.ulewo.mapper.KnowledgeMapper;
import com.ulewo.mapper.SignInMapper;
import com.ulewo.mapper.SpitSlotCommentMapper;
import com.ulewo.mapper.SpitSlotMapper;
import com.ulewo.mapper.StatisticsMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.model.Ask;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.Comment;
import com.ulewo.po.model.Exam;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.model.SignIn;
import com.ulewo.po.model.SpitSlot;
import com.ulewo.po.model.SpitSlotComment;
import com.ulewo.po.model.Statistics;
import com.ulewo.po.model.Topic;
import com.ulewo.po.model.User;
import com.ulewo.po.model.chart.Chart;
import com.ulewo.po.model.chart.Series;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.query.CommentQuery;
import com.ulewo.po.query.ExamQuery;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.query.SignInQuery;
import com.ulewo.po.query.SpitSlotQuery;
import com.ulewo.po.query.StatisticsQuery;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.query.UserQuery;
import com.ulewo.service.StatisticsService;
import com.ulewo.utils.DateUtil;

/**
 * ClassName:StatisticsServiceImpl <br/>
 * Date:     2015年12月17日 下午9:31:08 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

	@Resource
	private SpitSlotMapper<SpitSlot, SpitSlotQuery> spitSlotMapper;

	@Resource
	private SpitSlotCommentMapper<SpitSlotComment, SpitSlotQuery> spitSlotCommentMapper;

	@Resource
	private TopicMapper<Topic, TopicQuery> topicMapper;

	@Resource
	private CommentMapper<Comment, CommentQuery> commentMapper;

	@Resource
	private KnowledgeMapper<Knowledge, KnowledgeQuery> knowledgeMapper;

	@Resource
	private AskMapper<Ask, AskQuery> askMapper;

	@Resource
	private BlogMapper<Blog, BlogQuery> blogMapper;

	@Resource
	private UserMapper<User, UserQuery> userMapper;

	@Resource
	private SignInMapper<SignIn, SignInQuery> signInMapper;

	@Resource
	private ExamMapper<Exam, ExamQuery> examMapper;

	@Resource
	private StatisticsMapper<Statistics, StatisticsQuery> statisticsMapper;

	public void statistAllInfo() {
		Date curDate = new Date();
		String date = DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern());
		//吐槽数量
		SpitSlotQuery spitSlotQuery = new SpitSlotQuery();
		spitSlotQuery.setStartTime(date);
		spitSlotQuery.setEndTime(date);
		int spitSlotCount = spitSlotMapper.selectCount(spitSlotQuery);
		//吐槽评论数量
		int spitSlotCommentCount = spitSlotCommentMapper.selectCount(spitSlotQuery);
		//帖子数
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setStartDate(date);
		topicQuery.setEndDate(date);
		int topicCount = topicMapper.selectCount(topicQuery);
		//帖子评论数
		CommentQuery commentQuery = new CommentQuery();
		commentQuery.setStartTime(date);
		commentQuery.setEndTime(date);
		commentQuery.setArticleType(ArticleType.TOPIC);
		int topicCommentCount = commentMapper.selectCount(commentQuery);
		//发布知识库数量
		KnowledgeQuery knowledgeQuery = new KnowledgeQuery();
		knowledgeQuery.setStartTime(date);
		knowledgeQuery.setEndTime(date);
		int knowledgeCount = knowledgeMapper.selectCount(knowledgeQuery);
		commentQuery.setArticleType(ArticleType.KNOWLEDGE);
		int knowledgeCommentCount = commentMapper.selectCount(commentQuery);
		//问答
		AskQuery askQuery = new AskQuery();
		askQuery.setStartDate(date);
		askQuery.setEndDate(date);
		int askCount = askMapper.selectCount(askQuery);
		commentQuery.setArticleType(ArticleType.ASK);
		int askCommentCount = commentMapper.selectCount(commentQuery);
		//博客
		BlogQuery blogQuery = new BlogQuery();
		blogQuery.setStartTime(date);
		blogQuery.setEndTime(date);
		int blogCount = blogMapper.selectCount(blogQuery);
		commentQuery.setArticleType(ArticleType.BLOG);
		int blogCommentCount = commentMapper.selectCount(commentQuery);
		//新增用户
		UserQuery userQuery = new UserQuery();
		userQuery.setStartDate(date);
		userQuery.setEndDate(date);
		int userCount = userMapper.selectCount(userQuery);
		//活跃用户数
		userQuery = new UserQuery();
		userQuery.setLastLoginStartDate(date);
		userQuery.setLastLoginEndDate(date);
		int activeUserCount = userMapper.selectCount(userQuery);
		//签到数量
		SignInQuery signInQuery = new SignInQuery();
		signInQuery.setStartDate(curDate);
		signInQuery.setEndDate(curDate);
		int signinCount = signInMapper.selectCount(signInQuery);
		//考题数
		ExamQuery examQuery = new ExamQuery();
		examQuery.setStartTime(date);
		examQuery.setEndTime(date);
		int examCount = examMapper.selectCount(examQuery);

		Statistics statistics = new Statistics();
		statistics.setStatisticsDate(curDate);
		statistics.setSigninCount(signinCount);
		statistics.setSpitSlotCount(spitSlotCount);
		statistics.setSpitSlotCommentCount(spitSlotCommentCount);
		statistics.setTopicCount(topicCount);
		statistics.setTopicCommentCount(topicCommentCount);
		statistics.setKnowledgeCount(knowledgeCount);
		statistics.setKnowledgeCommentCount(knowledgeCommentCount);
		statistics.setAskCount(askCount);
		statistics.setAskCommentCount(askCommentCount);
		statistics.setBlogCount(blogCount);
		statistics.setBlogCommentCount(blogCommentCount);
		statistics.setUserCount(userCount);
		statistics.setActiveUserCount(activeUserCount);
		statistics.setExamCount(examCount);
		statisticsMapper.insert(statistics);
	}

	@Override
	public List<Chart> getChartList() {
		StatisticsQuery query = new StatisticsQuery();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -7);
		Date startDate = c.getTime();
		query.setStartDate(startDate);
		query.setEndDate(new Date());
		List<Statistics> list = statisticsMapper.selectList(query);
		List<Chart> chartList = new ArrayList<Chart>();
		List<String> xAsis = new ArrayList<String>();

		List<Integer> series_sign = new ArrayList<Integer>();
		List<Integer> series_spit_slot = new ArrayList<Integer>();
		List<Integer> series_sign_slot_comment = new ArrayList<Integer>();
		List<Integer> series_topic = new ArrayList<Integer>();
		List<Integer> series_topic_comment = new ArrayList<Integer>();
		List<Integer> series_knowledge = new ArrayList<Integer>();
		List<Integer> series_knowledge_comment = new ArrayList<Integer>();
		List<Integer> series_ask = new ArrayList<Integer>();
		List<Integer> series_ask_comment = new ArrayList<Integer>();
		List<Integer> series_blog = new ArrayList<Integer>();
		List<Integer> series_blog_comment = new ArrayList<Integer>();
		List<Integer> series_exam = new ArrayList<Integer>();
		List<Integer> series_user = new ArrayList<Integer>();
		List<Integer> series_active_user = new ArrayList<Integer>();
		for (Statistics s : list) {
			xAsis.add(DateUtil.format(s.getStatisticsDate(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
			series_sign.add(s.getSigninCount());
			series_spit_slot.add(s.getSpitSlotCount());
			series_sign_slot_comment.add(s.getSpitSlotCommentCount());
			series_topic.add(s.getTopicCount());
			series_topic_comment.add(s.getTopicCommentCount());
			series_knowledge.add(s.getKnowledgeCount());
			series_knowledge_comment.add(s.getKnowledgeCommentCount());
			series_ask.add(s.getAskCount());
			series_ask_comment.add(s.getAskCommentCount());
			series_blog.add(s.getBlogCount());
			series_blog_comment.add(s.getBlogCommentCount());
			series_exam.add(s.getExamCount());
			series_user.add(s.getUserCount());
			series_active_user.add(s.getActiveUserCount());
		}
		//签到
		Chart signChart = new Chart(xAsis, new String[] { "签到" });
		signChart.getSeries().add(new Series("签到", series_sign));
		chartList.add(signChart);

		//用户
		Chart userChart = new Chart(xAsis, new String[] { "新增用户", "活跃用户" });
		userChart.getSeries().add(new Series("新增用户", series_user));
		userChart.getSeries().add(new Series("活跃用户", series_active_user));
		chartList.add(userChart);

		//吐槽
		Chart spitSlotChart = new Chart(xAsis, new String[] { "吐槽", "吐槽评论" });
		spitSlotChart.getSeries().add(new Series("吐槽", series_spit_slot));
		spitSlotChart.getSeries().add(new Series("吐槽评论", series_sign_slot_comment));
		chartList.add(spitSlotChart);

		//帖子
		Chart topicChart = new Chart(xAsis, new String[] { "帖子", "帖子评论" });
		topicChart.getSeries().add(new Series("帖子", series_topic));
		topicChart.getSeries().add(new Series("帖子评论", series_topic_comment));
		chartList.add(topicChart);

		//知识库
		Chart knowledgeChart = new Chart(xAsis, new String[] { "知识库", "知识库评论" });
		knowledgeChart.getSeries().add(new Series("知识库", series_knowledge));
		knowledgeChart.getSeries().add(new Series("知识库评论", series_knowledge_comment));
		chartList.add(knowledgeChart);

		//问答
		Chart askChart = new Chart(xAsis, new String[] { "问答", "问答评论" });
		askChart.getSeries().add(new Series("问答", series_ask));
		askChart.getSeries().add(new Series("问答评论", series_ask_comment));
		chartList.add(askChart);

		//博客
		Chart blogChart = new Chart(xAsis, new String[] { "博客", "博客评论" });
		blogChart.getSeries().add(new Series("博客", series_blog));
		blogChart.getSeries().add(new Series("博客评论", series_blog_comment));
		chartList.add(blogChart);

		return chartList;
	}
}
