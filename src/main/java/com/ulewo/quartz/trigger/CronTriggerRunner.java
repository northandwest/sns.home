package com.ulewo.quartz.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import com.ulewo.quartz.TaskMessage;
import com.ulewo.quartz.job.DefaultJob;
import com.ulewo.utils.Constants;

/**
 * 
 * ClassName: CronTriggerRunner
 * date: 2015年8月9日 上午11:42:53 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Component
public class CronTriggerRunner {

	private final String TASKNAME = "task_";
	private final String TASKGROUP = "task_gourp_";

	/**
	 * 保存调度
	 * runJob:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param task
	 * @throws SchedulerException
	 * @since JDK 1.7
	 */
	public void saveJob(TaskMessage task, boolean immediateExecution) throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		TriggerKey triggerKey = getTriggerKey(task);
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 不存在，创建一个
		if (null == trigger) {
			JobDetail jobDetail = JobBuilder.newJob(DefaultJob.class)
					.withIdentity(TASKNAME + task.getId(), TASKGROUP + task.getId()).build();
			jobDetail.getJobDataMap().put(Constants.TASK_MESSAGE, task);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTaskTime());
			// 按新的表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger().withIdentity(TASKNAME + task.getId(), TASKGROUP + task.getId())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} else {
			// trigger已存在，则更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTaskTime());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
		if (immediateExecution) {
			this.triggerJob(task, scheduler);
		}
	}

	/**
	 * 暂停
	 * pauseJob:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author 不错啊
	 * @throws SchedulerException 
	 * @since JDK 1.7
	 */
	public void pauseJob(TaskMessage task) throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobKey jobKey = getJobKey(task);
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 删除job
	 * delJob:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author 不错啊
	 * @param task
	 * @throws SchedulerException
	 * @since JDK 1.7
	 */
	public void delJob(TaskMessage task) throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobKey jobKey = getJobKey(task);
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 立即执行
	 * triggerJob:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author 不错啊
	 * @param task
	 * @throws SchedulerException
	 * @since JDK 1.7
	 */
	public void triggerJob(TaskMessage task, Scheduler scheduler) throws SchedulerException {
		JobKey jobKey = getJobKey(task);
		scheduler.triggerJob(jobKey);
	}

	private JobKey getJobKey(TaskMessage task) {
		JobKey jobKey = JobKey.jobKey(TASKNAME + task.getId(), TASKGROUP + task.getId());
		return jobKey;
	}

	private TriggerKey getTriggerKey(TaskMessage task) {
		TriggerKey triggerKey = TriggerKey.triggerKey(TASKNAME + task.getId(), TASKGROUP + task.getId());
		return triggerKey;
	}
}
