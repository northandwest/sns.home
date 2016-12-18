package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.Blog;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.vo.PaginationResult;

public interface BlogService {

	public List<Blog> findBlogs4Index();

	public Blog getBlogById(Integer blogId);

	public PaginationResult<Blog> findBlogByPage(BlogQuery query);

	public Blog showBlog(Integer blogId, Integer userId) throws BusinessException;

	public Blog getBlogByBlogId(Integer blogId, Integer userId) throws BusinessException;

	public void saveDraftsBlog(Blog blog) throws BusinessException;

	public void postBlog(Blog blog, Attachment attachment) throws BusinessException;

	public void delBlog(Integer blogId, Integer userId) throws BusinessException;

	public void deleteBatch(Integer[] ids);
}
