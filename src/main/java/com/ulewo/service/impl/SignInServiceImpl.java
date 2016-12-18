/**
 * Project Name:ulewo-web
 * File Name:SignInServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年9月23日下午8:58:34
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.SignInMapper;
import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.enums.MarkEnum;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.model.Calendar4Signin;
import com.ulewo.po.model.DaySignInfo;
import com.ulewo.po.model.SessionUser;
import com.ulewo.po.model.SignIn;
import com.ulewo.po.model.SignInInfo;
import com.ulewo.po.query.SignInQuery;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.SignInService;
import com.ulewo.service.UserService;
import com.ulewo.utils.Constants;
import com.ulewo.utils.DateUtil;

/**
 * ClassName:SignInServiceImpl <br/>
 * Date:     2015年9月23日 下午8:58:34 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("signInService")
public class SignInServiceImpl implements SignInService {

	@Resource
	private SignInMapper<SignIn, SignInQuery> signInMapper;

	@Resource
	private UserService userService;

	@Override
	public SignInInfo findSignInInfoByUserId(Integer userId) {

		SignInInfo signInInfo = new SignInInfo();
		SignInQuery query = new SignInQuery();

		Date curDate = new Date();
		// 查询当日所有签到数量
		query.setCurDate(curDate);
		int todaySigInCount = this.signInMapper.selectCount(query);
		signInInfo.setTodaySigInCount(todaySigInCount);
		//用户没有登录，显示未签到，已经签到数量为0
		if (null == userId) {
			signInInfo.setHaveSignInToday(Boolean.FALSE);
			signInInfo.setUserSignInCount(0);
		} else {
			//查询用户所有签到数量
			query = new SignInQuery();
			query.setUserId(userId);
			int userSignInCount = this.signInMapper.selectCount(query);
			signInInfo.setUserSignInCount(userSignInCount);

			//查询用户当日签到数量
			query.setCurDate(curDate);
			int todaySignInCount = this.signInMapper.selectCount(query);
			if (todaySignInCount == 0) {
				signInInfo.setHaveSignInToday(Boolean.FALSE);
			} else {
				signInInfo.setHaveSignInToday(Boolean.TRUE);
			}

		}
		signInInfo.setCurDay(DateUtil.format(new Date(), DateTimePatternEnum.MM_POINT_DD.getPattern()));
		signInInfo.setWeek(DateUtil.getWeekCN(curDate));
		return signInInfo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public SignIn doSignIn(SessionUser sessionUser) throws BusinessException {
		// 判断今天是否已经签到
		Date curDate = new Date();
		SignInQuery query = new SignInQuery();
		query.setUserId(sessionUser.getUserId());
		query.setCurDate(curDate);
		int todaySignInCount = this.signInMapper.selectCount(query);
		if (todaySignInCount > 0) {
			throw new BusinessException("今天已经签到!");
		}
		// 签到
		SignIn signIn = new SignIn();
		signIn.setUserIcon(sessionUser.getUserIcon());
		signIn.setUserId(sessionUser.getUserId());
		signIn.setUserName(sessionUser.getUserName());
		signIn.setSignDate(curDate);
		signIn.setSignTime(curDate);
		this.signInMapper.insert(signIn);

		// 判断是否是连续7天签到
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -6);
		Date startDate = c.getTime();
		query.setCurDate(null);
		query.setStartDate(startDate);
		query.setEndDate(curDate);
		int count = this.signInMapper.selectCount(query);

		//写入积分
		int mark = MarkEnum.MARK_SIGNIN.getMark();
		if (count >= Constants.CONTINUESIGINCOUNT) {
			mark = MarkEnum.MARK_SIGNIN_CONTINUE.getMark();
			signIn.setContinueSigIn(Boolean.TRUE);
		} else {
			signIn.setContinueSigIn(Boolean.FALSE);
		}
		userService.changeMark(mark, sessionUser.getUserId());
		return signIn;
	}

	@Override
	public List<Calendar4Signin> findUserSiginsByYear(Integer userId, Integer year) {
		Calendar c = Calendar.getInstance();
		if (null == year) {
			year = c.get(Calendar.YEAR);
		}
		//一年的月份
		final int YEARMONTH = 12;
		//当月月份
		int curMonth = c.get(Calendar.MONTH) + 1;
		SignInQuery query = new SignInQuery();
		query.setYear(year);
		query.setUserId(userId);
		List<SignIn> list = this.signInMapper.selectList(query);
		Map<String, String> siginMap = new HashMap<String, String>();
		// 签到情况放到map中
		for (SignIn sign : list) {
			String dateStr = DateUtil.format(sign.getSignDate(), DateTimePatternEnum.YYYY_MM_DD.getPattern());
			siginMap.put(dateStr, dateStr);
		}
		// 获取日历信息
		List<Calendar4Signin> resultlist = new ArrayList<Calendar4Signin>();
		for (int i = 1; i <= YEARMONTH; i++) {
			Calendar4Signin sigin = new Calendar4Signin();
			resultlist.add(sigin);
			Map<String, Integer> dayMonth = DateUtil.getTotalDayAndFirstWeekDay4Month(year, i, 1);
			int thisMonthDays = dayMonth.get("totalDay");
			int fristDay = dayMonth.get("firstWeekDay");
			// 0 代表前面补充0  4 代表长度为4  d 代表参数为正数型 
			sigin.setMonth(String.format("%02d", i));
			sigin.setFristDay(fristDay);
			sigin.setMonthDays(thisMonthDays);
			List<DaySignInfo> dayList = new ArrayList<DaySignInfo>();
			sigin.setDayInfos(dayList);

			// 遍历日期天数
			for (int j = 1; j <= thisMonthDays; j++) {
				DaySignInfo info = new DaySignInfo();
				dayList.add(info);
				String day = String.format("%02d", j);
				String curDay = year + "-" + sigin.getMonth() + "-" + day;

				//判断是否是当天
				if (curDay.equals(DateUtil.format(new Date(), DateTimePatternEnum.YYYY_MM_DD.getPattern()))) {
					info.setCurDay(Boolean.TRUE);
				} else {
					info.setCurDay(Boolean.FALSE);
				}
				info.setDay(day);
				//判断是否已经签到
				if (siginMap.get(curDay) != null) {
					info.setSigninType(true);
				} else {
					info.setSigninType(false);
				}

				/**
				 * 判断日期是否是当天之前
				 */
				//月份小于当月，或者标示符是true，那么表示是在当天之前
				if (i < curMonth) {
					info.setBeforeNowDate(true);
				} else if (i == curMonth) {
					//如果是当月并且是在当天之前，那么就要判断具体日期
					if (DateUtil.beforeNowDate(curDay)) {
						info.setBeforeNowDate(true);
					} else {
						//如果是在当天之后，那么表示符为false，后面的日期就不用判断具体日期了，直接就是在当天之后了
						info.setBeforeNowDate(false);
					}
				} else {
					info.setBeforeNowDate(false);
				}
			}
		}
		return resultlist;
	}

	@Override
	public PaginationResult<SignIn> findCurDaySigin(SignInQuery query) {
		query.setCurDate(new Date());
		int count = this.signInMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		List<SignIn> list = this.signInMapper.selectList(query);
		PaginationResult<SignIn> result = new PaginationResult<SignIn>(page, list);
		return result;
	}

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -6);
		Date endDate = c.getTime();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endDate));

		System.out.println(String.format("%02d", 10));
	}
}
