/**
 * Project Name:umei-admin
 * File Name:TaskService.java
 * Package Name:com.umei.service
 * Date:2015年8月4日下午9:17:57
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.Task;
import com.ulewo.po.query.TaskQuery;
import com.ulewo.po.vo.PaginationResult;

/**
 * 
 * ClassName: TaskService
 * date: 2015年8月9日 上午11:45:23 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
public interface TaskService {

	public List<Task> findAllTask() throws BusinessException;

	/**
	 * 
	 * findTaskList:(分页查询任务). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public PaginationResult<Task> findTaskList(TaskQuery query);

	/**
	 * 
	 * findTaskById:(通过ID查询任务). <br/>
	 *
	 * @author 不错啊
	 * @param id
	 * @return
	 * @since JDK 1.7
	 */
	public Task findTaskById(Integer id);

	/**
	 * 
	 * saveTask:(保存任务). <br/>
	 *
	 * @author 不错啊
	 * @param task
	 * @param immediateExecution TODO
	 * @since JDK 1.7
	 */
	public void addTask(Task task, boolean immediateExecution) throws BusinessException;

	/**
	 * 
	 * updateTask:(更新任务). <br/>
	 *
	 * @author 不错啊
	 * @param task
	 * @param immediateExecution
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void updateTask(Task task, boolean immediateExecution) throws BusinessException;

	/**
	 * 
	 * suspendedTask:(暂停任务). <br/>
	 *
	 * @author 不错啊
	 * @param id
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void suspendedTask(Integer id) throws BusinessException;

	/**
	 * 
	 * enableTask:(恢复任务). <br/>
	 *
	 * @author 不错啊
	 * @param id
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void enableTask(Integer id) throws BusinessException;

	/**
	 * 
	 * immediateExecutionTask:(立即执行任务). <br/>
	 *
	 * @author 不错啊
	 * @param id
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void immediateExecutionTask(Integer id) throws BusinessException;

	/**
	 * 
	 * deleteTaskBatch:(批量删除任务). <br/>
	 *
	 * @author 不错啊
	 * @param ids
	 * @since JDK 1.7
	 */
	public void deleteTaskBatch(Integer[] ids) throws BusinessException;
}
