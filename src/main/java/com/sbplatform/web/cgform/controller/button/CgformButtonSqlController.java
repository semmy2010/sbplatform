package com.sbplatform.web.cgform.controller.button;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.common.Constant;
import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.MyBeanUtils;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.cgform.entity.button.CgformButtonEntity;
import com.sbplatform.web.cgform.entity.button.CgformButtonSqlEntity;
import com.sbplatform.web.cgform.service.button.CgformButtonServiceI;
import com.sbplatform.web.cgform.service.button.CgformButtonSqlServiceI;
import com.sbplatform.web.system.service.SystemService;

/**   
 * @Title: Controller
 * @Description: 按钮sql增强
 * @author 黄世民
 * @date 2013-08-07 23:09:23
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/cgformButtonSqlController")
public class CgformButtonSqlController extends BaseController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CgformButtonSqlController.class);

	@Autowired
	private CgformButtonSqlServiceI cgformButtonSqlService;
	@Autowired
	private CgformButtonServiceI cgformButtonService;
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
	 * 按钮sql增强列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cgformButtonSql")
	public ModelAndView cgformButtonSql(HttpServletRequest request) {
		String formId = request.getParameter("formId");
		String tableName = request.getParameter("tableName");
		request.setAttribute("formId", formId);
		request.setAttribute("tableName", tableName);
		return new ModelAndView("sb/cgform/button/cgformButtonSqlList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(CgformButtonSqlEntity cgformButtonSql,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CgformButtonSqlEntity.class, dataGrid);
		//查询条件组装器
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, cgformButtonSql, request.getParameterMap());
		this.cgformButtonSqlService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除按钮sql增强
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CgformButtonSqlEntity cgformButtonSql, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		cgformButtonSql = systemService.getEntity(CgformButtonSqlEntity.class, cgformButtonSql.getId());
		message = "删除成功";
		cgformButtonSqlService.delete(cgformButtonSql);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMessage(message);
		return j;
	}
	
	/**
	 * 查找数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "doCgformButtonSql")
	@ResponseBody  
	public AjaxJson doCgformButtonSql(CgformButtonSqlEntity cgformButtonSql, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		CgformButtonSqlEntity cgformSql = cgformButtonSqlService.getCgformButtonSqlByCodeFormId(cgformButtonSql.getButtonCode(), cgformButtonSql.getFormId());
		if(cgformSql!=null){
			j.setData(cgformSql);
			j.setStatus(Constant.CommonConstant.STATUS_SUCCESS);
		}else{
			j.setStatus(Constant.CommonConstant.STATUS_FAILURE);
		}
		return j;
	}


	/**
	 * 添加按钮sql增强
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CgformButtonSqlEntity cgformButtonSql, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		List<CgformButtonSqlEntity> list =  cgformButtonSqlService.checkCgformButtonSql(cgformButtonSql);
		if(list!=null&&list.size()>0){
			message = "按钮编码已经存在";
			j.setMessage(message);
			return j;
		}
		if (StringUtil.isNotEmpty(cgformButtonSql.getId())) {
			message = "更新成功";
			CgformButtonSqlEntity t = cgformButtonSqlService.get(CgformButtonSqlEntity.class, cgformButtonSql.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(cgformButtonSql, t);
				cgformButtonSqlService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			cgformButtonSqlService.save(cgformButtonSql);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMessage(message);
		return j;
	}

	/**
	 * 按钮sql增强列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CgformButtonSqlEntity cgformButtonSql, HttpServletRequest req) {
		//根据buttonCode和formId初始化数据
		cgformButtonSql.setButtonCode("add");
		if (StringUtil.isNotEmpty(cgformButtonSql.getButtonCode())&&StringUtil.isNotEmpty(cgformButtonSql.getFormId())) {
			CgformButtonSqlEntity cgformButtonSqlVo = cgformButtonSqlService.getCgformButtonSqlByCodeFormId(cgformButtonSql.getButtonCode(), cgformButtonSql.getFormId());
		    if(cgformButtonSqlVo!=null){
		    	cgformButtonSql = cgformButtonSqlVo;
		    }
		}
		List<CgformButtonEntity> list = cgformButtonService.getCgformButtonListByFormId(cgformButtonSql.getFormId());
		if(list==null){
			list = new ArrayList<CgformButtonEntity>();
		}
		req.setAttribute("buttonList", list);
		req.setAttribute("cgformButtonSqlPage", cgformButtonSql);
		return new ModelAndView("sb/cgform/button/cgformButtonSql");
	}
}
