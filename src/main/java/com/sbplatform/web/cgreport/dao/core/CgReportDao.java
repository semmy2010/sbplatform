package com.sbplatform.web.cgreport.dao.core;

import java.util.List;
import java.util.Map;

import com.sbplatform.sbdao.annotation.Arguments;
import com.sbplatform.sbdao.annotation.MiniDao;

/**
 * 
 * @author zhangdaihao
 *
 */
@MiniDao
public interface CgReportDao{

	@Arguments("configId")
	List<Map<String,Object>> queryCgReportItems(String configId);
	
	@Arguments("id")
	Map queryCgReportMainConfig(String id);
}
