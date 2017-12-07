package com.geekcattle.conf;
/**
 * 
 * @author Administrator
 * 字符常量类 
 */
public enum ConstantEnum {
	/**
	 *  INFO删除状态
	 */
	INFO_DISABLE("1"),
	/**
	 * INFO删除状态
	 */
	INFO_ENABLE("0"),
	/**
	 *  付费标志（0 未付费）
	 */
	KJK_COURSEWARE_PLY_FLAG_NOT("0"),
	/**
	 * 付费标志（1 付费）
	 */
	KJK_COURSEWARE_PLY_FLAG_IS("1"),
	/**
	 * 课件来源 cme
	 */
	KJK_COURSEWARE_SOURCE_CME("cme"),
	/**
	 * 课件来源 基层
	 */
	KJK_COURSEWARE_SOURCE_BASE("基层"),
	/**
	 * 课件来源 住院医
	 */
	KJK_COURSEWARE_SOURCE_RCT("住院医"),
	/**
	 * 课件来源 医护考
	 */
	KJK_COURSEWARE_SOURCE_EXAM("医护考"),
	/**
	 * 课件来源 乡医
	 */
	KJK_COURSEWARE_SOURCE_COUNTRY_DOCTORS("乡医"),
	/**
	 * 下载课件表EXCEL文件名
	 */
	DOWNLOAD_COURSEWARE_FILENAME("课件表"),
	/**
	 * 下载课件表EXCEL标题名
	 */
	DOWNLOAD_COURSEWARE_TITLENAME("课件表"),
	/**
	 * 下载课件表EXCEL sheet名称
	 */
	DOWNLOAD_COURSEWARE_SHEETNAME("课件表"),
	;
	private String value;
	private ConstantEnum(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
}
