package com.ulewo.init;

import java.util.ArrayList;
import java.util.List;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.Taskstatus;
import com.ulewo.po.model.Task;
import com.ulewo.quartz.TaskMessage;
import com.ulewo.quartz.trigger.CronTriggerRunner;
import com.ulewo.service.TaskService;
import com.ulewo.utils.SpringContextUtil;

/**
 * 
 * ClassName: InitTask
 * date: 2015年8月9日 上午11:42:37 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
public class InitTask {

	/**
	 * 通过spring上下文获取spring bean
	 */
	private Logger logger = LoggerFactory.getLogger(InitTask.class);

	private static TaskService taskService = (TaskService) SpringContextUtil.getBean("taskService");

	private static CronTriggerRunner cronTriggerRunner = (CronTriggerRunner) SpringContextUtil
			.getBean("cronTriggerRunner");

	public void onApplicationEvent() {
		List<Task> taskList = new ArrayList<Task>();
		try {
			taskList = taskService.findAllTask();
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
		for (Task task : taskList) {
			if (task.getTaskStatus() == Taskstatus.SUSPENDED) {
				continue;
			}
			try {
				cronTriggerRunner.saveJob(convertTask2TaskMessage(task), false);
			} catch (SchedulerException e) {
				logger.error("启动任务失败,taskId:{},taskClassz:{},taskMethod:{}", task.getId(), task.getTaskClassz(),
						task.getTaskMethod(), e);
			}
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
