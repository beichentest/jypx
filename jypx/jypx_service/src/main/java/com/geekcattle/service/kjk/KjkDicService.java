package com.geekcattle.service.kjk;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.mapper.kjk.KjkDicMapper;
import com.geekcattle.model.kjk.KjkDic;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@CacheConfig(cacheNames = "kjk_dic")
public class KjkDicService {
	private final static Logger logger = LoggerFactory.getLogger(KjkDicService.class);
	@Autowired
	private KjkDicMapper kjkDicMapper;
	@Cacheable(key="#dicType")
	public List<KjkDic> findDicByType(String dicType){
		Example example = new Example(KjkDic.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("dicType", dicType);
		criteria.andEqualTo("status", ConstantEnum.KJK_DIC_STATUS_ENABLE.toString());
		example.setOrderByClause("seq desc");		
		return kjkDicMapper.selectByExample(example);
	}
	@CacheEvict(value="kjk_dic", allEntries=true)
	public void flushDic() {
		logger.info("清空缓存字典信息");
	}
}
