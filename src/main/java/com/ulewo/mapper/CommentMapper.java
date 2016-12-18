package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper<T, Q> extends BaseMapper<T, Q> {
	public List<T> selectChildren(Integer pid);

	public T selectCommentNoChildren(@Param("commentId") Integer commentId);

	public List<T> selectListNoChildren(Q q);

	public int deleteComment(@Param("commentId") Integer commentId);
}
