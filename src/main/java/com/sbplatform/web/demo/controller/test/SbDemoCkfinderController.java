package com.sbplatform.web.demo.controller.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sbplatform.web.demo.entity.test.SbDemoCkfinderEntity;
import com.sbplatform.web.demo.service.test.SbDemoCkfinderServiceI;
import com.sbplatform.web.system.service.SystemService;

/**
 * @Title: Controller
 * @Description: ckeditor+ckfinder例子
 * @author Alexander
 * @date 2013-09-19 18:29:20
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/sbDemoCkfinderController")
public class SbDemoCkfinderController extends BaseController {

	@Autowired
	private SbDemoCkfinderServiceI sbDemoCkfinderService;
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
	 * ckeditor+ckfinder例子列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sbDemoCkfinder")
	public ModelAndView sbDemoCkfinder(HttpServletRequest request) {
		return new ModelAndView("sb/demo/test/sbDemoCkfinderList");
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
	public void datagrid(SbDemoCkfinderEntity sbDemoCkfinder,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SbDemoCkfinderEntity.class,
				dataGrid);
		// 查询条件组装器
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				sbDemoCkfinder, request.getParameterMap());
		this.sbDemoCkfinderService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除ckeditor+ckfinder例子
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SbDemoCkfinderEntity sbDemoCkfinder,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		sbDemoCkfinder = systemService.getEntity(
				SbDemoCkfinderEntity.class, sbDemoCkfinder.getId());
		message = "ckeditor+ckfinder例子删除成功";
		sbDemoCkfinderService.delete(sbDemoCkfinder);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);

		j.setMessage(message);
		return j;
	}

	/**
	 * 添加ckeditor+ckfinder例子
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SbDemoCkfinderEntity sbDemoCkfinder,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sbDemoCkfinder.getId())) {
			message = "ckeditor+ckfinder例子更新成功";
			SbDemoCkfinderEntity t = sbDemoCkfinderService.get(
					SbDemoCkfinderEntity.class, sbDemoCkfinder.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sbDemoCkfinder, t);
				sbDemoCkfinderService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "ckeditor+ckfinder例子更新失败";
			}
		} else {
			message = "ckeditor+ckfinder例子添加成功";
			sbDemoCkfinderService.save(sbDemoCkfinder);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}
		j.setMessage(message);
		return j;
	}

	/**
	 * ckeditor+ckfinder例子列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SbDemoCkfinderEntity sbDemoCkfinder,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sbDemoCkfinder.getId())) {
			sbDemoCkfinder = sbDemoCkfinderService.getEntity(
					SbDemoCkfinderEntity.class, sbDemoCkfinder.getId());
			req.setAttribute("sbDemoCkfinderPage", sbDemoCkfinder);
		}
		return new ModelAndView("sb/demo/test/sbDemoCkfinder");
	}
	
	/**
	 * ckeditor+ckfinder例子预览
	 * 
	 * @return
	 */
	@RequestMapping(params = "preview")
	public ModelAndView preview(SbDemoCkfinderEntity sbDemoCkfinder,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sbDemoCkfinder.getId())) {
			sbDemoCkfinder = sbDemoCkfinderService.getEntity(
					SbDemoCkfinderEntity.class, sbDemoCkfinder.getId());
			req.setAttribute("ckfinderPreview", sbDemoCkfinder);
		}
		return new ModelAndView("sb/demo/test/sbDemoCkfinderPreview");
	}
}
