/**
 * Project Name:ulewo-web
 * File Name:BbsService.java
 * Package Name:com.ulewo.service
 * Date:2015年10月24日下午8:29:18
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.Category;
import com.ulewo.po.model.Topic;
import com.ulewo.po.model.TopicVote;
import com.ulewo.po.query.TopicQuery;
import com.ulewo.po.vo.PaginationResult;

/**
 * ClassName:BbsService <br/>
 * Date:     2015年10月24日 下午8:29:18 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface TopicService {

	/**
	 * 
	 * findTopics4Index:(查询首页文章). <br/>
	 *
	 * @author 不错啊
	 * @return
	 * @since JDK 1.7
	 */
	public List<Topic> findTopics4Index();

	/**
	 * 
	 * findTopicsByPage:(分页查询主题). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public PaginationResult<Topic> findTopicsByPage(TopicQuery query);

	/**
	 * 
	 * findCount:(查询主题数量). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public int findCount(TopicQuery query);

	/**
	 * 
	 * addTopic:(添加topic). <br/>
	 *
	 * @author 不错啊
	 * @param topic
	 * @param vote
	 * @param voteTitle
	 * @param flie
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void addTopic(Topic topic, TopicVote vote, String[] voteTitle, Attachment flie) throws BusinessException;

	/**
	 * 
	 * showTopic:(详情帖). <br/>
	 *
	 * @author 不错啊
	 * @param topicId
	 * @return
	 * @since JDK 1.7
	 */
	public Topic showTopic(Integer topicId) throws BusinessException;

	/**
	 * 
	 * getTopicById:(通过Id获取topic). <br/>
	 *
	 * @author 不错啊
	 * @param topicId
	 * @return
	 * @since JDK 1.7
	 */
	public Topic getTopicById(Integer topicId);

	/**
	 * 
	 * selectActiveUsers:(查询活跃用户). <br/>
	 *
	 * @author 不错啊
	 * @return
	 * @since JDK 1.7
	 */
	public List<Topic> findActiveUsers();

	/**
	 * 
	 * getPcategoryByPCategoryId:(通过id获取分类). <br/>
	 *
	 * @author 不错啊
	 * @param pcategoryId
	 * @return
	 * @since JDK 1.7
	 */
	public Category getPcategoryByPCategoryId(Integer pcategoryId);

	/**
	 * 
	 * getPcategoryByCategoryId:(通过二级分类获取分类). <br/>
	 *
	 * @author 不错啊
	 * @param categoryId
	 * @return
	 * @since JDK 1.7
	 */
	public Category getPcategoryByCategoryId(Integer categoryId);

	/**
	 * 
	 * deleteTopic:(删除帖子)
	 * @author luohaili
	 * @param ids
	 * @since JDK 1.7
	 */
	public void deleteTopic(Integer[] ids);

	/**
	 * 
	 * changeCategory:(修改分类)
	 * @author luohaili
	 * @param topicId
	 * @param pCategoryId
	 * @param categoryId
	 * @since JDK 1.7
	 */
	public void changeCategory(Integer topicId, Integer pCategoryId, Integer categoryId);

	/**
	 * 
	 * setTop:(置顶)
	 * @author luohaili
	 * @param topicId
	 * @since JDK 1.7
	 */
	public void setTop(Integer topicId);

	/**
	 * 
	 * cancelTop:(取消置顶)
	 * @author luohaili
	 * @param topicId
	 * @since JDK 1.7
	 */
	public void cancelTop(Integer topicId);

	/**
	 * 
	 * setEssence:(设置精华)
	 * @author luohaili
	 * @param topicId
	 * @since JDK 1.7
	 */
	public void setEssence(Integer topicId);

	/**
	 * 
	 * cancelEssence:(取消精华)
	 * @author luohaili
	 * @param topicId
	 * @since JDK 1.7
	 */
	public void cancelEssence(Integer topicId);
}
