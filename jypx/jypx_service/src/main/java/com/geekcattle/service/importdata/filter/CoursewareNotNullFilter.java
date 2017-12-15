package com.geekcattle.service.importdata.filter;

import org.apache.commons.lang3.StringUtils;

import com.geekcattle.vo.kjk.CoursewareVo;

public class CoursewareNotNullFilter implements ImportDataFilter<CoursewareVo>{

	@Override
	public String doFilter(CoursewareVo courseware) {
		StringBuilder sbuilder = new StringBuilder();
		if(StringUtils.isBlank(courseware.getName())) {
			sbuilder.append("课件名称为必填项，");
		}
		if(StringUtils.isBlank(courseware.getpName())) {
			sbuilder.append("课程名称为必填项，");
		}
		if(StringUtils.isBlank(courseware.getExpert())) {
			sbuilder.append("专家为必填项，");
		}
		if(StringUtils.isBlank(courseware.getExpertUnit())) {
			sbuilder.append("专家单位为必填项，");
		}
		if(courseware.getClassTime()==null) {
			sbuilder.append("时长(秒)为必填项，");
		}
		if(StringUtils.isBlank(courseware.getSubject2())) {
			sbuilder.append("二级学科为必填项，");
		}
		if(StringUtils.isBlank(courseware.getSubject())) {
			sbuilder.append("三级学科为必填项，");
		}		
		if(StringUtils.isBlank(courseware.getPlayType())) {
			sbuilder.append("播放类型（PC）为必填项，");
		}
		if(StringUtils.isBlank(courseware.getMobileType())) {
			sbuilder.append("播放类型（手机）为必填项，");
		}
		if(StringUtils.isBlank(courseware.getSource())) {
			sbuilder.append("来源为必填项，");
		}
		if(StringUtils.isBlank(courseware.getProjectLevel())) {
			sbuilder.append("项目级别为必填项，");
		}		
		//TODO ： 以下判断不同情况下判断
		if(sbuilder.length()>0) {
			return sbuilder.toString();
		}
		return null;
	}
}
