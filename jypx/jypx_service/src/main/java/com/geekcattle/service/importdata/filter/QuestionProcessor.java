package com.geekcattle.service.importdata.filter;

import com.geekcattle.vo.kjk.QuestionVo;

public class QuestionProcessor {
	private QuestionVo vo;
	private QuestionFilterChain chain = new QuestionFilterChain();
	
	public QuestionProcessor(QuestionVo vo,QuestionFilterChain chain){
		this.vo = vo;
		this.chain = chain;
	}
	
	public String process(){
		return chain.doFilter(vo);
	}
}
