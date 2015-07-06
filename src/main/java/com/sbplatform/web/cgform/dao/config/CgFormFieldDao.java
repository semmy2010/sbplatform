package com.sbplatform.web.cgform.dao.config;

import java.util.List;
import java.util.Map;

import com.sbplatform.sbdao.annotation.Arguments;
import com.sbplatform.sbdao.annotation.MiniDao;

/**
 * 
 * @Title:CgFormFieldDao
 * @description:
 * @author 黄世民
 * @date Aug 24, 2013 11:33:33 AM
 * @version V1.0
 */
@MiniDao
public interface CgFormFieldDao {
	
	@Arguments("tableName")
	public List<Map<String, Object>> getCgFormFieldByTableName(String tableName);
	
	@Arguments("tableName")
	public List<Map<String, Object>> getCgFormHiddenFieldByTableName(String tableName);
	
}
