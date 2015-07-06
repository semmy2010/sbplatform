package com.sbplatform.web.cgreport.controller.core;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.exception.BusinessException;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.MyBeanUtils;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.cgreport.entity.core.CgreportConfigHeadEntity;
import com.sbplatform.web.cgreport.entity.core.CgreportConfigItemEntity;
import com.sbplatform.web.cgreport.page.core.CgreportConfigHeadPage;
import com.sbplatform.web.cgreport.service.core.CgreportConfigHeadServiceI;
import com.sbplatform.web.system.pojo.base.Department;
import com.sbplatform.web.system.service.SystemService;
/**   
 * @Title: Controller
 * @Description: 动态报表配置抬头
 * @author 黄世民
 * @date 2013-12-07 16:00:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/cgreportConfigHeadController")
public class CgreportConfigHeadController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CgreportConfigHeadController.class);

	@Autowired
	private CgreportConfigHeadServiceI cgreportConfigHeadService;
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
	 * 动态报表配置抬头列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cgreportConfigHead")
	public ModelAndView cgreportConfigHead(HttpServletRequest request) {
		return new ModelAndView("sb/cgreport/core/cgreportConfigHeadList");
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
	public void datagrid(CgreportConfigHeadEntity cgreportConfigHead,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CgreportConfigHeadEntity.class, dataGrid);
		//查询条件组装器
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, cgreportConfigHead);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.cgreportConfigHeadService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除动态报表配置抬头
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(CgreportConfigHeadEntity cgreportConfigHead, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		cgreportConfigHead = systemService.getEntity(CgreportConfigHeadEntity.class, cgreportConfigHead.getId());
		message = "动态报表配置抬头删除成功";
		try{
			cgreportConfigHeadService.delete(cgreportConfigHead);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "动态报表配置抬头删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMessage(message);
		return j;
	}

	/**
	 * 批量删除动态报表配置抬头
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "动态报表配置抬头删除成功";
		try{
			for(String id:ids.split(",")){
				CgreportConfigHeadEntity cgreportConfigHead = systemService.getEntity(CgreportConfigHeadEntity.class, id);
				cgreportConfigHeadService.delete(cgreportConfigHead);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "动态报表配置抬头删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMessage(message);
		return j;
	}

	/**
	 * 添加动态报表配置抬头
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(CgreportConfigHeadEntity cgreportConfigHead,CgreportConfigHeadPage cgreportConfigHeadPage, HttpServletRequest request) {
		List<CgreportConfigItemEntity> cgreportConfigItemList =  cgreportConfigHeadPage.getCgreportConfigItemList();
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		try{
			cgreportConfigHeadService.addMain(cgreportConfigHead, cgreportConfigItemList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "动态报表配置抬头添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMessage(message);
		return j;
	}
	/**
	 * 更新动态报表配置抬头
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(CgreportConfigHeadEntity cgreportConfigHead,CgreportConfigHeadPage cgreportConfigHeadPage, HttpServletRequest request) {
		List<CgreportConfigItemEntity> cgreportConfigItemList =  cgreportConfigHeadPage.getCgreportConfigItemList();
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		try{
			cgreportConfigHeadService.updateMain(cgreportConfigHead, cgreportConfigItemList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新动态报表配置抬头失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMessage(message);
		return j;
	}

	/**
	 * 动态报表配置抬头新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(CgreportConfigHeadEntity cgreportConfigHead, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(cgreportConfigHead.getId())) {
			cgreportConfigHead = cgreportConfigHeadService.getEntity(CgreportConfigHeadEntity.class, cgreportConfigHead.getId());
			req.setAttribute("cgreportConfigHeadPage", cgreportConfigHead);
		}
		return new ModelAndView("sb/cgreport/core/cgreportConfigHead-add");
	}
	
	/**
	 * 动态报表配置抬头编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(CgreportConfigHeadEntity cgreportConfigHead, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(cgreportConfigHead.getId())) {
			cgreportConfigHead = cgreportConfigHeadService.getEntity(CgreportConfigHeadEntity.class, cgreportConfigHead.getId());
			req.setAttribute("cgreportConfigHeadPage", cgreportConfigHead);
		}
		return new ModelAndView("sb/cgreport/core/cgreportConfigHead-update");
	}
	
	
	/**
	 * 加载明细列表[动态报表配置明细]
	 * 
	 * @return
	 */
	@RequestMapping(params = "cgreportConfigItemList")
	public ModelAndView cgreportConfigItemList(CgreportConfigHeadEntity cgreportConfigHead, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = cgreportConfigHead.getId();
		//===================================================================================
		//查询-动态报表配置明细
	    String hql0 = "from CgreportConfigItemEntity where 1 = 1 AND cGRHEAD_ID = ? ";
	    try{
	    	List<CgreportConfigItemEntity> cgreportConfigItemEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("cgreportConfigItemList", cgreportConfigItemEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("sb/cgreport/core/cgreportConfigItemList");
	}
	
}
