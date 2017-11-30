package com.geekcattle.mapper.cms;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.geekcattle.model.cms.Info;
import com.geekcattle.util.CustomerMapper;

public interface InfoMapper extends CustomerMapper<Info> {
	List<Info> findInfoByModuleId(String moduleId);
	List<Info> findInfoByModuleIds(@Param("params") Map<String,String> moduleIds);
	void updateInfo(Info info);
	void insertInfo(Info info);
	void updateOrder(Info info);
	void deleteInfo(@Param("infoId") String infoId,@Param("delFlag") String delFlag);
}