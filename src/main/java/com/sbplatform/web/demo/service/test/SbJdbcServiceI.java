package com.sbplatform.web.demo.service.test;

import net.sf.json.JSONObject;

import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.web.demo.entity.test.SbJdbcEntity;

public interface SbJdbcServiceI extends CommonService{
	public void getDatagrid1(SbJdbcEntity pageObj, DataGrid dataGrid);
	public void getDatagrid2(SbJdbcEntity pageObj, DataGrid dataGrid);
	public JSONObject getDatagrid3(SbJdbcEntity pageObj, DataGrid dataGrid);
	public void listAllByJdbc(DataGrid dataGrid);
}
