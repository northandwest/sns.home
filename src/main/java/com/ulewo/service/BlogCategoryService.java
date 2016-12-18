package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.BlogCategory;

public interface BlogCategoryService {

	public List<BlogCategory> findBlogCategoryList(Integer userId);

	public void saveBlogCategory(BlogCategory category) throws BusinessException;

	public void deleteBlogCategory(Integer categroyId, Integer userId) throws BusinessException;
}
