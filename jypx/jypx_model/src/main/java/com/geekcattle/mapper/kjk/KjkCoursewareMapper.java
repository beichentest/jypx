package com.geekcattle.mapper.kjk;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.util.CustomerMapper;
import com.geekcattle.vo.kjk.CoursewareVo;

public interface KjkCoursewareMapper extends CustomerMapper<KjkCourseware> {
	List<KjkCourseware> findCourseware(KjkCourseware courseware);
	
	List<CoursewareVo> findCoursewareVo(KjkCourseware courseware);
	
	void insertCourseware(KjkCourseware courseware);
	
	void updateCourseware(KjkCourseware courseware);
	
	List<CoursewareVo> findCoursewareByNames(@Param("params") List<CoursewareVo> coursewareList);
	
	void insertCoursewareBatch(List<CoursewareVo> coursewareList);
	
	List<CoursewareVo> findCoursewareByIds(String[] ids);
}