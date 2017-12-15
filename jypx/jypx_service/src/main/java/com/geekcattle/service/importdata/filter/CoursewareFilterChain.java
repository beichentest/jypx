package com.geekcattle.service.importdata.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.geekcattle.vo.kjk.CoursewareVo;

/**
 * 过滤链
 * @author Administrator
 *
 */
public class CoursewareFilterChain implements ImportDataFilter<CoursewareVo> {
	private List<ImportDataFilter> filters = new ArrayList<ImportDataFilter>();
	
	public CoursewareFilterChain addFilter(ImportDataFilter f){
		filters.add(f);
		return this;
	}
	
	@Override
	public String doFilter(CoursewareVo vo) {
		StringBuilder errMsg = new StringBuilder();
		for(ImportDataFilter filter : filters){
			String msg = filter.doFilter(vo);
			if(StringUtils.isNotBlank(msg)){
				errMsg.append(" {").append(msg).append("} ;");
			}
		}
		return errMsg.toString();
	}
}
