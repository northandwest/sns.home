package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ulewo.po.enums.SloveTypeEnum;
import com.ulewo.po.model.Ask;
import com.ulewo.po.query.UpdateQuyery4ArticleCount;

public interface AskMapper<T, Q> extends BaseMapper<T, Q> {

	public void updateInfoCount(UpdateQuyery4ArticleCount query);

	public void updateBestAnswer(@Param("bestAnswerId") Integer bestAnswerId,
			@Param("bestAnswerUserId") Integer bestAnswerUserId,
			@Param("bestAnswerUserName") String bestAnswerUserName, @Param("sloveType") SloveTypeEnum sloveType,
			@Param("askId") Integer askId, @Param("userId") Integer userId);

	public List<Ask> selectTopUsers(@Param("topCount") Integer topCount);

	public void deleteBatch(@Param("ids") Integer[] ids);
}
