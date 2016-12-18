package com.ulewo.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ulewo.po.enums.StatusEnum;
import com.ulewo.po.query.UpdateQuyery4ArticleCount;

@Repository
public interface KnowledgeMapper<T, Q> extends BaseMapper<T, Q> {
	public void updateInfoCount(UpdateQuyery4ArticleCount query);

	public void updateStatus(@Param("status") StatusEnum status, @Param("ids") Integer[] ids);

	public void deleteBatch(@Param("ids") Integer[] ids);
}
