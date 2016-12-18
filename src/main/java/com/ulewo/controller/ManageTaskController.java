package com.ulewo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.po.enums.Taskstatus;
import com.ulewo.po.model.Task;
import com.ulewo.po.query.TaskQuery;
import com.ulewo.po.vo.AjaxResponse;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.TaskService;

/**
 * 
 * ClassName: TaskController
 * date: 2015年8月9日 上午11:38:19 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping(value = "/manage/task")
public class ManageTaskController extends BaseController {

	Logger logger = LoggerFactory.getLogger(ManageTaskController.class);

	@Resource
	private TaskService taskService;

	@RequestMapping(value = "/task_list")
	public String task_list() {
		return "page/manage/task_list";
	}

	@ResponseBody
	@RequestMapping(value = "/load_task")
	public AjaxResponse<PaginationResult<Task>> load_task(TaskQuery query) {
		AjaxResponse<PaginationResult<Task>> response = new AjaxResponse<PaginationResult<Task>>();
		try {
			PaginationResult<Task> result = taskService.findTaskList(query);
			response.setData(result);
			response.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("查询任务失败：{}", e);
			response.setResponseCode(ResponseCode.SERVERERROR);
			response.setErrorMsg("查询任务失败");
		}
		return response;
	}

	@RequestMapping(value = "/task_2add")
	public ModelAndView task_2add_task() {
		ModelAndView result = new ModelAndView();
		try {
			result.setViewName("page/manage/task_edit");
			return result;
		} catch (Exception e) {
			logger.error("跳转到任务调度新增页面异常", e);
			return result;
		}
	}

	/**
	 * 添加任务
	 * @param task
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/task_add")
	public AjaxResponse<?> task_add(Task task, Integer execution) {
		AjaxResponse<?> response = new AjaxResponse<Object>();
		try {
			this.taskService.addTask(task, execution == null ? false : true);
			response.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg("保存任务失败");
		}
		return response;
	}

	@RequestMapping(value = "/task_2edit_task")
	public ModelAndView task_2edit_task(Integer id) {
		ModelAndView result = new ModelAndView();
		try {
			Task task = this.taskService.findTaskById(id);
			result.addObject("task", task);
			result.setViewName("page/manage/task_edit");
			return result;
		} catch (Exception e) {
			logger.error("跳转到任务调度编辑页面异常", e);
			return result;
		}
	}

	/**
	 * 修改任务
	 * @param task
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/task_edit")
	public AjaxResponse<?> task_edit(Task task, Integer execution) {
		AjaxResponse<?> response = new AjaxResponse<Object>();
		try {
			this.taskService.updateTask(task, execution == null ? false : true);
			response.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg("保存任务失败");
		}
		return response;
	}

	/**
	 * 暂停任务
	 * @param task
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/task_suspended")
	public AjaxResponse<?> task_suspended(Integer id) {
		AjaxResponse<?> response = new AjaxResponse<Object>();
		try {
			this.taskService.suspendedTask(id);
			response.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg("保存任务失败");
		}
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/task_enable")
	public AjaxResponse<?> task_enable(Integer id) {
		AjaxResponse<?> response = new AjaxResponse<Object>();
		try {
			Task task = new Task();
			task.setId(id);
			task.setTaskStatus(Taskstatus.NORMAL);
			this.taskService.enableTask(id);
			response.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg("保存任务失败");
		}
		return response;
	}

	/**
	 * 立即执行
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/immediate_execution")
	public AjaxResponse<?> immediate_execution(Integer id) {
		AjaxResponse<?> response = new AjaxResponse<Object>();
		try {
			this.taskService.immediateExecutionTask(id);
			response.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("保存任务失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg("保存任务失败");
		}
		return response;
	}

	/**
	 * 删除任务
	 * @param session
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/task_del")
	public AjaxResponse<?> task_del(HttpSession session, Integer[] ids) {
		AjaxResponse<?> response = new AjaxResponse<Object>();
		try {
			this.taskService.deleteTaskBatch(ids);
			response.setResponseCode(ResponseCode.SUCCESS);
		} catch (BusinessException e) {
			logger.error("删除任务调度失败：{}", e);
			response.setResponseCode(ResponseCode.BUSINESSERROR);
			response.setErrorMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("删除任务调度失败：{}", e);
			response.setResponseCode(ResponseCode.SERVERERROR);
			response.setErrorMsg("删除任务调度失败");
		}
		return response;
	}
}
