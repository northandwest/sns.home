package com.ulewo.service;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.model.Comment;
import com.ulewo.po.query.CommentQuery;
import com.ulewo.po.vo.PaginationResult;

public interface CommentService {

	public Comment getCommentById(Integer commentId);

	public PaginationResult<Comment> findCommentByPage(CommentQuery query);

	public void addComment(Comment comment) throws BusinessException;

	public void deleteComment(Integer commentId, ArticleType articleType);

	public PaginationResult<Comment> findAllCommentByPage(CommentQuery query);
}
