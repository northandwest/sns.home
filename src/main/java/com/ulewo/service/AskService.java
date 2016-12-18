package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.Ask;
import com.ulewo.po.query.AskQuery;
import com.ulewo.po.vo.PaginationResult;

public interface AskService {
	public List<Ask> findAsks4Index();

	public PaginationResult<Ask> findAskByPage(AskQuery query);

	public int findCount(AskQuery query);

	public void addAsk(Ask ask) throws BusinessException;

	public void setBestAnswer(Integer bestAnswerId, Integer askId, Integer userId) throws BusinessException;

	public Ask getAskById(Integer askId);

	public Ask showAsk(Integer askId) throws BusinessException;

	public List<Ask> selectTopUsers();

	public void deleteBatch(Integer[] ids);
}
