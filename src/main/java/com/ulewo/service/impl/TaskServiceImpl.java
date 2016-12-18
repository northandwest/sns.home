package com.ulewo.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.TaskMapper;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.Taskstatus;
import com.ulewo.po.model.Task;
import com.ulewo.po.query.TaskQuery;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.quartz.TaskMessage;
import com.ulewo.quartz.trigger.CronTriggerRunner;
import com.ulewo.service.TaskService;

/**
 * 
 * ClassName: TaskServiceImpl
 * date: 2015年8月9日 上午11:44:43 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Resource
	private TaskMapper<Task, TaskQuery> taskMapper;

	@Resource
	private CronTriggerRunner cronTriggerRunner;

	@Override
	public List<Task> findAllTask() throws BusinessException {
		List<Task> list = this.taskMapper.selectList(null);
		return list;
	}

	@Override
	public PaginationResult<Task> findTaskList(TaskQuery query) {
		int count = this.taskMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		List<Task> list = this.taskMapper.selectList(query);
		PaginationResult<Task> result = new PaginationResult<Task>(page, list);
		return result;
	}

	@Override
	public Task findTaskById(Integer id) {
		TaskQuery query = new TaskQuery();
		query.setId(id);
		List<Task> list = this.taskMapper.selectList(query);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addTask(Task task, boolean immediateExecution) throws BusinessException {

		if (null == task) {
			throw new BusinessException("参数错误");
		}
		task.setLastupdateTime(new Date());
		//插入
		if (StringUtils.isEmpty(task.getTaskClassz()) || StringUtils.isEmpty(task.getTaskMethod())
				|| StringUtils.isEmpty(task.getTaskTime())) {
			throw new BusinessException("参数错误");
		}
		if (!CronExpression.isValidExpression(task.getTaskTime())) {
			throw new BusinessException("时间格式错误");
		}
		//校验类是否存在
		Class<?> classz = null;
		try {
			classz = Class.forName(task.getTaskClassz());
		} catch (ClassNotFoundException e) {
			throw new BusinessException("输入的类名不存在");
		}
		try {
			classz.getDeclaredMethod(task.getTaskMethod());
		} catch (NoSuchMethodException e) {
			throw new BusinessException("输入的方法不存在");
		}

		TaskQuery query = new TaskQuery();
		query.setTaskClassz(task.getTaskClassz());
		query.setTaskMethod(task.getTaskMethod());
		int count = this.taskMapper.selectCount(query);
		if (count > 0) {
			throw new BusinessException("该任务已经存在");
		}

		this.taskMapper.insert(task);

		try {
			TaskMessage taskMessage = convertTask2TaskMessage(task);
			cronTriggerRunner.saveJob(taskMessage, immediateExecution);
		} catch (SchedulerException e) {
			throw new BusinessException("新增任务失败", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void updateTask(Task task, boolean immediateExecution) throws BusinessException {
		if (null == task.getId()) {
			throw new BusinessException("参数错误");
		}
		if (StringUtils.isEmpty(task.getTaskTime()) || !CronExpression.isValidExpression(task.getTaskTime())) {
			throw new BusinessException("时间格式错误");
		}
		task.setLastupdateTime(new Date());
		this.taskMapper.update(task);
		Task resultTask = this.findTaskById(task.getId());
		//如果任务是暂停的那么不执行saveJob
		if (null != resultTask && resultTask.getTaskStatus() == Taskstatus.NORMAL) {
			try {
				TaskMessage taskMessage = convertTask2TaskMessage(task);
				cronTriggerRunner.saveJob(taskMessage, immediateExecution);
			} catch (SchedulerException e) {
				throw new BusinessException("新增任务失败", e);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void suspendedTask(Integer id) throws BusinessException {
		if (null == id) {
			throw new BusinessException("参数错误");
		}
		Task task = new Task();
		task.setLastupdateTime(new Date());
		task.setId(id);
		task.setTaskStatus(Taskstatus.SUSPENDED);
		this.taskMapper.update(task);
		Task resultTask = findTaskById(id);
		try {
			TaskMessage taskMessage = convertTask2TaskMessage(resultTask);
			cronTriggerRunner.pauseJob(taskMessage);
		} catch (SchedulerException e) {
			throw new BusinessException("新增任务失败", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void enableTask(Integer id) throws BusinessException {
		if (null == id) {
			throw new BusinessException("参数错误");
		}
		Task task = new Task();
		task.setLastupdateTime(new Date());
		task.setId(id);
		task.setTaskStatus(Taskstatus.NORMAL);
		this.taskMapper.update(task);
		Task resultTask = findTaskById(id);
		try {
			TaskMessage taskMessage = convertTask2TaskMessage(resultTask);
			cronTriggerRunner.saveJob(taskMessage, false);
		} catch (SchedulerException e) {
			throw new BusinessException("新增任务失败", e);
		}
	}

	@Override
	public void immediateExecutionTask(Integer id) throws BusinessException {

		try {
			Task task = this.findTaskById(id);
			task.setLastupdateTime(new Date());
			task.setTaskStatus(Taskstatus.NORMAL);
			this.taskMapper.update(task);
			TaskMessage taskMessage = convertTask2TaskMessage(task);
			cronTriggerRunner.saveJob(taskMessage, true);
		} catch (SchedulerException e) {
			throw new BusinessException("执行任务失败", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteTaskBatch(Integer[] ids) throws BusinessException {
		TaskQuery query = new TaskQuery();
		for (Integer id : ids) {
			try {
				Task task = this.findTaskById(id);
				TaskMessage taskMessage = convertTask2TaskMessage(task);
				cronTriggerRunner.delJob(taskMessage);
			} catch (SchedulerException e) {
				throw new BusinessException("删除任务失败", e);
			}
			query.setId(id);
			this.taskMapper.delete(query);
		}
	}

	private TaskMessage convertTask2TaskMessage(Task task) {
		TaskMessage taskMessage = new TaskMessage();
		taskMessage.setId(task.getId());
		taskMessage.setTaskClassz(task.getTaskClassz());
		taskMessage.setTaskMethod(task.getTaskMethod());
		taskMessage.setTaskTime(task.getTaskTime());
		return taskMessage;
	}

}
