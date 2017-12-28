package com.geekcattle.service.importdata.filter;

import org.apache.commons.lang3.StringUtils;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.conf.KjkEnum;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UuidUtil;
import com.geekcattle.vo.kjk.QuestionVo;

public class QuestionNotNullFilter implements ImportDataFilter<QuestionVo>{
	private String userId;
	@Override
	public String doFilter(QuestionVo question) {
		StringBuilder sbuilder = new StringBuilder();
		if(question.getCwId()==null||question.getCwId()<1) {
			sbuilder.append("课件ID为必填项，");
		}
		if(StringUtils.isBlank(question.getqType())) {
			sbuilder.append("试题类型为必填项，");
		}
		if(StringUtils.isBlank(question.getqClass())) {
			sbuilder.append("题型为必填项，");
		}
		if(StringUtils.isBlank(question.getContent())) {
			sbuilder.append("题干为必填项，");
		}		
		if(StringUtils.isBlank(question.getqKey())) {
			sbuilder.append("答案为必填项，");
		}
		if (ConstantEnum.KJK_DIC_TYPE_QUESTION_TYPE_PROCESS.toString().equals(question.getqType())
				&& StringUtils.isBlank(question.getExecuteTime())) {
			sbuilder.append("过程题中出现时间不能为空");
		}
		question.setqId(UuidUtil.getUUID());
		question.setOperator(userId);
		question.setqType(question.getqType().trim());
		question.setqClass(question.getqClass().trim());
		return null;
	}
	
	public QuestionNotNullFilter(String userId) {
		this.userId = userId;
	}
}
