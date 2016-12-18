/**
 * Project Name:ulewo-web
 * File Name:LikeService.java
 * Package Name:com.ulewo.service
 * Date:2015年10月9日下午8:44:55
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.model.Like;
import com.ulewo.po.query.LikeQuery;

/**
 * ClassName:LikeService <br/>
 * Date:     2015年10月9日 下午8:44:55 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface LikeService {
	/**
	 * 
	 * findLikeList:(查询like集合). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	List<Like> findLikeList(LikeQuery query);

	/**
	 * 
	 * findLikeByKey:(根据主键查询like). <br/>
	 *
	 * @author 不错啊
	 * @param opId
	 * @param userId
	 * @param type
	 * @return
	 * @since JDK 1.7
	 */
	Like findLikeByKey(Integer articleId, Integer userId, ArticleType articleType);

	/**
	 * 
	 * doLike:(点赞). <br/>
	 *
	 * @author 不错啊
	 * @since JDK 1.7
	 */
	public void doLike(Like like) throws BusinessException;
}
