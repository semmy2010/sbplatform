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
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.demo.entity.test.SbOrderCustomEntity;
import com.sbplatform.web.demo.entity.test.SbOrderMainEntity;
import com.sbplatform.web.demo.entity.test.SbOrderProductEntity;
import com.sbplatform.web.demo.page.SbOrderMainPage;
import com.sbplatform.web.demo.service.test.SbOrderMainServiceI;
import com.sbplatform.web.system.service.SystemService;

/**   
 * @Title: Controller
 * @Description: 订单信息
 * @author 黄世民
 * @date 2013-03-19 22:01:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sbOrderMainNoTagController")
public class SbOrderMainNoTagController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SbOrderMainController.class);

	@Autowired
	private SbOrderMainServiceI sbOrderMainService;
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
	 * 订单信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sbOrderMainNoTag")
	public ModelAndView sbOrderMain(HttpServletRequest request) {
		return new ModelAndView("sb/demo/notag/sbOrderMainListNoTag");
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
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SbOrderMainEntity.class, dataGrid);
		this.sbOrderMainService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除订单信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SbOrderMainEntity sbOrderMain, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		sbOrderMain = systemService.getEntity(SbOrderMainEntity.class, sbOrderMain.getId());
		message = "删除成功";
		sbOrderMainService.delMain(sbOrderMain);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMessage(message);
		return j;
	}


	/**
	 * 添加订单及明细信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SbOrderMainEntity sbOrderMain ,SbOrderMainPage sbOrderMainPage,	
			HttpServletRequest request) {
		List<SbOrderProductEntity> sbOrderProducList =  sbOrderMainPage.getSbOrderProductList();
		List<SbOrderCustomEntity>  sbOrderCustomList = sbOrderMainPage.getSbOrderCustomList();
		Boolean sbOrderCustomShow = "true".equals(request.getParameter("sbOrderCustomShow"));
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sbOrderMain.getId())) {
			message = "更新成功";
			sbOrderMainService.updateMain(sbOrderMain, sbOrderProducList, sbOrderCustomList,sbOrderCustomShow);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "添加成功";
			sbOrderMainService.addMain(sbOrderMain, sbOrderProducList, sbOrderCustomList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMessage(message);
		return j;
	}
	/**
	 * 订单信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateNoTag")
	public ModelAndView addorupdate(SbOrderMainEntity sbOrderMain, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sbOrderMain.getId())) {
			sbOrderMain = sbOrderMainService.getEntity(SbOrderMainEntity.class, sbOrderMain.getId());
			req.setAttribute("sbOrderMainPage", sbOrderMain);
		}
		if (StringUtil.isNotEmpty(sbOrderMain.getGoOrderCode())) {
			List<SbOrderProductEntity> sbOrderProductEntityList =  sbOrderMainService.findByProperty(SbOrderProductEntity.class, "goOrderCode", sbOrderMain.getGoOrderCode());
			req.setAttribute("sbOrderProductList", sbOrderProductEntityList);
		}
		if (StringUtil.isNotEmpty(sbOrderMain.getGoOrderCode())) {
			List<SbOrderCustomEntity> sbSbOrderCustomEntityList =  sbOrderMainService.findByProperty(SbOrderCustomEntity.class, "goOrderCode", sbOrderMain.getGoOrderCode());
			req.setAttribute("sbOrderCustomList", sbSbOrderCustomEntityList);
		}
		return new ModelAndView("sb/demo/notag/sbOrderMainNoTag");
	}
}
