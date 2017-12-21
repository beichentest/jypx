package com.geekcattle.service.kjk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekcattle.mapper.kjk.NcmeExpertMapper;
import com.geekcattle.model.kjk.NcmeExpert;
import com.geekcattle.util.CamelCaseUtil;
import com.github.pagehelper.PageHelper;

@Service
public class NcmeExpertService {
	@Autowired
	NcmeExpertMapper ncmeExpertMapper;

	public List<NcmeExpert> getNcmeExpertPageList(NcmeExpert ncmeExpert) {
		PageHelper.startPage(ncmeExpert.getPage(), ncmeExpert.getLimit(),
				CamelCaseUtil.toUnderlineName(ncmeExpert.getSort()) + " " + ncmeExpert.getOrder());
		return ncmeExpertMapper.getNcmeExpertPageList(ncmeExpert);
	}

	public int deleteNcmeExpert(NcmeExpert ncmeExpert) {
		return ncmeExpertMapper.delete(ncmeExpert);

	}

	public List<NcmeExpert> getListByName(String idCard) {
		NcmeExpert ncmeExpert = new NcmeExpert();
		ncmeExpert.setIdCard(idCard);
		return ncmeExpertMapper.select(ncmeExpert);
	}

	public int update(NcmeExpert ncmeExpert) {
		return ncmeExpertMapper.updateByPrimaryKeySelective(ncmeExpert);
	}

	public int insert(NcmeExpert ncmeExpert) {
		return ncmeExpertMapper.insert(ncmeExpert);
	}

	public NcmeExpert getById(String expId) {
		return ncmeExpertMapper.selectByPrimaryKey(expId);
	}

}
