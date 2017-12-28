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
////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 *  付费标志（0 未付费）
	 */
	KJK_COURSEWARE_PLY_FLAG_NOT("0"),
	/**
	 * 付费标志（1 付费）
	 */
	KJK_COURSEWARE_PLY_FLAG_IS("1"),
///////////////////////////////////////////////////////////////////////////////////////////////////	
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
///////////////////////////////////////////////////////////////////////////////////////////////////	
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
	/**
	 * 下载劳务费表EXCEL文件名
	 */
	DOWNLOAD_COST_FILENAME("劳务费表"),
	/**
	 * 下载劳务费表EXCEL标题名
	 */
	DOWNLOAD_COST_TITLENAME("劳务费表"),
	/**
	 * 下载劳务费表EXCEL sheet名称
	 */
	DOWNLOAD_COST_SHEETNAME("劳务费表"),
///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 项目级别 普通项目	
	 */
	KJK_COURSEWARE_PROJECT_LEVEL_COMMON("0"),
	/**
	 * 项目级别 国家级项目	
	 */
	KJK_COURSEWARE_PROJECT_LEVEL_NATION("1"),
	
///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * cc视频格式
	 */
	KJK_PLAY_TYPE_CC("13"),
	/**
	 * cme视频格式
	 */
	KJK_PLAY_TYPE_CME("1"),
///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 劳务费数据状态
	 */
	KJK_COST_STATUS_ENABLE("0"),
	/**
	 * 劳务费数据状态
	 */
	KJK_COST_STATUS_DISABLE("1"),
//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 劳务费审核状态 未审核
	 */
	KJK_COST_AUDIT_STATUS_NOT("0"),
	/**
	 * 劳务费审核状态 已审核
	 */
	KJK_COST_AUDIT_STATUS_IS("1"),
//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 字典表数据状态
	 */
	KJK_DIC_STATUS_ENABLE("0"),
	/**
	 * 字典表数据状态
	 */
	KJK_DIC_STATUS_DISABLE("1"),
////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 劳务费归属项目字典类型
	 */
	KJK_DIC_TYPE_SYSTEM("SYSTEM")
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
