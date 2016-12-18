/**
 * Project Name:ulewo-common
 * File Name:BaseQuery.java
 * Package Name:com.ulewo.po.query
 * Date:2015年9月19日下午5:08:25
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.po.query;

import com.ulewo.po.vo.SimplePage;

/**
 * ClassName:BaseQuery <br/>
 * Date:     2015年9月19日 下午5:08:25 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class BaseQuery {
	private SimplePage page;
	private Integer pageNo;

	public SimplePage getPage() {
		return page;
	}

	public void setPage(SimplePage page) {
		this.page = page;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
}
