package com.geekcattle.mapper.kjk;

import java.util.List;

import com.geekcattle.model.kjk.KjkQuestion;
import com.geekcattle.util.CustomerMapper;
import com.geekcattle.vo.kjk.QuestionVo;

public interface KjkQuestionMapper extends CustomerMapper<KjkQuestion> {
	void updateQuestion(KjkQuestion question);
	void insertQuestion(KjkQuestion question);
	List<KjkQuestion> findQuestions(KjkQuestion question);
	void updateQuestionBatch(List<KjkQuestion> questions);
	void insertQuestionBatch(List<QuestionVo> questions);
}