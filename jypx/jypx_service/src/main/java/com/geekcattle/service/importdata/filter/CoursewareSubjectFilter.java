package com.geekcattle.service.importdata.filter;

import java.util.List;
import java.util.Map;

import com.geekcattle.model.kjk.NcmeSubject;
import com.geekcattle.vo.kjk.CoursewareVo;

public class CoursewareSubjectFilter implements ImportDataFilter<CoursewareVo>{
	private static Map<String,List<NcmeSubject>> subjectMap;
	@Override
	public String doFilter(CoursewareVo courseware) {
		List<NcmeSubject> subjectList = subjectMap.get(courseware.getSubject2());
		if(subjectList==null) {
			return "二级学科输入不正确";
		}else {
			if(!existSubject(subjectList,courseware.getSubject())) {
				return "三级学科输入不正确";
			}
		}
		return null;
	}
	public CoursewareSubjectFilter(Map<String,List<NcmeSubject>> map){
		this.subjectMap = map;
	} 
	
	private boolean existSubject(List<NcmeSubject> subjectList,String subjectName) {
		for(NcmeSubject subject : subjectList) {
			if(subject.getSubjectName().equals(subjectName)) {
				return true;
			}
		}
		return false;
	}
}
