package com.geekcattle.service.kjk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geekcattle.mapper.kjk.NcmeExpertMapper;
import com.geekcattle.model.kjk.NcmeExpert;
import com.geekcattle.util.CamelCaseUtil;
import com.github.pagehelper.PageHelper;

@Service
public class NcmeExpertService {
	@Autowired
	NcmeExpertMapper ncmeExpertMapper;

	/**
	 * 专家分页列表查询
	 * @param ncmeExpert
	 * @return
	 */
	public List<NcmeExpert> getNcmeExpertPageList(NcmeExpert ncmeExpert) {
		PageHelper.startPage(ncmeExpert.getPage(), ncmeExpert.getLimit(),
				CamelCaseUtil.toUnderlineName(ncmeExpert.getSort()) + " " + ncmeExpert.getOrder());
		return ncmeExpertMapper.getNcmeExpertPageList(ncmeExpert);
	}

	/**
	 * 专家删除
	 * @param ncmeExpert
	 * @return
	 */
	public int deleteNcmeExpert(NcmeExpert ncmeExpert) {
		return ncmeExpertMapper.delete(ncmeExpert);

	}

	/**
	 * 查询身份证是否已存在接口
	 * @param idCard
	 * @return
	 */
	public List<NcmeExpert> getListByName(String idCard) {
		NcmeExpert ncmeExpert = new NcmeExpert();
		ncmeExpert.setIdCard(idCard);
		return ncmeExpertMapper.select(ncmeExpert);
	}

	/**
	 * 专家编辑
	 * @param ncmeExpert
	 * @return
	 */
	public int update(NcmeExpert ncmeExpert) {
		return ncmeExpertMapper.updateByPrimaryKeySelective(ncmeExpert);
	}

	/**
	 * 专家添加
	 * @param ncmeExpert
	 * @return
	 */
	public int insert(NcmeExpert ncmeExpert) {
		 return ncmeExpertMapper.insertNcmeExpert(ncmeExpert);
	}

	/**
	 * 根据id获取实体
	 * @param expId
	 * @return
	 */
	public NcmeExpert getById(String expId) {
		return ncmeExpertMapper.selectByPrimaryKey(expId);
	}

}
