package com.ulewo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.model.Message;
import com.ulewo.po.query.MessageQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.MessageService;
import com.ulewo.utils.Constants;

@Controller
@RequestMapping("/admin")
public class AdminMessageController extends BaseController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private MessageService messageService;

	@RequestMapping(value = "message_list.action")
	public ModelAndView message_list(HttpSession session) {
		ModelAndView view = new ModelAndView("/page/admin/message_list");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "load_user_message_list.action")
	public AjaxResponse<PaginationResult<Message>> load_user_message(HttpSession session, MessageQuery query) {
		AjaxResponse<PaginationResult<Message>> result = new AjaxResponse<PaginationResult<Message>>();
		try {
			query.setReceivedUserId(this.getUserId(session));
			PaginationResult<Message> data = messageService.findMessageByPage(query);
			result.setData(data);
			result.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载消息异常", e);
			result.setResponseCode(ResponseCode.SERVERERROR);
			result.setErrorMsg("加载消息异常");
		}
		return result;
	}

	/**
	 * 
	 * load_user_message_count:(获取消息数量)
	 * @author luohaili
	 * @param session
	 * @param user
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "load_user_message_count.action")
	public AjaxResponse<Integer> load_user_message_count(HttpSession session, MessageQuery query) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		result.setResponseCode(ResponseCode.SUCCESS);
		int count = 0;
		try {
			query.setReceivedUserId(this.getUserId(session));
			count = messageService.findMessageCount(query);
		} catch (Exception e) {
			logger.error("获取消息数量异常", e);
		}
		result.setData(count);
		return result;
	}

	/**
	 * 
	 * readMessage:(阅读消息)
	 * @author luohaili
	 * @param session
	 * @param response
	 * @param id
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "readMessage.action")
	public ModelAndView readMessage(HttpSession session, HttpServletResponse response, Integer id) {
		ModelAndView view = new ModelAndView();
		String url = Constants.ERROR_404;
		Integer userId = this.getUserId(session);
		Message message = messageService.getMessageById(id, userId);
		if (message != null) {
			url = message.getUrl();
		}
		//标记已读
		try {
			messageService.update(new Integer[] { id }, userId);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		view.setViewName("redirect:" + url);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "mark_message_read.action")
	public AjaxResponse<Integer> mark_message_read(HttpSession session, Integer[] ids) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			messageService.update(ids, this.getUserId(session));
		} catch (BusinessException e) {
			logger.error("标记消息为已读异常", e);
			result.setErrorMsg(e.getMessage());
			result.setResponseCode(ResponseCode.BUSINESSERROR);
		} catch (Exception e) {
			logger.error("标记消息为已读异常", e);
			result.setResponseCode(ResponseCode.BUSINESSERROR);
			result.setErrorMsg("系统异常");
		}
		return result;
	}

	/**
	 * 
	 * del_message:(删除消息)
	 * @author luohaili
	 * @param session
	 * @param ids
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "del_message.action")
	public AjaxResponse<Integer> del_message(HttpSession session, Integer[] ids) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		result.setResponseCode(ResponseCode.SUCCESS);
		try {
			messageService.delMessage(this.getUserId(session), ids);
		} catch (BusinessException e) {
			logger.error("删除消息异常", e);
			result.setErrorMsg(e.getMessage());
			result.setResponseCode(ResponseCode.BUSINESSERROR);
		} catch (Exception e) {
			logger.error("删除消息异常", e);
			result.setErrorMsg("系统异常");
			result.setResponseCode(ResponseCode.SERVERERROR);
		}
		return result;
	}
}
