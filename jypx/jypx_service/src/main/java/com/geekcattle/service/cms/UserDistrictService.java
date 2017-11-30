/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.service.cms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekcattle.mapper.cms.UserDistrictMapper;
import com.geekcattle.model.cms.UserDistrict;
import com.geekcattle.util.CamelCaseUtil;
import com.github.pagehelper.PageHelper;

/**
 * author geekcattle date 2016/10/21 0021 下午 15:47
 */
@Service
public class UserDistrictService {

	@Autowired
	private UserDistrictMapper userDistrictMapper;

	public List<UserDistrict> getRootDistrictList() {		
		return userDistrictMapper.selectRootDistrict();
	}
   
	public  List<UserDistrict> getDistrictAll(){
		return userDistrictMapper.selectAll();
	}
	
	public String getDistrictByIds(String[] districtIds){
		return userDistrictMapper.selectDistrictByIds(districtIds);
	}
	/*public Info getById(String infoId) {
		return infoMapper.selectByPrimaryKey(infoId);
	}

	public void insert(Info info) {
		infoMapper.insertInfo(info);
	}

	public void save(Info info) {
		infoMapper.updateInfo(info);
	}

	public void deleteById(String id) {
		infoMapper.deleteByPrimaryKey(id);
	}*/
}
