/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.service.kjk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.conf.KjkEnum;
import com.geekcattle.mapper.kjk.KjkCoursewareMapper;
import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.util.CamelCaseUtil;
import com.geekcattle.vo.kjk.CoursewareVo;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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
	
	/**
	 * 根据属性修改课件库
	 * @param kjkCourseware
	 * @return
	 */
	public int updateCourseCware(KjkCourseware kjkCourseware){
		return kjkCoursewareMapper.updateByPrimaryKeySelective(kjkCourseware);
	}
	
	/**
	 * 根据主键查询课件库对象
	 * @param id
	 * @return
	 */
	public KjkCourseware getById(Long id){
		return kjkCoursewareMapper.selectByPrimaryKey(id);
	}
	
	public void insert(KjkCourseware kjkCourseware){
		kjkCoursewareMapper.insertCourseware(kjkCourseware);
	}
	
	public void save(KjkCourseware kjkCourseware){
		kjkCoursewareMapper.updateCourseware(kjkCourseware);;
	}
	
	/**
	 * 根据属性返回list对象
	 * @param name
	 * @return
	 */
	public List<KjkCourseware> getListByName(String name){
		KjkCourseware kjkCourseware = new KjkCourseware();
		kjkCourseware.setName(name);
		return kjkCoursewareMapper.select(kjkCourseware);
	}
	
	public List<CoursewareVo> getListByNames(List<CoursewareVo> list){
		return kjkCoursewareMapper.findCoursewareByNames(list);
	} 
	
	public void insertBatch(List<CoursewareVo> list){
		kjkCoursewareMapper.insertCoursewareBatch(list);	
	}
	
	public Integer getCountNotPlay() {
		Example example = new Example(KjkCourseware.class);
		Criteria criteria = example.createCriteria();	
		criteria.andEqualTo("status", KjkEnum.KJK_COURSEWARE_STATUS_ENABLE.getValue());
		criteria.andEqualTo("playFlag", ConstantEnum.KJK_COURSEWARE_PLY_FLAG_NOT.toString());
		return kjkCoursewareMapper.selectCountByExample(example);
	}
}
