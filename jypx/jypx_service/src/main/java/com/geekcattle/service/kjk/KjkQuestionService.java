package com.geekcattle.service.kjk;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.mapper.kjk.KjkQuestionMapper;
import com.geekcattle.model.kjk.KjkQuestion;
import com.geekcattle.util.CamelCaseUtil;
import com.geekcattle.vo.kjk.QuestionVo;
import com.github.pagehelper.PageHelper;

@Service
public class KjkQuestionService {
	private final static Logger logger = LoggerFactory.getLogger(KjkQuestionService.class);
	@Autowired
	private KjkQuestionMapper kjkQuestionMapper;

	public KjkQuestion getQuestionById(String qId) {
		return kjkQuestionMapper.selectByPrimaryKey(qId);
	}

	public void insetQuestion(KjkQuestion question) {
		kjkQuestionMapper.insertQuestion(question);
	}

	public void updateQuestion(KjkQuestion question) {
		kjkQuestionMapper.updateQuestion(question);
	}

	public List<KjkQuestion> getPageList(KjkQuestion question) {
		PageHelper.startPage(question.getPage(), question.getLimit(),
				CamelCaseUtil.toUnderlineName(question.getSort()) + " " + question.getOrder());
		question.setStatus(ConstantEnum.KJK_QUESTION_STATUS_ENABLE.toString());
		return kjkQuestionMapper.findQuestions(question);
	}

	public void deleteQuestion(String qIds) {
		String[] ids = qIds.split(",");
		List<KjkQuestion> questionList = new ArrayList();
		KjkQuestion question = null;
		for (String string : ids) {
			question = new KjkQuestion();
			question.setqId(string);
			question.setStatus(ConstantEnum.KJK_QUESTION_STATUS_DISABLE.toString());
			questionList.add(question);
		}
		kjkQuestionMapper.updateQuestionBatch(questionList);
	}
	
	public void insertQuestionBatch(List<QuestionVo> questions) {
		kjkQuestionMapper.insertQuestionBatch(questions);
	}
}
