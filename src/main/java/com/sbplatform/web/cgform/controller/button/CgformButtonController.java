package com.sbplatform.web.cgform.controller.button;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.MyBeanUtils;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.cgform.entity.button.CgformButtonEntity;
import com.sbplatform.web.cgform.service.button.CgformButtonServiceI;
import com.sbplatform.web.system.service.SystemService;

/**   
 * @Title: Controller
 * @Description: 表单自定义按钮
 * @author 黄世民
 * @date 2013-08-07 20:16:26
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/cgformButtonController")
public class CgformButtonController extends BaseController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CgformButtonController.class);

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
	 * 表单自定义按钮列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cgformButton")
	public ModelAndView cgformButton(HttpServletRequest request) {
		String formId = request.getParameter("formId");
		String tableName = request.getParameter("tableName");
		request.setAttribute("formId", formId);
		request.setAttribute("tableName", tableName);
		return new ModelAndView("sb/cgform/button/cgformButtonList");
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
	public void datagrid(CgformButtonEntity cgformButton,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CgformButtonEntity.class, dataGrid);
		//查询条件组装器
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, cgformButton, request.getParameterMap());
		this.cgformButtonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除表单自定义按钮
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CgformButtonEntity cgformButton, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		cgformButton = systemService.getEntity(CgformButtonEntity.class, cgformButton.getId());
		message = "删除成功";
		cgformButtonService.delete(cgformButton);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMessage(message);
		return j;
	}


	/**
	 * 添加表单自定义按钮
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CgformButtonEntity cgformButton, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if("add".equalsIgnoreCase(cgformButton.getButtonCode())
				||"update".equalsIgnoreCase(cgformButton.getButtonCode())
				||"delete".equalsIgnoreCase(cgformButton.getButtonCode())){
			message = "按钮编码不能是add/update/delete";
			j.setMessage(message);
			return j;
		}
		List<CgformButtonEntity> list =  cgformButtonService.checkCgformButton(cgformButton);
		if(list!=null&&list.size()>0){
			message = "按钮编码已经存在";
			j.setMessage(message);
			return j;
		}
		if (StringUtil.isNotEmpty(cgformButton.getId())) {
			message = "更新成功";
			CgformButtonEntity t = cgformButtonService.get(CgformButtonEntity.class, cgformButton.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(cgformButton, t);
				cgformButtonService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			cgformButtonService.save(cgformButton);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMessage(message);
		return j;
	}

	/**
	 * 表单自定义按钮列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CgformButtonEntity cgformButton, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(cgformButton.getId())) {
			cgformButton = cgformButtonService.getEntity(CgformButtonEntity.class, cgformButton.getId());
		}
		req.setAttribute("cgformButtonPage", cgformButton);
		return new ModelAndView("sb/cgform/button/cgformButton");
	}
}
