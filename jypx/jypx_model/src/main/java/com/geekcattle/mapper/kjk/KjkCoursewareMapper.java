package com.geekcattle.mapper.kjk;

import java.util.List;

import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.util.CustomerMapper;
import com.geekcattle.vo.kjk.CoursewareVo;

public interface KjkCoursewareMapper extends CustomerMapper<KjkCourseware> {
	List<KjkCourseware> findCourseware(KjkCourseware courseware);
	
	List<CoursewareVo> findCoursewareVo(KjkCourseware courseware);
	
	void insertCourseware(KjkCourseware courseware);
	
	void updateCourseware(KjkCourseware courseware);
}