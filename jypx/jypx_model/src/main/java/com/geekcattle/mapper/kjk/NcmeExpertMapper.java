package com.geekcattle.mapper.kjk;

import java.util.List;

import com.geekcattle.model.kjk.NcmeExpert;
import com.geekcattle.util.CustomerMapper;

public interface NcmeExpertMapper extends CustomerMapper<NcmeExpert> {
	//专家分页列表
	List<NcmeExpert> getNcmeExpertPageList(NcmeExpert ncmeExpert);
	//添加专家
	int insertNcmeExpert(NcmeExpert ncmeExpert);
}