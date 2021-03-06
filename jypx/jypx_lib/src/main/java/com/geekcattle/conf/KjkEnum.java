package com.geekcattle.conf;
/**
 * 课件库Long类型常量
 * @author Administrator
 *
 */
public enum KjkEnum {
	/**
	 * 课件表状态（有效）
	 */
	KJK_COURSEWARE_STATUS_ENABLE(0L),
	/**
	 * 课件表状态（删除）
	 */
	KJK_COURSEWARE_STATUS_DISABLE(1L),
	/**
	 * 课件表状态（下架）
	 */
	KJK_COURSEWARE_STATUS_DOWN(-1L),
	/**
	 * 题库中默认选项数量
	 */
	KJK_QUESTION_OPTION_NUMBER(4L);
	private Long value;
	private KjkEnum(Long value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value.toString();
	}	
	public Long getValue() {
		return value;
	}
}
