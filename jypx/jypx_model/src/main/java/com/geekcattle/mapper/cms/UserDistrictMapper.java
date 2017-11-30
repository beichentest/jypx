package com.geekcattle.mapper.cms;

import java.util.List;

import com.geekcattle.model.cms.UserDistrict;
import com.geekcattle.util.CustomerMapper;

public interface UserDistrictMapper extends CustomerMapper<UserDistrict> {
	List<UserDistrict> selectRootDistrict();
	String selectDistrictByIds(String[] districtIds);
}