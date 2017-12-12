package com.geekcattle.service.kjk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekcattle.mapper.kjk.NcmeSubjectMapper;
import com.geekcattle.model.kjk.NcmeSubject;

@Service
public class NcmeSubjectService {

	@Autowired
	private NcmeSubjectMapper ncmeSubjectMapper;
	
	/**
	 * 二级学科
	 * @return
	 */
	public List<NcmeSubject> getNcmeSubject2(){
		return ncmeSubjectMapper.getNcmeSubject2();
	}
	
	/**
	 * 三级学科
	 * @param subjectName2
	 * @return
	 */
	public List<NcmeSubject> getNcmeSubjectByName(String subjectName2){
		return ncmeSubjectMapper.getNcmeSubjectByName(subjectName2);
	}
}
