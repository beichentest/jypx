package com.geekcattle.service.kjk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekcattle.mapper.kjk.KjkPlayTypeMapper;
import com.geekcattle.model.kjk.KjkPlayType;
import com.github.pagehelper.PageHelper;

@Service
public class KjkPlayTypeService {

	@Autowired
	private KjkPlayTypeMapper kjkPlayTypeMapper;
	
	public List<KjkPlayType> findAll(){
		PageHelper.orderBy("play_type");
		return kjkPlayTypeMapper.selectAll();
	}
	
}
