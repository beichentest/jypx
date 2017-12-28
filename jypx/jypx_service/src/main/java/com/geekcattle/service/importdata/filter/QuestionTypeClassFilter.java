package com.geekcattle.service.importdata.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.vo.kjk.QuestionVo;

import net.sf.jsqlparser.util.AddAliasesVisitor;

public class QuestionTypeClassFilter implements ImportDataFilter<QuestionVo>{
	private final static Map<String,String> QUESTION_TYPE = new HashMap<String,String>() {
		{
			put(ConstantEnum.KJK_DIC_TYPE_QUESTION_TYPE_PROCESS_DESC.toString(),ConstantEnum.KJK_DIC_TYPE_QUESTION_TYPE_PROCESS.toString());
			put(ConstantEnum.KJK_DIC_TYPE_QUESTION_TYPE_AFTER_DESC.toString(),ConstantEnum.KJK_DIC_TYPE_QUESTION_TYPE_AFTER.toString());
		}
	};
	private final static Map<String,String> QUESTION_CLASS = new HashMap<String,String>() {
		{
			put(ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS_MULTIPLE_DESC.toString(),ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS_MULTIPLE.toString());
			put(ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS_SINGLE_DESC.toString(),ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS_SINGLE.toString());
		}
	};
	
	@Override
	public String doFilter(QuestionVo question) {
		if(QUESTION_TYPE.get(question.getqType())==null) {
			return "试题类型输入不正确";
		}
		if(QUESTION_CLASS.get(question.getqClass())==null) {
			return "题型输入不正确";
		}
		question.setqType(QUESTION_TYPE.get(question.getqType()));
		question.setqClass(QUESTION_CLASS.get(question.getqClass()));
		return null;
	}	
}
