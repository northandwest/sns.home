package com.ulewo.po.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.ulewo.utils.StringTools;

public class PageTag extends BodyTagSupport {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 提交的地址
	 */
	private String url = "";

	/**
	 * 当前页
	 */
	private String pageStr;

	/**
	 * 总页数
	 */
	private String pageTotalStr;

	private String countTotalStr;

	/**
	 * 起始页
	 */
	public static int beginNum = 0;

	/**
	 * 结尾页
	 */
	public static int endNum = 0;

	/**
	 * 分页显示的大数量
	 */
	public static final int pageNum = 10;

	/**
	 * 标签处理程序
	 */
	public int doStartTag() throws JspException {
		int page = 1;
		if (StringTools.isNumber(pageStr)) {
			page = Integer.parseInt(pageStr);
		}
		int pageTotal = Integer.parseInt(pageTotalStr);
		int countTotal = Integer.parseInt(countTotalStr);
		// 要输出到页面的HTML文本
		StringBuffer sb = new StringBuffer();
		if (pageNum > pageTotal) {
			beginNum = 1;
			endNum = pageTotal;
		}

		if (page - pageNum / 2 < 1) {
			beginNum = 1;
			endNum = pageNum;
		} else {
			beginNum = page - pageNum / 2 + 1;
			endNum = page + pageNum / 2;
		}

		if (pageTotal - page < pageNum / 2) {
			beginNum = pageTotal - pageNum + 1;
		}
		if (beginNum < 1) {
			beginNum = 1;
		}
		if (endNum > pageTotal) {
			endNum = pageTotal;
		}
		sb.append("<ul class='pagination'>");
		if (page > 1) {
			sb.append("<li><a href='" + url + "1'>首&nbsp;&nbsp;页</a></li>");
			sb.append("<li><a href='" + url + (page - 1) + "'><</a></li>");
		} else {
			sb.append("<li><span>首&nbsp;&nbsp;页</span></li>");
			sb.append("<li><span><</span></li>");
		}

		if (pageTotal == 1) {
			sb.append("<li id='nowPage'>1</li>");
		}

		for (int i = beginNum; i <= endNum; i++) {
			if (pageTotal > 1) {
				if (i == page) {
					sb.append("<li id='nowPage'>" + page + "</li>");
				} else {
					sb.append("<li><a href='" + url + i + " '>" + i + "</a></li>");
				}
			}
		}
		if (page < pageTotal) {
			sb.append("<li><a href='" + url + (page + 1) + "'>></a></li>");
			sb.append("<li><a href='" + url + (pageTotal) + "'>尾&nbsp;&nbsp;页</a></li>");
		} else {
			sb.append("<li><span>></span></li>");
			sb.append("<li><span>尾&nbsp;&nbsp;页</span></li>");
		}
		sb.append("<li><span>总共" + countTotalStr + "条记录，当前" + page + "/" + pageTotal + "页</span></li>");
		sb.append("</ul>");
		try {
			if (pageTotal > 0 && countTotal > 0) {
				pageContext.getOut().println(sb.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		if (url.indexOf("?") > 0) {
			this.url = url + "&pageNo=";
		} else {
			this.url = url + "?pageNo=";
		}
	}

	public String getPageStr() {
		return pageStr;
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public String getPageTotalStr() {
		return pageTotalStr;
	}

	public void setPageTotalStr(String pageTotalStr) {
		this.pageTotalStr = pageTotalStr;
	}

	public String getCountTotalStr() {
		return countTotalStr;
	}

	public void setCountTotalStr(String countTotalStr) {
		this.countTotalStr = countTotalStr;
	}

}