/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.service.kjk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekcattle.conf.KjkEnum;
import com.geekcattle.mapper.kjk.KjkCoursewareMapper;
import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.util.CamelCaseUtil;
import com.geekcattle.vo.kjk.CoursewareVo;
import com.github.pagehelper.PageHelper;

/**
 * author geekcattle date 2016/10/21 0021 下午 15:47
 */
@Service
public class KjkService {

	@Autowired
	private KjkCoursewareMapper kjkCoursewareMapper;
	
	public List<KjkCourseware> getPageList(KjkCourseware kjkCourseware) {
		PageHelper.startPage(kjkCourseware.getPage(), kjkCourseware.getLimit(),
				CamelCaseUtil.toUnderlineName(kjkCourseware.getSort()) + " " + kjkCourseware.getOrder());	
		 /*Example example = new Example(KjkCourseware.class);
		 Criteria criteria = example.createCriteria();	
		 criteria.andEqualTo("status", KjkEnum.KJK_COURSEWARE_STATUS_ENABLE.getValue());
		 if(kjkCourseware.getId()!=null) {
			 criteria.andEqualTo("id", kjkCourseware.getId());			 
		 }
		 if(StringUtils.isNotBlank(kjkCourseware.getKeyword())){
			 criteria.andLike("keyword", "%"+kjkCourseware.getKeyword()+"%");
		 }*/
		kjkCourseware.setStatus(KjkEnum.KJK_COURSEWARE_STATUS_ENABLE.getValue().intValue());
		return kjkCoursewareMapper.findCourseware(kjkCourseware);
	}
	
	public List<CoursewareVo> getExcelList(KjkCourseware kjkCourseware){
		return kjkCoursewareMapper.findCoursewareVo(kjkCourseware);
	} 
	
	public int updateCourseCware(KjkCourseware kjkCourseware){
		return kjkCoursewareMapper.updateByPrimaryKeySelective(kjkCourseware);
	}

	public KjkCourseware findKjkCoursewareById(Long id) {
		return kjkCoursewareMapper.selectByPrimaryKey(id);
	}
}
