package com.geekcattle.service.importdata.filter;

import java.util.List;
import java.util.Map;

import com.geekcattle.model.kjk.KjkPlayType;
import com.geekcattle.model.kjk.NcmeSubject;
import com.geekcattle.vo.kjk.CoursewareVo;

public class CoursewarePlayTypeFilter implements ImportDataFilter<CoursewareVo>{
	private static List<KjkPlayType> playTypeList;
	@Override
	public String doFilter(CoursewareVo courseware) {
		String pcType = convertPlayType(playTypeList,courseware.getPlayType());
		String mType = convertPlayType(playTypeList,courseware.getMobileType());
		if(pcType==null) {
			return "播放类型（PC）输入不正确";
		}
		if(mType==null) {
			return "播放类型（手机）输入不正确";
		}
		courseware.setPlayType(pcType);
		courseware.setMobileType(mType);
		return null;
	}
	public CoursewarePlayTypeFilter(List<KjkPlayType> list){
		this.playTypeList = list;
	} 
	
	private String convertPlayType(List<KjkPlayType> list,String playTypeName) {
		for(KjkPlayType playType : list) {
			if(playType.getPlayTypeName().equals(playTypeName)) {
				return playType.getPlayType();
			}
		}
		return null;
	}
}
