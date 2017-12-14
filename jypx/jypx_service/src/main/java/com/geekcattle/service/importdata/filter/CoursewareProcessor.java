package com.geekcattle.service.importdata.filter;

import com.geekcattle.vo.kjk.CoursewareVo;

public class CoursewareProcessor {
	private CoursewareVo vo;
	private CoursewareFilterChain chain = new CoursewareFilterChain();
	
	public CoursewareProcessor(CoursewareVo vo,CoursewareFilterChain chain){
		this.vo = vo;
		this.chain = chain;
	}
	
	public String process(){
		return chain.doFilter(vo);
	}
}
