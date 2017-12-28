package com.geekcattle.service.importdata;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.geekcattle.model.kjk.KjkPlayType;
import com.geekcattle.model.kjk.NcmeSubject;
import com.geekcattle.service.importdata.filter.CoursewareProcessor;
import com.geekcattle.service.importdata.filter.QuestionFilterChain;
import com.geekcattle.service.importdata.filter.QuestionNotNullFilter;
import com.geekcattle.service.importdata.filter.QuestionOptionFilter;
import com.geekcattle.service.importdata.filter.QuestionProcessor;
import com.geekcattle.service.importdata.filter.QuestionTypeClassFilter;
import com.geekcattle.vo.kjk.QuestionVo;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHanlderResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;

public class QuestionVerify implements IExcelVerifyHandler<QuestionVo> {

	private String userId;
	@Override
	public ExcelVerifyHanlderResult verifyHandler(QuestionVo obj) {			
		boolean result = true;
		QuestionFilterChain chain = new QuestionFilterChain();
		chain.addFilter(new QuestionNotNullFilter(userId))
		        .addFilter(new QuestionTypeClassFilter())
		        .addFilter(new QuestionOptionFilter());
		        
		QuestionProcessor processor = new QuestionProcessor(obj, chain);
		String errMsg = processor.process();
		if(StringUtils.isNotBlank(errMsg)){
			result = false;
		}
		return new ExcelVerifyHanlderResult(result,errMsg);
	}
	public QuestionVerify(String userId){		
		this.userId = userId;
	}
}
