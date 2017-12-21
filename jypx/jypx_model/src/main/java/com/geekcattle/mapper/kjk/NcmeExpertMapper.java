package com.geekcattle.mapper.kjk;

import java.util.List;

import com.geekcattle.model.kjk.NcmeExpert;
import com.geekcattle.util.CustomerMapper;

public interface NcmeExpertMapper extends CustomerMapper<NcmeExpert> {

	List<NcmeExpert> getNcmeExpertPageList(NcmeExpert ncmeExpert);
}