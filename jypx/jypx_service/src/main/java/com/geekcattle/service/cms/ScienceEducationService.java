/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.service.cms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.mapper.cms.InfoMapper;
import com.geekcattle.model.cms.Info;
import com.geekcattle.util.CamelCaseUtil;
import com.github.pagehelper.PageHelper;

/**
 * author geekcattle date 2016/10/21 0021 下午 15:47
 */
@Service
public class ScienceEducationService {

	@Autowired
	private InfoMapper infoMapper;

	public List<Info> getPageList(Info info,String moduleId) {
		PageHelper.offsetPage(info.getOffset(), info.getLimit(),
				CamelCaseUtil.toUnderlineName(info.getSort()) + " " + info.getOrder());		
		return infoMapper.findInfoByModuleId(moduleId);
	}
	
	public List<Info> getPageListPage(Info info,String moduleId) {		
		PageHelper.startPage(info.getPage(), info.getLimit(), CamelCaseUtil.toUnderlineName(info.getSort()) + " " + info.getOrder());
		return infoMapper.findInfoByModuleId(moduleId);
	}
	
	public List<Info> getPageList(Info info,Map<String,String> moduleIds) {
		PageHelper.offsetPage(info.getOffset(), info.getLimit(),
				CamelCaseUtil.toUnderlineName(info.getSort()) + " " + info.getOrder());
		return infoMapper.findInfoByModuleIds(moduleIds);
	}
	
	public Info getById(String infoId) {
		return infoMapper.selectByPrimaryKey(infoId);
	}

	public void insert(Info info) {
		infoMapper.insertInfo(info);
	}

	public void save(Info info) {
		infoMapper.updateInfo(info);
	}

	public void deleteById(String id) {
		infoMapper.deleteInfo(id,ConstantEnum.INFO_DISABLE.toString());
	}
	public void updateOrder(Info info){
		infoMapper.updateOrder(info);
	}
}
