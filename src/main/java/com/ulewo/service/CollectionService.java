package com.ulewo.service;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.model.Collection;
import com.ulewo.po.query.CollectionQuery;
import com.ulewo.po.vo.PaginationResult;

public interface CollectionService {

	/**
	 * 添加收藏
	 * @param map
	 * @param sessionUser
	 */
	public void addCollection(Collection collection) throws BusinessException;

	/**
	 * 
	 * findCollectionByKey:(通过key查询用户收藏信息). <br/>
	 *
	 * @author 不错啊
	 * @param articleId
	 * @param userId
	 * @param articleType
	 * @return
	 * @since JDK 1.7
	 */
	public Collection findCollectionByKey(Integer articleId, Integer userId, ArticleType articleType);

	/**
	 * 
	 * delCollection:(删除收藏). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @since JDK 1.7
	 */
	public void delCollection(Collection collection) throws BusinessException;

	/**
	 * 
	 * findCollectionByPage:(分页查询). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public PaginationResult<Collection> findCollectionByPage(CollectionQuery query);

}
