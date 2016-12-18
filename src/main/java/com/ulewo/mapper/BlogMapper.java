package com.ulewo.mapper;

import org.apache.ibatis.annotations.Param;

import com.ulewo.po.query.UpdateQuyery4ArticleCount;

public interface BlogMapper<T, Q> extends BaseMapper<T, Q> {
	public void updateInfoCount(UpdateQuyery4ArticleCount query);

	public void deleteBatch(@Param("ids") Integer[] ids);
}
