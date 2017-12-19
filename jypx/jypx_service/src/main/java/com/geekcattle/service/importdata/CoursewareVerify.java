package com.geekcattle.service.importdata;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.geekcattle.model.kjk.KjkPlayType;
import com.geekcattle.model.kjk.NcmeSubject;
import com.geekcattle.service.importdata.filter.CoursewareFilterChain;
import com.geekcattle.service.importdata.filter.CoursewareNotNullFilter;
import com.geekcattle.service.importdata.filter.CoursewarePlayTypeFilter;
import com.geekcattle.service.importdata.filter.CoursewareProcessor;
import com.geekcattle.service.importdata.filter.CoursewareSourceFilter;
import com.geekcattle.service.importdata.filter.CoursewareSubjectFilter;
import com.geekcattle.vo.kjk.CoursewareVo;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHanlderResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;

public class CoursewareVerify implements IExcelVerifyHandler<CoursewareVo> {
	private List<KjkPlayType> playTypeList;
	private Map<String,List<NcmeSubject>> subjectMap;
	private String userId;
	@Override
	public ExcelVerifyHanlderResult verifyHandler(CoursewareVo obj) {			
		boolean result = true;
		CoursewareFilterChain chain = new CoursewareFilterChain();
		chain.addFilter(new CoursewareNotNullFilter(userId))
		        .addFilter(new CoursewareSourceFilter())
		        .addFilter(new CoursewarePlayTypeFilter(playTypeList))
		        .addFilter(new CoursewareSubjectFilter(subjectMap));
		        
		CoursewareProcessor processor = new CoursewareProcessor(obj, chain);
		String errMsg = processor.process();
		if(StringUtils.isNotBlank(errMsg)){
			result = false;
		}
		return new ExcelVerifyHanlderResult(result,errMsg);
	}
	public CoursewareVerify(List<KjkPlayType> list,Map<String,List<NcmeSubject>> map,String userId){
		this.playTypeList = list;
		this.subjectMap = map;
		this.userId = userId;
	}
}
