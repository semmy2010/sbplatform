package com.sbplatform.web.cgform.service.impl.autolist;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbplatform.core.common.exception.BusinessException;
import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.web.cgform.common.CommUtils;
import com.sbplatform.web.cgform.entity.config.CgFormFieldEntity;
import com.sbplatform.web.cgform.entity.config.CgFormHeadEntity;
import com.sbplatform.web.cgform.service.autolist.CgTableServiceI;
import com.sbplatform.web.cgform.service.build.DataBaseService;
import com.sbplatform.web.cgform.service.config.CgFormFieldServiceI;
import com.sbplatform.web.cgform.service.config.DbTableHandleI;
import com.sbplatform.web.cgform.service.impl.config.util.DbTableUtil;
import com.sbplatform.web.cgform.util.QueryParamUtil;
/**
 * 
 * @Title:CgTableServiceImpl
 * @description:动态表数据服务实现
 * @author 赵俊夫
 * @date Jul 5, 2013 9:34:51 PM
 * @version V1.0
 */
@Service("cgTableService")
@Transactional
public class CgTableServiceImpl extends CommonServiceImpl implements CgTableServiceI {
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DataBaseService dataBaseService;
	
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;

	@SuppressWarnings("unchecked")
	
	public List<Map<String, Object>> querySingle(String table, String field, Map params,
			int page, int rows) {
		StringBuilder sqlB = new StringBuilder();
		dealQuerySql(table,field,params,sqlB);
		List<Map<String, Object>> result = commonService.findForJdbcParam(sqlB
				.toString(), page, rows);
		return result;
	}
	
	public List<Map<String, Object>> querySingle(String table, String field, Map params,
			String sort, String order, int page, int rows) {
		StringBuilder sqlB = new StringBuilder();
		dealQuerySql(table,field,params,sqlB);
		if(!StringUtil.isEmpty(sort)&& !StringUtil.isEmpty(order)){
			sqlB.append(" ORDER BY "+sort+" "+ order);
		}
		List<Map<String, Object>> result = commonService.findForJdbcParam(sqlB
				.toString(), page, rows);
		return result;
	}

	@SuppressWarnings("unchecked")
	
	public boolean delete(String table, Object id) {
		try{
			CgFormHeadEntity head = cgFormFieldService.getCgFormHeadByTableName(table);
			Map<String,Object> data  = dataBaseService.findOneForJdbc(table, id.toString());
			if(data!=null){
				//打印测试
			    Iterator it=data.entrySet().iterator();
			    while(it.hasNext()){
			    	Map.Entry entry=(Map.Entry)it.next();
			        Object ok=entry.getKey();
			        Object ov=entry.getValue()==null?"":entry.getValue();
			        com.sbplatform.core.util.LogUtil.info("name:"+ok.toString()+";value:"+ov.toString());
			    }
				data = CommUtils.mapConvert(data);
				dataBaseService.executeSqlExtend(head.getId(), "delete", data);
			}
			//step.1 删除表
			StringBuilder deleteSql = new StringBuilder();
			deleteSql.append("DELETE FROM "+table+" WHERE id = ?");
			if(!QueryParamUtil.sql_inj(id.toString())){
				commonService.executeSql(deleteSql.toString(), id);
			}
			//step.2 判断是否有明细表,进行连带删除
			String[] subTables = head.getSubTableStr()==null?new String[0]:head.getSubTableStr().split(",");
			for(String subTable:subTables){
				Map<String, CgFormFieldEntity>  fields = cgFormFieldService.getAllCgFormFieldByTableName(subTable);
				String subFkField = null;
				Iterator it = fields.keySet().iterator();
				for(;it.hasNext();){
					String fieldName  = (String) it.next();
					CgFormFieldEntity fieldc = fields.get(fieldName);
					if(StringUtil.isNotEmpty(fieldc.getMainTable())){
						if(table.equalsIgnoreCase(fieldc.getMainTable())){
							subFkField = fieldName;
						}
					}
				}
				if(StringUtil.isNotEmpty(subFkField)){
					String dsql = "delete from "+subTable+" "+"where "+subFkField+" = ? ";
					this.executeSql(dsql,id);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	private void dealQuerySql(String table, String field, Map params,StringBuilder sqlB){
		sqlB.append(" SELECT ");
		for (String f : field.split(",")) {
			sqlB.append(f);
			sqlB.append(",");
		}
		sqlB.deleteCharAt(sqlB.length() - 1);
		sqlB.append(" FROM " + table);
		if (params.size() >= 1) {
			sqlB.append(" WHERE 1=1 ");
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value = String.valueOf(params.get(key));
				if (!StringUtil.isEmpty(value) && !"null".equals(value)) {
						sqlB.append(" AND ");
						sqlB.append(" " + key +  value );
				}
			}
		}
	}


	@SuppressWarnings("unchecked")
	
	public Long getQuerySingleSize(String table, String field, Map params) {
		StringBuilder sqlB = new StringBuilder();
		dealQuerySql(table,"count(*) as query_size,",params,sqlB);
		List<Map<String, Object>> result = commonService.findForJdbc(sqlB.toString());
		if(result.size()>=1){
			return Long.parseLong(String.valueOf(result.get(0).get("query_size")));
		}else{
			return 0L;
		}
	}
	
	public boolean deleteBatch(String table, String[] ids) {
		try{
			for(String id:ids){
				delete(table, id);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return true;
	}


	
}
