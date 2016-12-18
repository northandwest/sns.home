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
 * Date: 2015年9月19日 下午4:50:03 <br/>
 * 
 * @author 不错啊 Copyright (c) 2015, bucuoa.com All Rights Reserved.
 */
public interface UserRpcServerService {
	UlewoUser findUserByEmail(String value);

	UlewoUser findUserByUserId(String userId);

	UlewoUser findUserByUserName(String userName);

	void updateInfo(UlewoUser user);

}
