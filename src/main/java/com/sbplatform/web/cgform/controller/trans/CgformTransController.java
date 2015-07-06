package com.sbplatform.web.cgform.controller.trans;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sbplatform.codegenerate.database.SbReadTable;
import com.sbplatform.codegenerate.pojo.Columnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.vo.datatable.SortDirection;
import com.sbplatform.web.cgform.entity.config.CgFormFieldEntity;
import com.sbplatform.web.cgform.entity.config.CgFormHeadEntity;
import com.sbplatform.web.cgform.entity.consts.DataBaseConst;
import com.sbplatform.web.cgform.service.config.CgFormFieldServiceI;

/**
 * 自定义表单逆向生成
 * 
 * @author Alexander
 * @date 20130927
 */
@Controller
@RequestMapping("/cgformTransController")
public class CgformTransController {

	@Autowired
	private CgFormFieldServiceI cgFormFieldService;

	/**
	 * 自动生成表属性列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "trans")
	public ModelAndView trans(HttpServletRequest request) {
		return new ModelAndView("sb/cgform/trans/transList");
	}

	@RequestMapping(params = "datagrid")
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String tableName = request.getParameter("id");

		List<String> list = new ArrayList<String>();
		try {
			list = new SbReadTable().readAllTableNames();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String html = "";
		Collections.sort(list, new StringSort(dataGrid.getOrder()));
		List<String> tables = cgFormFieldService.findByQueryString("select tableName from CgFormHeadEntity");
		list.removeAll(tables);
		List<String> index = new ArrayList<String>();
		if (StringUtil.isNotEmpty(tableName)) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).contains(tableName))
					index.add(list.get(i));
			}
			html = getJson(index, index.size());
		} else
			html = getJson(list, list.size());
		try {
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			PrintWriter writer = response.getWriter();
			writer.println(html);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(params = "transEditor")
	@ResponseBody
	public AjaxJson transEditor(HttpServletRequest request, String id) throws Exception {
		AjaxJson j = new AjaxJson();
		String ids[] = id.split(",");
		String no = "";
		String yes = "";
		for (int i = 0; i < ids.length; i++) {
			if (StringUtil.isNotEmpty(ids[i])) {
				List<CgFormHeadEntity> cffList = cgFormFieldService.findByProperty(CgFormHeadEntity.class, "tableName", ids[i]);
				if (cffList.size() > 0) {
					if (no != "")
						no += ",";
					no += ids[i];
					continue;
				}
				List<Columnt> list = new SbReadTable().readOriginalTableColumn(ids[i]);
				CgFormHeadEntity cgFormHead = new CgFormHeadEntity();
				cgFormHead.setJformType(1);
				cgFormHead.setIsCheckbox("Y");
				cgFormHead.setIsDbSynch("Y");
				cgFormHead.setIsTree("N");
				cgFormHead.setQuerymode("group");
				cgFormHead.setTableName(ids[i].toLowerCase());
				cgFormHead.setIsPagination("Y");
				cgFormHead.setContent(ids[i]);
				cgFormHead.setJformVersion("1");
				List<CgFormFieldEntity> columnsList = new ArrayList<CgFormFieldEntity>();
				for (int k = 0; k < list.size(); k++) {
					Columnt columnt = list.get(k);
					String fieldName = columnt.getFieldDbName();
					CgFormFieldEntity cgFormField = new CgFormFieldEntity();
					cgFormField.setFieldName(columnt.getFieldDbName().toLowerCase());
					if (StringUtil.isNotEmpty(columnt.getFiledComment()))
						cgFormField.setContent(columnt.getFiledComment());
					else
						cgFormField.setContent(columnt.getFieldName());
					cgFormField.setIsKey("N");
					cgFormField.setIsShow("Y");
					cgFormField.setIsShowList("Y");
					cgFormField.setOrderNum(k + 2);
					cgFormField.setQueryMode("group");
					cgFormField.setLength(0);
					cgFormField.setFieldLength(120);
					cgFormField.setPointLength(0);
					cgFormField.setShowType("text");
					cgFormField.setIsNull(columnt.getNullable());
					if ("id".equalsIgnoreCase(fieldName)) {
						String[] pkTypeArr = { "java.lang.Integer", "java.lang.Long" };
						String idFiledType = columnt.getFieldType();
						if (Arrays.asList(pkTypeArr).contains(idFiledType)) {
							//如果主键是数字类型,则设置为自增长
							cgFormHead.setJformPkType("NATIVE");
						} else {
							//否则设置为UUID
							cgFormHead.setJformPkType("UUID");
						}
						cgFormField.setIsKey("Y");
						cgFormField.setIsShow("N");
						cgFormField.setIsShowList("N");
					}
					if ("java.lang.Integer".equalsIgnoreCase(columnt.getFieldType())) {
						cgFormField.setType(DataBaseConst.INT);
					} else if ("java.lang.Long".equalsIgnoreCase(columnt.getFieldType())) {
						cgFormField.setType(DataBaseConst.INT);
					} else if ("java.util.Date".equalsIgnoreCase(columnt.getFieldType())) {
						cgFormField.setType(DataBaseConst.DATE);
						cgFormField.setShowType("date");
					} else if ("java.lang.Double".equalsIgnoreCase(columnt.getFieldType()) || "java.lang.Float".equalsIgnoreCase(columnt.getFieldType())) {
						cgFormField.setType(DataBaseConst.DOUBLE);
					} else if ("java.math.BigDecimal".equalsIgnoreCase(columnt.getFieldType())) {
						cgFormField.setType(DataBaseConst.BIGDECIMAL);
					} else if (columnt.getFieldType().contains("blob")) {
						cgFormField.setType(DataBaseConst.BLOB);
						columnt.setCharmaxLength(null);
					} else {
						cgFormField.setType(DataBaseConst.STRING);
					}
					if (StringUtil.isNotEmpty(columnt.getCharmaxLength())) {
						if (Long.valueOf(columnt.getCharmaxLength()) >= 3000) {
							cgFormField.setType(DataBaseConst.TEXT);
							cgFormField.setShowType(DataBaseConst.TEXTAREA);
							try {//有可能长度超出int的长度
								cgFormField.setLength(Integer.valueOf(columnt.getCharmaxLength()));
							} catch (Exception e) {
							}
						} else {
							cgFormField.setLength(Integer.valueOf(columnt.getCharmaxLength()));
						}
					} else {
						if (StringUtil.isNotEmpty(columnt.getPrecision())) {
							cgFormField.setLength(Integer.valueOf(columnt.getPrecision()));
						}
						//update-begin--Author:zhangdaihao  Date:20140212 for：[001]oracle下number类型，数据库表导出表单，默认长度为0同步失败
						else {
							if (cgFormField.getType().equals(DataBaseConst.INT)) {
								cgFormField.setLength(10);
							}
						}
						//update-end--Author:zhangdaihao  Date:20140212 for：[001]oracle下number类型，数据库表导出表单，默认长度为0同步失败
						if (StringUtil.isNotEmpty(columnt.getScale()))
							cgFormField.setPointLength(Integer.valueOf(columnt.getScale()));

					}
					columnsList.add(cgFormField);
				}
				cgFormHead.setColumns(columnsList);
				cgFormFieldService.saveTable(cgFormHead, "");
				if (yes != "")
					yes += ",";
				yes += ids[i];
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("no", no);
		map.put("yes", yes);
		j.setData(map);
		return j;
	}

	public static String getJson(List<String> result, Integer size) {
		JSONObject main = new JSONObject();
		JSONArray rows = new JSONArray();
		main.put("total", size);
		for (String m : result) {
			JSONObject item = new JSONObject();
			item.put("id", m);
			rows.add(item);
		}
		main.put("rows", rows);
		return main.toString();
	}

	private class StringSort implements Comparator<String> {

		private SortDirection sortOrder;

		public StringSort(SortDirection sortDirection) {
			this.sortOrder = sortDirection;
		}

		public int compare(String prev, String next) {
			return sortOrder.equals(SortDirection.asc) ? prev.compareTo(next) : next.compareTo(prev);
		}

	}

}
