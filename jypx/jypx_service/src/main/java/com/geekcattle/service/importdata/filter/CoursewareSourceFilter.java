package com.geekcattle.service.importdata.filter;

import java.util.ArrayList;
import java.util.List;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.vo.kjk.CoursewareVo;

public class CoursewareSourceFilter implements ImportDataFilter<CoursewareVo>{
	private final static List<String> COURSEWARE_SOURCE = new ArrayList<String>() {
		{
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_CME.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_BASE.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_RCT.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_EXAM.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_COUNTRY_DOCTORS.toString());
		}
	};
	@Override
	public String doFilter(CoursewareVo courseware) {
		if(!COURSEWARE_SOURCE.contains(courseware.getSource())) {
			return "来源输入不在取值范围内";
		}
		return null;
	}

}
