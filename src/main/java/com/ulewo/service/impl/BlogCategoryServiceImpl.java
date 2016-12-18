package com.ulewo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.BlogCategoryMapper;
import com.ulewo.po.enums.BlogStatusEnum;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.BlogCategory;
import com.ulewo.po.query.BlogCategoryQuery;
import com.ulewo.service.BlogCategoryService;

/**
 * TODO: 增加描述
 * 
 * @author luohaili
 * @date 2015年11月12日 下午4:24:18
 * @version 0.1.0 
 */
@Service("blogCategoryService")
public class BlogCategoryServiceImpl implements BlogCategoryService {
	@Resource
	private BlogCategoryMapper<BlogCategory, BlogCategoryQuery> blogCategoryMapper;

	@Override
	public List<BlogCategory> findBlogCategoryList(Integer userId) {
		BlogCategoryQuery query = new BlogCategoryQuery();
		query.setUserId(userId);
		query.setBlogStatus(BlogStatusEnum.PASS);
		return blogCategoryMapper.selectList(query);
	}

	@Override
	public void saveBlogCategory(BlogCategory category) throws BusinessException {
		if (StringUtils.isEmpty(category.getName())
				|| category.getName().length() > TextLengthEnum.LENGTH_50.getLength() || category.getRang() == null) {
			throw new BusinessException("参数错误");
		}
		BlogCategoryQuery query = new BlogCategoryQuery();
		query.setUserId(category.getUserId());
		blogCategoryMapper.insert(category);
	}

	@Override
	public void deleteBlogCategory(Integer categoryId, Integer userId) throws BusinessException {
		if (null == categoryId) {
			throw new BusinessException("参数错误");
		}
		BlogCategoryQuery query = new BlogCategoryQuery();
		query.setUserId(userId);
		query.setCategoryId(categoryId);
		blogCategoryMapper.delete(query);
	}
}
