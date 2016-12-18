/**
 * Project Name:ulewo-web
 * File Name:SpitSlotService.java
 * Package Name:com.ulewo.service
 * Date:2015年9月26日下午7:51:10
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.SpitSlot;
import com.ulewo.po.model.SpitSlotComment;
import com.ulewo.po.query.SpitSlotQuery;
import com.ulewo.po.vo.PaginationResult;

/**
 * ClassName:SpitSlotService <br/>
 * Date:     2015年9月26日 下午7:51:10 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface SpitSlotService {
	/**
	 * 
	 * addSpitSlot:(新增吐槽). <br/>
	 *
	 * @author 不错啊
	 * @param slot
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void addSpitSlot(SpitSlot slot) throws BusinessException;

	/**
	 * 
	 * findSpitSlotList:(分页查询吐槽). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public PaginationResult<SpitSlot> findSpitSlotList(SpitSlotQuery query);

	/**
	 * 
	 * findSpitSlotById:(查询吐槽详情). <br/>
	 *
	 * @author 不错啊
	 * @param userId
	 * @param id
	 * @return
	 * @since JDK 1.7
	 */
	public SpitSlot findSpitSlot(Integer userId, Integer id);

	/**
	 * 
	 * addSpitSlotComment:(添加评论). <br/>
	 *
	 * @author 不错啊
	 * @param slot
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void addSpitSlotComment(SpitSlotComment slot) throws BusinessException;

	/**
	 * 
	 * loadSpitSlotComment:(查询所有评论). <br/>
	 *
	 * @author 不错啊
	 * @param spitSlotId
	 * @return
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public List<SpitSlotComment> loadSpitSlotComment(Integer spitSlotId);

	/**
	 * 
	 * selectActiveUser4SpitSlot:(查询活跃用户). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public List<SpitSlot> findActiveUser4SpitSlot();

	/**
	 * 
	 * findSpitSlotCommentList:(分页查询吐槽评论). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public PaginationResult<SpitSlotComment> findSpitSlotCommentList(SpitSlotQuery query);

	/***
	 * 
	 * deleteSpitSlot:(删除吐槽). <br/>
	 *
	 * @author 不错啊
	 * @param spitSlotId
	 * @since JDK 1.7
	 */
	public void deleteSpitSlot(Integer spitSlotId);

	/**
	 * 
	 * deleteSpitSlotComment:(删除评论). <br/>
	 *
	 * @author 不错啊
	 * @param commentId
	 * @since JDK 1.7
	 */
	public void deleteSpitSlotComment(Integer spitSlotId, Integer commentId);
}
