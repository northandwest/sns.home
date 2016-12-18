package com.ulewo.quartz;

/**
 * 
 * ClassName: TaskMessage
 * date: 2015年8月9日 上午11:42:59 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
public class TaskMessage {
	private Integer id;
	private String taskClassz;
	private String taskMethod;
	private String taskTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskClassz() {
		return taskClassz;
	}

	public void setTaskClassz(String taskClassz) {
		this.taskClassz = taskClassz;
	}

	public String getTaskMethod() {
		return taskMethod;
	}

	public void setTaskMethod(String taskMethod) {
		this.taskMethod = taskMethod;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

}
