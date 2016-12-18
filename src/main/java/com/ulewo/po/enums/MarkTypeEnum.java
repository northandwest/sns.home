package com.ulewo.po.enums;

/**
 * 
 * ClassName: 积分类型 date: 2015年8月9日 下午12:04:13
 * 
 * @author 不错啊
 * @version
 * @since JDK 1.7
 */
public enum MarkTypeEnum {
	MARK_SIGNIN(1, "签到"), MARK_TOPIC(2, "讨论帖"), MARK_KNOWLEDGE(3, "知识库"), MARK_BLOG(4, "博客"), MARK_EXAM(5,
			"考题"), MARK_SIGNIN_CONTINUE(6, "连续签到"), MARK_SPIT_SLOT(7, "吐槽"), MARK_SPIT_SLOT_COMMENT(8,
					"吐槽评论"), COMMENT(9, "评论");

	private int type;
	private String desc;

	private MarkTypeEnum(int mark, String desc) {
		this.type = mark;
	}

	public int getType() {
		return this.type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setMark(int type) {
		this.type = type;
	}

}
