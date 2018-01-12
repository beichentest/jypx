package com.geekcattle.service.importdata.filter;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.model.kjk.KjkPlayType;
import com.geekcattle.model.kjk.NcmeSubject;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.vo.kjk.CoursewareVo;

public class CoursewarePlayTypeFilter implements ImportDataFilter<CoursewareVo>{
	private static List<KjkPlayType> playTypeList;
	@Override
	public String doFilter(CoursewareVo courseware) {
		String pcType = convertPlayType(playTypeList,courseware.getPlayTypeText());
		String mType = convertPlayType(playTypeList,courseware.getMobileTypeText());
		if(pcType==null) {
			return "播放类型（PC）输入不正确";
		}
		if(mType==null) {
			return "播放类型（手机）输入不正确";
		}
		courseware.setPlayType(pcType);
		courseware.setMobileType(mType);
		if(pcType.equals(ConstantEnum.KJK_PLAY_TYPE_CC.toString())||mType.equals(ConstantEnum.KJK_PLAY_TYPE_CC.toString())) {
			if(StringUtils.isEmpty(courseware.getCode()))
				return "课件编号不能为空";
			if(courseware.getCode().trim().length()!=32)
				return "课件编号长度必须为32位";					
			courseware.setCode(courseware.getCode().trim());
			String par1code = getPayCode(courseware.getPar1());
			String par2code = getPayCode(courseware.getPar2());
			if(!courseware.getCode().equals(par1code)||!courseware.getCode().equals(par2code)) {
				return "课件参数填写有误";
			}
		}
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
	private static String getPayCode(String par) {
		String beginStr = "vid=";
		String endStr = "&";
		if(par==null)
			return "err";
		if(par.indexOf(beginStr)==-1||par.indexOf(endStr)==-1)
			return "err";
		return par.substring(par.indexOf(beginStr)+beginStr.length(), par.indexOf(endStr, par.indexOf(beginStr)));
	}	
}
