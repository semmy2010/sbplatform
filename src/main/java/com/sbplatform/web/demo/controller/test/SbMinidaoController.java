package com.sbplatform.web.demo.controller.test;
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
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.MyBeanUtils;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.demo.entity.test.SbMinidaoEntity;
import com.sbplatform.web.demo.service.test.SbMinidaoServiceI;
import com.sbplatform.web.system.pojo.base.Department;
import com.sbplatform.web.system.service.SystemService;

/**   
 * @Title: Controller
 * @Description: Minidao例子
 * @author fancq
 * @date 2013-12-23 21:18:59
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sbMinidaoController")
public class SbMinidaoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SbMinidaoController.class);

	@Autowired
	private SbMinidaoServiceI sbMinidaoService;
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
	 * Minidao例子列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sbMinidao")
	public ModelAndView sbMinidao(HttpServletRequest request) {
		return new ModelAndView("sb/demo/test/sbMinidaoList");
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
	public void datagrid(SbMinidaoEntity sbMinidao,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		List<SbMinidaoEntity> list = sbMinidaoService.listAll(sbMinidao, dataGrid.getPage(), dataGrid.getRows());
		Integer count = sbMinidaoService.getCount();
		dataGrid.setTotal(count);
		dataGrid.setResults(list);
		String total_salary = String.valueOf(sbMinidaoService.getSumSalary());
		/*
		 * 说明：格式为 字段名:值(可选，不写该值时为分页数据的合计) 多个合计 以 , 分割
		 */
		dataGrid.setFooter("salary:"+(total_salary.equalsIgnoreCase("null")?"0.0":total_salary)+",age,email:合计");
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除Minidao例子
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SbMinidaoEntity sbMinidao, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		sbMinidao = sbMinidaoService.getEntity(SbMinidaoEntity.class, sbMinidao.getId());
		message = "Minidao例子删除成功";
		sbMinidaoService.delete(sbMinidao);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMessage(message);
		return j;
	}


	/**
	 * 添加Minidao例子
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SbMinidaoEntity sbMinidao, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sbMinidao.getId())) {
			message = "Minidao例子更新成功";
			SbMinidaoEntity t = sbMinidaoService.getEntity(SbMinidaoEntity.class, sbMinidao.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sbMinidao, t);
				sbMinidaoService.update(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "Minidao例子更新失败";
			}
		} else {
			message = "Minidao例子添加成功";
			sbMinidao.setStatus("0");
			sbMinidaoService.insert(sbMinidao);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMessage(message);
		return j;
	}

	/**
	 * Minidao例子列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SbMinidaoEntity sbMinidao, HttpServletRequest req) {
		//获取部门信息
		List<Department> departList = systemService.getList(Department.class);
		req.setAttribute("departList", departList);
		
		if (StringUtil.isNotEmpty(sbMinidao.getId())) {
			sbMinidao = sbMinidaoService.getEntity(SbMinidaoEntity.class, sbMinidao.getId());
			req.setAttribute("sbMinidaoPage", sbMinidao);
		}
		return new ModelAndView("sb/demo/test/sbMinidao");
	}
}
