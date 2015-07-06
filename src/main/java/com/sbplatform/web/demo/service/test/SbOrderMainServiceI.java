package com.sbplatform.web.demo.service.test;

import java.util.List;

import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.web.demo.entity.test.SbOrderCustomEntity;
import com.sbplatform.web.demo.entity.test.SbOrderMainEntity;
import com.sbplatform.web.demo.entity.test.SbOrderProductEntity;


public interface SbOrderMainServiceI extends CommonService{
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(SbOrderMainEntity sbOrderMain,List<SbOrderProductEntity> sbOrderProducList,List<SbOrderCustomEntity> sbOrderCustomList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(SbOrderMainEntity sbOrderMain,List<SbOrderProductEntity> sbOrderProducList,List<SbOrderCustomEntity> sbOrderCustomList,boolean sbOrderCustomShow) ;
	public void delMain (SbOrderMainEntity sbOrderMain);
}
