/**
 * Project Name:ulewo-web
 * File Name:UserServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年9月19日下午4:50:35
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bucuoa.ucenter.model.UlewoUser;
//import com.bucuoa.ucenter.model.User;
import com.ulewo.cache.BlackUserCache;
import com.ulewo.exception.BusinessException;
import com.ulewo.po.config.ConfigInfo;
import com.ulewo.po.enums.MessageType;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.MessageParams;
import com.ulewo.po.model.UserFriend;
//import com.ulewo.po.model.User;
import com.ulewo.po.query.UserQuery;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.service.MessageService;
import com.ulewo.service.UserFriendService;
import com.ulewo.service.UserService;
import com.ulewo.service.rpc.UserRpcServerServiceImpl;
import com.ulewo.utils.Constants;
import com.ulewo.utils.FileUtils;
import com.ulewo.utils.ServerUtils;
import com.ulewo.utils.StringTools;

/**
 * ClassName:UserServiceImpl <br/>
 * Date:     2015年9月19日 下午4:50:35 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserRpcServerServiceImpl userRpcServerService;

	@Resource
	private UserFriendService userFriendService;

	@Resource
	private MessageService messageService;

	@Resource
	private ConfigInfo configInfo;

	/*public void restister(User user) throws BusinessException {
		*//**
		 * 校验邮箱，用户名，密码是否合法
		 *//*
		String userName = user.getUserName();
		String email = user.getEmail();
		String password = user.getPassword();
		if (StringTools.isEmpty(userName) || StringTools.isEmpty(email) || StringTools.isEmpty(password)
				|| userName.length() > Constants.LENGTH_20 || password.length() > Constants.LENGTH_16
				|| password.length() < Constants.LENGTH_6 || !StringTools.checkEmail(email)
				|| !StringTools.checkUserName(userName) || !StringTools.checkPassWord(password)) {
			throw new BusinessException("输入参数不合法");
		}
		*//**
		 * 校验用户是否已经存在
		 *//*
		if (this.findUserByUserName(user.getUserName()) != null) {
			throw new BusinessException("用户已经存在");
		}
		*//**
		 * 校验邮箱是否已经存在
		 *//*
		if (this.findUserByEmail(user.getEmail()) != null) {
			throw new BusinessException("邮箱已经存在");
		}
		user.setPassword(StringTools.encodeByMD5(password));

		user.setUserBg(Constants.USER_IMG_PATH_USER_BG + ((int) (Math.random() * 10) + 1) + Constants.IMAGE_SUFFIX_JPG);
		Date curDate = new Date();
		user.setRegisterTime(curDate);
		user.setLastLoginTime(curDate);
		this.userMapper.insert(user);

		//复制头像，更新头像信息
		String icon = Constants.USER_IMG_PATH_USER_ICON + ((int) (Math.random() * 10) + 1) + ".png";
		String targetIcon = user.getUserId() + ".jpg";
		copyUserIcon(icon, targetIcon);
		user.setUserIcon(Constants.PATH_AVATARS_SUFFIX + targetIcon);
		this.updateInfo(user);
	}*/

	public void copyUserIcon(String sourceIcon, String targetIcon) {
		File sourcefile = new File(ServerUtils.getRealPath() + Constants.PATH_DEFAULT_USER_ICON + sourceIcon);
		File targetFile = new File(ServerUtils.getRealPath() + Constants.PATH_AVATARS + targetIcon);
		FileUtils.copyFile(sourcefile, targetFile);
	}

	@Override
	public UlewoUser login(String account, String password, Boolean encodePwd) throws BusinessException {
		if (StringTools.isEmpty(account) || StringTools.isEmpty(password)) {
			throw new BusinessException("输入参数不合法");
		}
		UlewoUser user = null;
		//邮箱登陆
		if (account.contains("@")) {
			user = this.findUserByEmail(account);
		} else {
			user = this.findUserByUserName(account);
		}
		if (null == user) {
			throw new BusinessException("用户不存在");
		}
		if (encodePwd) {
			password = StringTools.encodeByMD5(password);
		}
		if (!user.getPassword().equals(password)) {
			throw new BusinessException("密码错误");
		}
//		updateLastLoginTime(user.getUserId());
		return user;
	}

//	@Override
//	public void sendCheckCode(String email) throws BusinessException {
//		if (StringTools.isEmpty(email)) {
//			throw new BusinessException("请求参数错误");
//		}
//
//		User user = this.findUserByEmail(email);
//		if (null == user) {
//			throw new BusinessException("输入的邮箱不存在");
//		}
//		String checkCode = createCheckCode();
//		String title = "ulewo邮箱找回密码邮件";
//		StringBuilder content = new StringBuilder("亲爱的" + email + "<br><br>");
//		content.append("欢迎使用ulewo找回密码功能。(http://bucuoa.com)<br><br>");
//		content.append("您的验证码是：<span style='color:red;'>" + checkCode + "</span>,如果不是本人操作，请忽略此邮件<br><br>");
//		content.append("您的注册邮箱是:" + email + "<br><br>");
//		content.append("希望你在有乐窝社区的体验有益和愉快！<br><br>");
//		content.append("- 有乐窝社区(http://bucuoa.com)");
//		try {
//			SendMailUtils.sendEmail(configInfo.getFindemail(), configInfo.getFindpwd(), title, content.toString(),
//					new String[] { email });
//		} catch (Exception e) {
//			throw new BusinessException("发送邮件失败,请稍后再试");
//		}
//		//更新数据库
//		user.setActivationCode(checkCode);
//		this.userMapper.update(user);
//
//	}

	/*public void resetPwd(String email, String password, String checkCode) throws BusinessException {
		if (StringTools.isEmpty(email) || StringTools.isEmpty(password) || password.length() > Constants.LENGTH_16
				|| password.length() < Constants.LENGTH_6 || !StringTools.checkPassWord(password)) {
			throw new BusinessException("输入参数不合法");
		}
		User user = this.findUserByEmail(email);
		if (null == user) {
			throw new BusinessException("邮箱不存在");
		}
		if (!user.getActivationCode().equals(checkCode)) {
			throw new BusinessException("验证码错误");
		}
		user.setPassword(StringTools.encodeByMD5(password));
		this.userMapper.update(user);
	}
*/
/*	private String createCheckCode() {
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
		int codeCount = 6;
		Random random = new Random();
		StringBuilder randomCode = new StringBuilder();
		int codeLength = codeSequence.length;
		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeLength)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}*/
	
	public boolean isFriend(Integer uid,Integer friendId)
	{
		int findFocusCount = userFriendService.findFocusCount(uid, friendId);
		
		if(findFocusCount > 0)
		{
			return true;
		}
		
		return false;
	}


	public Integer myFriendCount(Integer uid)
	{
		int findFansCount = userFriendService.findFansCount(uid);
		
		return findFansCount;
	
	}
	
	public Integer focusMeCount(Integer uid)
	{
		int findFocusCount = userFriendService.findFocusCount(uid);
		
		return findFocusCount;
	}

	
	public UlewoUser findUserByUserName(String userName) {
//		String jsonAsString = HttpUtil.sendGet("http://ucenter.bucuoa.com/api/loadu/"+userName);
////		String jsonAsString = HttpUtil.sendGet("http://192.168.1.110:8070/api/loadu/"+userName);
//
//		AjaxResponse response = (AjaxResponse)JacksonMapper.fromJson(jsonAsString, AjaxResponse.class);
//		LinkedHashMap data = (LinkedHashMap)response.getData();
//		User user = (User)JacksonMapper.map2Json(data, User.class);
		UlewoUser user = userRpcServerService.findUserByUserName(userName);
		return user;
	}

	public UlewoUser findUserByEmail(String email) {
		UlewoUser userx = userRpcServerService.findUserByEmail(email);
//		String jsonAsString = HttpUtil.sendGet("http://ucenter.bucuoa.com/api/load/email/"+email);
//		AjaxResponse response = (AjaxResponse)JacksonMapper.fromJson(jsonAsString, AjaxResponse.class);
//		LinkedHashMap data = (LinkedHashMap)response.getData();
//		User user = (User)JacksonMapper.map2Json(data, User.class);
		return userx;
	}
	
//	public boolean update(String characters,String uid,String work,String age,String address) {
//		
//		String jsonAsString = HttpUtil.sendGet("http://ucenter.bucuoa.com/api/update?uid="+uid+"&characters="+characters);
//		AjaxResponse response = (AjaxResponse)JacksonMapper.fromJson(jsonAsString, AjaxResponse.class);
//
//		return true;
//	}

	@Override
	public UlewoUser findUserByUserId(String userId) {

//		String jsonAsString = HttpUtil.sendGet("http://ucenter.bucuoa.com/api/load/"+userId);
////		User user = (User)JacksonMapper.fromJson(jsonAsString, User.class);
//		
////		String jsonAsString = HttpUtil.sendGet("http://"+host+"/api/load/10000");
//		AjaxResponse response = (AjaxResponse)JacksonMapper.fromJson(jsonAsString, AjaxResponse.class);
//		Map data = (Map)response.getData();
//		JacksonMapper jackson = new JacksonMapper();
//		User user = (User)JacksonMapper.map2Json(data, User.class);
//		User user = new User();
//		user.setUserId(10000);
		UlewoUser user = userRpcServerService.findUserByUserId(userId);
		return user;
	}

	@Override
	public int changeMark(Integer userId, Integer mark) throws BusinessException {
		return 0 ;// userMapper.changeUserMark(userId, mark);
	}

	@Override
	public void updateInfo(UlewoUser user) throws BusinessException {
		if (!StringUtils.isEmpty(user.getAddress())
				&& user.getAddress().length() > TextLengthEnum.LENGTH_50.getLength()
				|| !StringUtils.isEmpty(user.getWork())
				&& user.getWork().length() > TextLengthEnum.LENGTH_50.getLength()
				|| !StringUtils.isEmpty(user.getCharacters())
				&& user.getCharacters().length() > TextLengthEnum.LENGTH_200.getLength()
				) {
			throw new BusinessException("参数错误");
		}
		
		userRpcServerService.updateInfo(user);
//		user.setLastLoginTime(null);
//		user.setRegisterTime(null);
//		user.setPassword(null);
//		user.setActivationCode(null);
////		userMapper.update(user);
//		QueryString qs = new QueryString("userid", user.getUserId()+"");
//		qs.add("characters", user.getCharacters());
//		qs.add("work", user.getWork());
//		qs.add("birthday", user.getBirthday());
//		qs.add("sex", user.getSex());
//		qs.add("address", user.getAddress());
//		
//		String url = "http://ucenter.bucuoa.com/api/update?"+qs;
//		String jsonAsString = HttpUtil.sendGet(url);
//		AjaxResponse response = (AjaxResponse)JacksonMapper.fromJson(jsonAsString, AjaxResponse.class);
	}
	
	@Override
	public void updateInfoBg(UlewoUser user) throws BusinessException {
		if (!StringUtils.isEmpty(user.getAddress())
				&& user.getAddress().length() > TextLengthEnum.LENGTH_50.getLength()
				|| !StringUtils.isEmpty(user.getWork())
				&& user.getWork().length() > TextLengthEnum.LENGTH_50.getLength()
				|| !StringUtils.isEmpty(user.getCharacters())
				&& user.getCharacters().length() > TextLengthEnum.LENGTH_200.getLength()
				) {
			throw new BusinessException("参数错误");
		}
		userRpcServerService.updateInfo(user);
	}
	
	@Override
	public void updateInfoIcon(UlewoUser user) throws BusinessException {
		if (!StringUtils.isEmpty(user.getAddress())
				&& user.getAddress().length() > TextLengthEnum.LENGTH_50.getLength()
				|| !StringUtils.isEmpty(user.getWork())
				&& user.getWork().length() > TextLengthEnum.LENGTH_50.getLength()
				|| !StringUtils.isEmpty(user.getCharacters())
				&& user.getCharacters().length() > TextLengthEnum.LENGTH_200.getLength()
			) {
			throw new BusinessException("参数错误");
		}
//		user.setLastLoginTime(null);
//		user.setRegisterTime(null);
//		user.setPassword(null);
//		user.setActivationCode(null);
////		userMapper.update(user);
//		
//		QueryString qs = new QueryString("userid", user.getUserId()+"");
//		qs.add("userIcon", user.getUserIcon());
//		
//		String url = "http://ucenter.bucuoa.com/api/updateicon?"+qs;
//		String jsonAsString = HttpUtil.sendGet(url);
//		
//		AjaxResponse response = (AjaxResponse)JacksonMapper.fromJson(jsonAsString, AjaxResponse.class);
		
		userRpcServerService.updateInfo(user);
	}
	

	@Override
	public void updateLastLoginTime(Integer userId) {
		UlewoUser newUser = new UlewoUser();
//		newUser.
//		newUser.setLastLoginTime(new Date());
//		newUser.setUserId(userId);
//		userMapper.update(newUser);
	}

	@Override
	public void changePwd(Integer userId, String oldPwd, String newPwd) throws BusinessException {

		if (StringTools.isEmpty(oldPwd) || StringTools.isEmpty(newPwd) || newPwd.length() > Constants.LENGTH_16
				|| !StringTools.checkPassWord(newPwd)) {
			throw new BusinessException("参数不合法");
		}
		UlewoUser user = this.findUserByUserId(userId.toString());
		if (!user.getPassword().equals(StringTools.encodeByMD5(oldPwd))) {
			throw new BusinessException("原始密码不正确");
		}
		UlewoUser user2 = new UlewoUser();
		user2.setPassword(StringTools.encodeByMD5(newPwd));
		user2.setUserId(user.getUserId());
//		userMapper.update(user);
		userRpcServerService.updateInfo(user2);
	}

//	@Override
//	public UlewoUser findUserInfo4UserHome(Integer userId, Integer sessionUserId) throws BusinessException {
//		UlewoUser user = this.findUserByUserId(userId.toString());
//		if (null == user) {
//			throw new BusinessException("用户不存在");
//		}
//		boolean haveFocus = false;
//		if (sessionUserId != null) {
//			int count = userFriendService.findFocusCount(sessionUserId, userId);
//			if (count > 0) {
//				haveFocus = true;
//			}
//		}
//		
////		user.setMark(1);
////		user.setUserId(10000);
////		user.setUserName("吴江");
//		if(user.getUserBg() == null)
//		{
//			user.setUserBg("defbg/bg3.jpg");
//		}
////		user.setFansCount(userFriendService.findFansCount(userId));
////		user.setFocusCount(userFriendService.findFocusCount(userId));
////		user.setHaveFocus(haveFocus);
//		user.setPassword(null);
//		user.setActivationCode(null);
//		user.setEmail(null);
//		return user;
//	}

	public PaginationResult<UlewoUser> findUserByPage(UserQuery query) {
//		int count = this.userMapper.selectCount(query);
//		int pageSize = PageSize.SIZE20.getSize();
//		int pageNo = 0;
//		if (null != query.getPageNo()) {
//			pageNo = query.getPageNo();
//		}
//		SimplePage page = new SimplePage(pageNo, count, pageSize);
//		query.setPage(page);
//		List<User> list = this.userMapper.selectList(query);
//		PaginationResult<User> result = new PaginationResult<User>(page, list);
//		return result;
		return null;
	}

	@Override
	public void rewardMark(Integer userId, Integer mark, String message) {
//		this.userMapper.changeUserMark(userId, mark);
//		//发送消息
//		Set<Integer> userList = new HashSet<Integer>();
//		userList.add(userId);
//		MessageParams messageParams = new MessageParams();
//		messageParams.setMessageType(MessageType.SYSTEM_MARK);
//		messageParams.setReceiveUserIds(userList);
//		messageParams.setMessage(message);
//		messageService.createMessage(messageParams);
	}

	public void warnUser(Integer userId, String message) {
		Set<Integer> userList = new HashSet<Integer>();
		userList.add(userId);
		MessageParams messageParams = new MessageParams();
		messageParams.setMessageType(MessageType.SYSTEM_WARN);
		messageParams.setReceiveUserIds(userList);
		messageParams.setMessage(message);
		messageService.createMessage(messageParams);
	}

	public void delete(Integer userId) {
		UserQuery query = new UserQuery();
		query.setUserId(userId.toString());
//		this.userMapper.delete(query);
		//加入黑名单缓存
		BlackUserCache.AddUser(userId);
	}




	@Override
	public void warnUlewoUser(Integer userId, String message) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void copyUlewoUserIcon(String sourceIcon, String targetIcon) {
		// TODO Auto-generated method stub
		
	}
}
