package com.geekcattle.conf;

public enum ModuleEnum {
	/**
	 * Banner
	 */
	BANNER("01"),
	/**
	 * 广告
	 */
	ADVERT("02"),
	/**
	 * 顶部链接
	 */
	TOP_LINK("03"),
	/**
	 * 友情链接
	 */
	FRIEND_LINK("04"),
	/**
	 * CME
	 */
	CME("05"),
	/**
	 * 继教政策
	 */
	EDUCATION_POLICY("0501"),
	/**
	 * CME特色项目
	 */
	CME_PROJECT("0502"),
	/**
	 * 课程推荐（海外）
	 */
	COURSE_ABROAD("0503"),
	/**
	 * 课程推荐（CME）
	 */
	COURSE_CME("0504"),
	/**
	 * 科教
	 */
	SCIENCE_EDUCATION("06"),
	/**
	 * 科教系统
	 */
	SCIENCE_EDUCATION_SYS("0601"),
	/**
	 * 科教系统 通知
	 */
	SCIENCE_EDUCATION_NOTICE("0602"),
	/**
	 * 科教系统 政策
	 */
	SCIENCE_EDUCATION_POLICY("0603"),
	/**
	 * 基层
	 */
	GRASS_ROOT("07"),
	/**
	 * 基层 通知
	 */
	GRASS_ROOT_NOTICE("0701"),
	/**
	 * 基层 政策
	 */
	GRASS_ROOT_POLICY("0702"),
	/**
	 * 护士定考
	 */
	NURSE_EXAM("08"),
	/**
	 * 护士定考 通知
	 */
	NURSE_EXAM_NOTICE("0801"),
	/**
	 * 护士定考 政策
	 */
	NURSE_EXAM_POLICY("0802"),
	/**
	 * 医师定考
	 */
	DOCTOR__EXAM("09"),
	/**
	 * 医师定考 通知
	 */
	DOCTOR__EXAM_NOTICE("0901"),
	/**
	 * 医师定考 政策
	 */
	DOCTOR__EXAM_POLICY("0902"),
	/**
	 * 课程推荐（海外）
	 */
	COURSE_ABROAD_VIEW("海外"),
	/**
	 * 课程推荐（CME）
	 */
	COURSE_CME_VIEW("CME"),
	/**
	 * 医师定考
	 */
	DOCTOR__EXAM_VIEW("医师定考"),
	/**
	 * 护士定考
	 */
	NURSE_EXAM_VIEW("护士定考");

	private String module;
	private ModuleEnum(String module){
        this.module = module;
    }

    @Override
    public  String toString(){
        return this.module.toString();
    }
}
