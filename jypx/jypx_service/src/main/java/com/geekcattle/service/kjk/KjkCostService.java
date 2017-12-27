package com.geekcattle.service.kjk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.mapper.kjk.KjkCostMapper;
import com.geekcattle.model.kjk.KjkCost;
import com.geekcattle.util.CamelCaseUtil;
import com.github.pagehelper.PageHelper;

@Service
public class KjkCostService {

	@Autowired
	private KjkCostMapper kjkCostMapper;
	
	public List<KjkCost> getPageList(KjkCost kjkCost) {
		List<KjkCost> list = null;
		try {
			PageHelper.startPage(kjkCost.getPage(), kjkCost.getLimit(),
					CamelCaseUtil.toUnderlineName(kjkCost.getSort()) + " " + kjkCost.getOrder());	
			kjkCost.setStatus(ConstantEnum.KJK_COST_STATUS_ENABLE.toString());
			list = kjkCostMapper.findKjkCost(kjkCost);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void insertKjkCost(KjkCost kjkCost){
		kjkCostMapper.insertKjkCost(kjkCost);
	}
	
	public int updateKjkCost(KjkCost kjkCost){
		return kjkCostMapper.updateByPrimaryKeySelective(kjkCost);
	}
	
	public void batchAudit(List<String> ids){
		kjkCostMapper.updateBatch(ids);
	}
}
