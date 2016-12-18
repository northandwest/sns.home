package com.ulewo.quartz.job;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import com.ulewo.quartz.TaskMessage;
import com.ulewo.utils.Constants;

/**
 * 
 * ClassName: DefaultJob
 * date: 2015年8月9日 上午11:42:48 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
public class DefaultJob implements Job {

	@Override
	public void execute(JobExecutionContext context) {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		TaskMessage task = (TaskMessage) jobDataMap.get(Constants.TASK_MESSAGE);
		Class<?> classz = null;
		try {
			classz = Class.forName(task.getTaskClassz());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Method method = classz.getDeclaredMethod(task.getTaskMethod());
			method.invoke(classz.newInstance());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

	}
}
