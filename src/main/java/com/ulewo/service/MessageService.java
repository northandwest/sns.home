package com.ulewo.service;

import org.springframework.scheduling.annotation.Async;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.Message;
import com.ulewo.po.model.MessageParams;
import com.ulewo.po.query.MessageQuery;
import com.ulewo.po.vo.PaginationResult;

public interface MessageService {
	@Async
	public void createMessage(MessageParams message);

	public Message getMessageById(Integer id, Integer userId);

	public PaginationResult<Message> findMessageByPage(MessageQuery query);

	public int findMessageCount(MessageQuery query);

	public void update(Integer[] ids, Integer userId) throws BusinessException;

	public void delMessage(Integer userId, Integer[] ids) throws BusinessException;
}
