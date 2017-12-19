package com.geekcattle.service.importdata.filter;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.conf.KjkEnum;
import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.vo.kjk.CoursewareVo;

public class CoursewareNotNullFilter implements ImportDataFilter<CoursewareVo>{
	private String userId;
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
		if(StringUtils.isBlank(courseware.getClassTimeStr())) {
			sbuilder.append("时长(时:分:秒)为必填项，");
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
		if(sbuilder.length()>0) {
			return sbuilder.toString();
		}
		courseware.setName(courseware.getName().trim());
		//设置默认参数
		courseware.setStatus(KjkEnum.KJK_COURSEWARE_STATUS_ENABLE.getValue().intValue());
		courseware.setPlayFlag(ConstantEnum.KJK_COURSEWARE_PLY_FLAG_NOT.toString());
		courseware.setClickCount(0);	//点击量		
		courseware.setUpdateDate(DateUtil.getSysTime());		
		courseware.setCreater(userId);
		courseware.setCreateDate(DateUtil.getSysTime());
		courseware.setAddDate(DateUtil.getSysTime());		
		return null;
	}
	
	public CoursewareNotNullFilter(String userId) {
		this.userId = userId;
	}
}
