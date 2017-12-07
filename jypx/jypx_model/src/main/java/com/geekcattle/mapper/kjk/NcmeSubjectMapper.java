package com.geekcattle.mapper.kjk;

import java.util.List;

import com.geekcattle.model.kjk.NcmeSubject;
import com.geekcattle.util.CustomerMapper;

public interface NcmeSubjectMapper extends CustomerMapper<NcmeSubject> {
	
	/**
	 * 获取二级学科
	 * @return
	 */
	public List<NcmeSubject> getNcmeSubject2();
	
	/**
	 * 根据二级学科名字获取三级学科
	 * @param subjectName2
	 * @return
	 */
	public List<NcmeSubject> getNcmeSubjectByName(String subjectName2);
}