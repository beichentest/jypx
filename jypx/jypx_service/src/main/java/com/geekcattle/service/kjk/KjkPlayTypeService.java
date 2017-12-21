package com.geekcattle.service.kjk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.geekcattle.mapper.kjk.KjkPlayTypeMapper;
import com.geekcattle.model.kjk.KjkPlayType;
import com.github.pagehelper.PageHelper;

@Service
@CacheConfig(cacheNames = "play_type")
public class KjkPlayTypeService {

	@Autowired
	private KjkPlayTypeMapper kjkPlayTypeMapper;
	@Cacheable(key="methodName")
	public List<KjkPlayType> findAll(){
		PageHelper.orderBy("play_type");
		return kjkPlayTypeMapper.selectAll();
	}
	
}
