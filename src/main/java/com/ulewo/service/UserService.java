/**
 * Project Name:ulewo-web
 * File Name:UlewoUserService.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年9月19日下午4:50:03
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import com.bucuoa.ucenter.model.UlewoUser;
import com.ulewo.exception.BusinessException;
import com.ulewo.po.query.UserQuery;
import com.ulewo.po.vo.PaginationResult;

/**
 * ClassName:UlewoUserService <br/>
 * Date:     2015年9月19日 下午4:50:03 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface UserService {
	/**
	 * 
	 * restister:(注册)
	 * @author luohaili
	 * @param user
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
//	public void restister(UlewoUser user) throws BusinessException;

	/**
	 * 
	 * findUlewoUserByUlewoUserName:(根据用户名查询用户)
	 * @author luohaili
	 * @param userName
	 * @return
	 * @since JDK 1.7
	 */
	public UlewoUser findUserByUserName(String userName);
//	public boolean update(String characters,String uid,String work,String age,String address);
	/**
	 * 
	 * findUlewoUserByEmail:(根据邮箱查询用户)
	 * @author luohaili
	 * @param email
	 * @return
	 * @since JDK 1.7
	 */
	public UlewoUser findUserByEmail(String email);

	/**
	 * 
	 * findUlewoUserByUlewoUserID:(通过userId获取用户信息). <br/>
	 *
	 * @author 不错啊
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public UlewoUser findUserByUserId(String userId);

	/**
	 * 
	 * login:(登录)
	 * @author luohaili
	 * @param account
	 * @param password
	 * @param encodePwd TODO
	 * @return
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public UlewoUser login(String account, String password, Boolean encodePwd) throws BusinessException;

	/**
	 * 
	 * sendCheckCode:(发送验证码)
	 * @author luohaili
	 * @param email
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
//	public void sendCheckCode(String email) throws BusinessException;

	/**
	 * 
	 * resetPwd:(重置密码)
	 * @author luohaili
	 * @param email
	 * @param password
	 * @param checkCode
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
//	public void resetPwd(String email, String password, String checkCode) throws BusinessException;

	/**
	 * addMark:(加积分)
	 * @author luohaili
	 * @param userId
	 * @param mark
	 * @return TODO
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public int changeMark(Integer userId, Integer mark) throws BusinessException;

	/**
	 * 
	 * update:(更新用户信息). <br/>
	 *
	 * @author 不错啊
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void updateInfo(UlewoUser user) throws BusinessException;

	public void updateInfoBg(UlewoUser user) throws BusinessException;

	public void updateInfoIcon(UlewoUser user) throws BusinessException;

	/**
	 * 更新最后登录时间
	 * updateLastLoginTime:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param user
	 * @since JDK 1.7
	 */
	public void updateLastLoginTime(Integer userId);

	/**
	 * 
	 * changePwd:(修改密码)
	 * @author luohaili
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @since JDK 1.7
	 */
	public void changePwd(Integer userId, String oldPwd, String newPwd) throws BusinessException;

	/**
	 * 
	 * copyUlewoUserIcon:(复制用户头像)
	 * @author luohaili
	 * @param sourceIcon
	 * @param targetIcon
	 * @since JDK 1.7
	 */
	public void copyUlewoUserIcon(String sourceIcon, String targetIcon);

	/**
	 * 
	 * findUlewoUserInfo4UlewoUserHome:(查询个人主页信息)
	 *
	 * @author 不错啊
	 * @param userId
	 * @param sessionUlewoUserId TODO
	 * @return
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
//	public UlewoUser findUserInfo4UserHome(Integer userId, Integer sessionUlewoUserId) throws BusinessException;

	/**
	 * 
	 * findUlewoUserByPage:(分页查询用户)
	 * @author luohaili
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
//	public PaginationResult<UlewoUser> findUlewoUserByPage(UserQuery query);

	/**
	 * 
	 * rewardMark:(奖励积分)
	 * @author luohaili
	 * @param userId
	 * @param mark
	 * @since JDK 1.7
	 */
	public void rewardMark(Integer userId, Integer mark, String message);

	/**
	 * 
	 * warnUlewoUser:(发送警告信息)
	 * @author luohaili
	 * @param userId
	 * @param message
	 * @since JDK 1.7
	 */
	public void warnUlewoUser(Integer userId, String message);

	/**
	 * 删除用户
	 * delete:(这里用一句话描述这个方法的作用)
	 * @author luohaili
	 * @param userId
	 * @since JDK 1.7
	 */
	public void delete(Integer userId);
}
