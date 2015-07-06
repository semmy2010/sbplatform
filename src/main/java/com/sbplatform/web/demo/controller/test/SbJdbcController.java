package com.sbplatform.web.demo.controller.test;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.MyBeanUtils;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.demo.entity.test.SbJdbcEntity;
import com.sbplatform.web.demo.service.test.SbJdbcServiceI;
import com.sbplatform.web.system.service.SystemService;

/**   
 * @Title: Controller
 * @Description: 通过JDBC访问数据库
 * @author Quainty
 * @date 2013-05-20 13:18:38
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sbJdbcController")
public class SbJdbcController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SbJdbcController.class);

	@Autowired
	private SbJdbcServiceI sbJdbcService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 通过JDBC访问数据库列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sbJdbc")
	public ModelAndView sbJdbc(HttpServletRequest request) {
		return new ModelAndView("sb/demo/test/sbJdbcList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(SbJdbcEntity sbJdbc,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 方式1, 用底层自带的方式往对象中设值  -------------------
		/*
		this.sbJdbcService.getDatagrid1(sbJdbc, dataGrid);
		TagUtil.datagrid(response, dataGrid);
		// end of 方式1 ========================================= */ 
		
		// 方式2, 取值自己处理(代码量多一些，但执行效率应该会稍高一些)  -------------------------------
		/*
		this.sbJdbcService.getDatagrid2(sbJdbc, dataGrid);
		TagUtil.datagrid(response, dataGrid);
		// end of 方式2 ========================================= */ 
		
		// 方式3, 取值进一步自己处理(直接转换成easyUI的datagrid需要的东西，执行效率最高，最自由)  -------------------------------
		//*
		JSONObject jObject = this.sbJdbcService.getDatagrid3(sbJdbc, dataGrid);
		responseDatagrid(response, jObject);
		// end of 方式3 ========================================= */
	}

	/**
	 * 删除通过JDBC访问数据库
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SbJdbcEntity sbJdbc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		String sql = "delete from sb_demo where id='" + sbJdbc.getId() + "'";
		sbJdbcService.executeSql(sql);

		message = "删除成功";
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMessage(message);
		return j;
	}


	/**
	 * 添加通过JDBC访问数据库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SbJdbcEntity sbJdbc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sbJdbc.getId())) {
			message = "更新成功";
			SbJdbcEntity t = sbJdbcService.get(SbJdbcEntity.class, sbJdbc.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sbJdbc, t);
				sbJdbcService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			sbJdbcService.save(sbJdbc);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 通过JDBC访问数据库列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SbJdbcEntity sbJdbc, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sbJdbc.getId())) {
			sbJdbc = sbJdbcService.getEntity(SbJdbcEntity.class, sbJdbc.getId());
			req.setAttribute("sbJdbcPage", sbJdbc);
		}
		return new ModelAndView("sb/demo/test/sbJdbc");
	}
	
	
	// -----------------------------------------------------------------------------------
	// 以下各函数可以提成共用部件 (Add by Quainty)
	// -----------------------------------------------------------------------------------
	public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter pw=response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	@RequestMapping(params = "dictParameter")
	public String dictParameter(){
		return "sb/demo/base/jdbc/jdbc-list";
	}
	
	/**
	 * JDBC DEMO 显示列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "listAllDictParaByJdbc")
	public void listAllDictParaByJdbc(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		this.sbJdbcService.listAllByJdbc(dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
}
