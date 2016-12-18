package com.ulewo.po.enums;

/**
 * 
 * ClassName: 积分 date: 2015年8月9日 下午12:04:13
 * 
 * @author 不错啊
 * @version
 * @since JDK 1.7
 */
public enum MarkEnum {
	MARK_SIGNIN(2, "签到"), MARK_TOPIC(5, "讨论帖"), MARK_KNOWLEDGE(5, "知识库"), MARK_BLOG(5, "博客"), MARK_EXAM(5,
			"考题"), MARK_SIGNIN_CONTINUE(10, "连续签到"), MARK_SPIT_SLOT(2, "吐槽"), MARK_SPIT_SLOT_COMMENT(1,
					"吐槽评论"), COMMENT(2, "评论");

	private int mark;
	private String desc;

	private MarkEnum(int mark, String desc) {
		this.mark = mark;
	}

	public int getMark() {
		return this.mark;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

}
