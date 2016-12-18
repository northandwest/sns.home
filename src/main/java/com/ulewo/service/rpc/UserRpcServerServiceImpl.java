package com.ulewo.service.rpc;

import org.springframework.stereotype.Service;

import com.bucuoa.ucenter.api.UserRpcService;
import com.bucuoa.ucenter.model.UlewoUser;
import com.ulewo.service.UserRpcServerService;
import com.ulewo.utils.SpringContextUtil;

@Service("userRpcServerService")
public class UserRpcServerServiceImpl implements UserRpcServerService {
//	@Autowired
//	@Qualifier("userRpcService")
	private UserRpcService userRpcService = (UserRpcService)SpringContextUtil.getBean("userRpcService");
	
	@Override
	public UlewoUser findUserByEmail(String value) {
		
		UlewoUser user = userRpcService.findUserByEmail(value);
		return user;
	}
	
	public UlewoUser findUserByUserId(String userId)
	{
		UlewoUser user = userRpcService.findUserByUserId(userId);
		
		return user;
	}


	@Override
	public void updateInfo(UlewoUser user) {
		userRpcService.updateInfo(user);		
	}

	@Override
	public UlewoUser findUserByUserName(String userName) {
		UlewoUser user = userRpcService.findUserByUserName(userName);
		return user;
	}
	
}
