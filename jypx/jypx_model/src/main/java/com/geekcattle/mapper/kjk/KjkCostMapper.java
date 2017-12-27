package com.geekcattle.mapper.kjk;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.geekcattle.model.kjk.KjkCost;
import com.geekcattle.util.CustomerMapper;

public interface KjkCostMapper extends CustomerMapper<KjkCost> {
	
	void insertKjkCost(KjkCost kjkCost);
	
	List<KjkCost> findKjkCost(KjkCost kjkCost);
	
	public void updateBatch(@Param("ids")List<String> ids);
	
}