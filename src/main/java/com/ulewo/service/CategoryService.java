/**
 * Project Name:ulewo-web
 * File Name:CategoryService.java
 * Package Name:com.ulewo.service
 * Date:2015年10月17日下午12:57:50
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.Category;
import com.ulewo.po.query.CategoryQuery;

/**
 * ClassName:CategoryService <br/>
 * Date:     2015年10月17日 下午12:57:50 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface CategoryService {
	public List<Category> findCategroyList(CategoryQuery query);

	public void saveCategory(Category category) throws BusinessException;

	public List<Category> findCategory4TopicCount(CategoryQuery query);

	public void delCategory(Integer categoryId);
}
