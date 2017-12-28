package com.geekcattle.service.importdata.filter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.thymeleaf.util.ArrayUtils;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.conf.KjkEnum;
import com.geekcattle.model.kjk.KjkPlayType;
import com.geekcattle.util.JsonUtil;
import com.geekcattle.vo.kjk.Option;
import com.geekcattle.vo.kjk.QuestionVo;

public class QuestionOptionFilter implements ImportDataFilter<QuestionVo>{	
	@Override
	public String doFilter(QuestionVo question) {		
		List<Option> options = new ArrayList<Option>();
		Option option = null;
		char alisa = 'A';
		for (int i = 0; i < KjkEnum.KJK_QUESTION_OPTION_NUMBER.getValue(); i++) {
			Method method = ReflectionUtils.findMethod(QuestionVo.class, "getOption"+alisa);
			if(method!=null) {
				option = new Option();
				option.setAlisa(String.valueOf(alisa));
				Object txObj = ReflectionUtils.invokeMethod(method, question);
				option.setText(txObj==null?"":txObj.toString());
				if(ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS_SINGLE.toString().equals(question.getqClass())) {
					option.setType("radio");
				}else if(ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS_MULTIPLE.toString().equals(question.getqClass())) {
					option.setType("checkbox");
				}
				if(StringUtils.contains(question.getqKey(), String.valueOf(alisa))) {
					option.setChecked(true);
				}
				options.add(option);
				alisa = (char) (alisa + '\001');
			}
		}							
		question.setOption(JsonUtil.toJson(options));
		return null;
	}	
}
