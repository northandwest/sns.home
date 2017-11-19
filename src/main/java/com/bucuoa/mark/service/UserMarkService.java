package com.bucuoa.mark.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bucuoa.mark.dao.UserMarkDao;
import com.bucuoa.mark.entity.UserMark;
import com.bucuoa.west.orm.app.common.Expression;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.app.extend.SingleBaseService;
import com.bucuoa.west.orm.app.utils.RequestConverter;

@Service
public class UserMarkService extends SingleBaseService<UserMark, Long> {
	@Autowired
	private UserMarkDao dao;

	public UserMarkDao getDao() {
		return dao;
	}

	protected String getReqValByParam(HttpServletRequest request, String param) {
		String value = request.getParameter(param);
		return (value == null) ? "" : value.trim();
	}

	public List<Map<String, Object>> queryListMap(String sql) {
		return getDao().queryListMap(sql);
	}

	@Transactional(readOnly = true)
	public WPage getEntityPage(HttpServletRequest request) throws Exception {
		// filters_like_name filters_equals_categoryName
		int pageNO = 0;
		Map map = request.getParameterMap();

		RequestConverter rq = new RequestConverter(map);

		List<Expression> wheres = rq.getWhereCondition();
		pageNO = rq.getPageNO();

		WPage page = new WPage();
		page.setPageSize(rq.getPageSize());
		page.setPageNo(pageNO);

		int count = getDao().getEntityCount(wheres);
		List<UserMark> data = getDao().findEntityList(wheres, page);

		page.setTotalCount(count);
		page.setData(data);

		return page;
	}

}
				 
								 
								