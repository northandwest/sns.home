/**
 * Project Name:ulewo-web
 * File Name:CategoryServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年10月17日下午1:11:54
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.CategoryMapper;
import com.ulewo.po.model.Category;
import com.ulewo.po.query.CategoryQuery;
import com.ulewo.service.CategoryService;
import com.ulewo.utils.StringTools;

/**
 * ClassName:CategoryServiceImpl <br/>
 * Date:     2015年10月17日 下午1:11:54 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private CategoryMapper<Category, CategoryQuery> categoryMapper;

	@Override
	public List<Category> findCategroyList(CategoryQuery query) {
		List<Category> list = categoryMapper.selectList(query);
		list = getChildren(list, 0);
		return list;
	}

	private static List<Category> getChildren(List<Category> dataList, int id) {
		List<Category> children = new ArrayList<Category>();
		for (Category m : dataList) {
			if (m.getPid() == id) {
				m.setChildren(getChildren(dataList, m.getCategoryId()));
				children.add(m);
			}
		}
		return children;
	}

	@Override
	public void saveCategory(Category category) throws BusinessException {
		if (StringTools.isEmpty(category.getName()) || category.getPid() == null) {
			throw new BusinessException("参数错误");
		}
		categoryMapper.insert(category);
	}

	@Override
	public void delCategory(Integer categoryId) {
		CategoryQuery query = new CategoryQuery();
		query.setCategoryId(categoryId);
		categoryMapper.delete(query);
	}

	@Override
	public List<Category> findCategory4TopicCount(CategoryQuery query) {
		List<Category> list = categoryMapper.selectCategory4TopicCount(query);
		list = getChildren(list, 0);
		return list;
	}
}
