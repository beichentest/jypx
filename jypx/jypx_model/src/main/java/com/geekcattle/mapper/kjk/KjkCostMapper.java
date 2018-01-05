package com.geekcattle.mapper.kjk;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.geekcattle.model.kjk.KjkCost;
import com.geekcattle.util.CustomerMapper;
import com.geekcattle.vo.kjk.KjkCostVo;

public interface KjkCostMapper extends CustomerMapper<KjkCost> {
	
	void insertKjkCost(KjkCost kjkCost);
	
	List<KjkCost> findKjkCost(KjkCost kjkCost);
	
	public void updateBatch(@Param("ids")List<String> ids);

	//导出查询
	List<KjkCostVo> findKjkCostVo(KjkCost kjkCost);
	
	List<KjkCostVo> findKjkCostVoByIds(String[] ids);	
}