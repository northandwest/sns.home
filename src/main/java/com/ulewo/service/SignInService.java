/**
 * Project Name:ulewo-web
 * File Name:SignInService.java
 * Package Name:com.ulewo.service
 * Date:2015年9月23日下午8:46:21
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.Calendar4Signin;
import com.ulewo.po.model.SessionUser;
import com.ulewo.po.model.SignIn;
import com.ulewo.po.model.SignInInfo;
import com.ulewo.po.query.SignInQuery;
import com.ulewo.po.vo.PaginationResult;

/**
 * ClassName:SignInService <br/>
 * Date:     2015年9月23日 下午8:46:21 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface SignInService {
	/**
	 * findSignInInfoByUserId:(获取用户当天签到信息). <br/>
	 *
	 * @author 不错啊
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public SignInInfo findSignInInfoByUserId(Integer userId);

	/**
	 * doSignIn:(签到). <br/>
	 *
	 * @author 不错啊
	 * @param sessionUser
	 * @return TODO
	 * @since JDK 1.7
	 */
	public SignIn doSignIn(SessionUser sessionUser) throws BusinessException;

	/**
	 * 
	 * queryUserSigin:(根据年份获取用户的签到信息). <br/>
	 *
	 * @author 不错啊
	 * @param userId
	 * @param year
	 * @return
	 * @since JDK 1.7
	 */
	public List<Calendar4Signin> findUserSiginsByYear(Integer userId, Integer year);

	/**
	 * 
	 * queryCurDaySigin:(查询当天所有的签到信息). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public PaginationResult<SignIn> findCurDaySigin(SignInQuery query);
}
