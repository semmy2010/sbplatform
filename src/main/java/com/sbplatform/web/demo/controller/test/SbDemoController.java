package com.sbplatform.web.demo.controller.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.annotation.config.AutoMenu;
import com.sbplatform.core.annotation.config.AutoMenuOperation;
import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.MyBeanUtils;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.demo.entity.test.CKEditorEntity;
import com.sbplatform.web.demo.entity.test.SbDemo;
import com.sbplatform.web.demo.service.test.SbDemoServiceI;
import com.sbplatform.web.system.pojo.base.Department;
import com.sbplatform.web.system.service.SystemService;

/**   
 * @Title: Controller
 * @Description: 单表模型（DEMO）
 * @author 黄世民
 * @date 2013-01-23 17:12:40
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sbDemoController")
@AutoMenu(name = "常用Demo", url = "sbDemoController.do?sbDemo")
public class SbDemoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SbDemoController.class);

	@Autowired
	private SbDemoServiceI sbDemoService;
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
	 * popup 例子
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "popup")
	public ModelAndView popup(HttpServletRequest request) {
		return new ModelAndView("sb/demo/sbDemo/popup");
	}
	/**
	 * popup 例子
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "selectUserList")
	public ModelAndView selectUserList(HttpServletRequest request) {
		String departsReplace = "";
		List<Department> departList = systemService.getList(Department.class);
		for (Department depart : departList) {
			if (departsReplace.length() > 0) {
				departsReplace += ",";
			}
			departsReplace += depart.getName() + "_" + depart.getId();
		}
		request.setAttribute("departsReplace", departsReplace);
		return new ModelAndView("sb/demo/sbDemo/selectUserList");
	}
	
	/**
	 * ckeditor 例子
	 * 
	 * @return
	 */
	@RequestMapping(params = "ckeditor")
	public ModelAndView ckeditor(HttpServletRequest request) {
//		CKEditorEntity t = sbDemoService.get(CKEditorEntity.class, "1");
		CKEditorEntity t = sbDemoService.loadAll(CKEditorEntity.class).get(0);
		request.setAttribute("cKEditorEntity", t);
		if(t.getContents() == null ){
			request.setAttribute("contents", "");
		}else {
			request.setAttribute("contents", new String (t.getContents()));
		}
		return new ModelAndView("sb/demo/sbDemo/ckeditor");
	}
	/**
	 * ckeditor saveCkeditor
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveCkeditor")
	@ResponseBody
	public AjaxJson saveCkeditor(HttpServletRequest request,CKEditorEntity cKEditor , String contents) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(cKEditor.getId())) {
			CKEditorEntity t =sbDemoService.get(CKEditorEntity.class, cKEditor.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(cKEditor, t);
				t.setContents(contents.getBytes());
				sbDemoService.saveOrUpdate(t);
				j.setMessage("更新成功");
			} catch (Exception e) {
				e.printStackTrace();
				j.setMessage("更新失败");
			}
		} else {
			cKEditor.setContents(contents.getBytes());
			sbDemoService.save(cKEditor);
		}
		return j;
	}

	/**
	 * SbDemo 打印预览跳转
	 * @param sbDemo
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "print")
	public ModelAndView print(SbDemo sbDemo, HttpServletRequest req) {
		// 获取部门信息
		List<Department> departList = systemService.getList(Department.class);
		req.setAttribute("departList", departList);

		if (StringUtil.isNotEmpty(sbDemo.getId())) {
			sbDemo = sbDemoService.getEntity(SbDemo.class, sbDemo
					.getId());
			req.setAttribute("jgDemo", sbDemo);
			if ("0".equals(sbDemo.getSex()))
				req.setAttribute("sex", "男");
			if ("1".equals(sbDemo.getSex()))
				req.setAttribute("sex", "女");
		}
		return new ModelAndView("sb/demo/sbDemo/sbDemoPrint");
	}

	/**
	 * SbDemo例子列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sbDemo")
	public ModelAndView sbDemo(HttpServletRequest request) {
		return new ModelAndView("sb/demo/sbDemo/sbDemoList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(SbDemo sbDemo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SbDemo.class, dataGrid);
		//查询条件组装器
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sbDemo,request.getParameterMap());
		this.sbDemoService.getDataGridReturn(cq, true);
		String total_salary = String.valueOf(sbDemoService.findOneForJdbc("select sum(salary) as ssum from sb_demo").get("ssum"));
		/*
		 * 说明：格式为 字段名:值(可选，不写该值时为分页数据的合计) 多个合计 以 , 分割
		 */
		dataGrid.setFooter("salary:"+(total_salary.equalsIgnoreCase("null")?"0.0":total_salary)+",age,email:合计");
		TagUtil.datagrid(response, dataGrid);
	}

	
	/**
	 * 权限列表
	 */
	@RequestMapping(params = "combox")
	@ResponseBody
	public List<SbDemo> combox(HttpServletRequest request, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SbDemo.class);
		List<SbDemo> ls = this.sbDemoService.getListByCriteriaQuery(cq, false);
		return ls;
	}
	/**
	 * 删除SbDemo例子
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SbDemo sbDemo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		sbDemo = systemService.getEntity(SbDemo.class, sbDemo.getId());
		message = "SbDemo例子: " + sbDemo.getUserName() + "被删除 成功";
		sbDemoService.delete(sbDemo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMessage(message);
		return j;
	}


	/**
	 * 添加SbDemo例子
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	@AutoMenuOperation(name="添加",code = "add")
	public AjaxJson save(SbDemo sbDemo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sbDemo.getId())) {
			message = "SbDemo例子: " + sbDemo.getUserName() + "被更新成功";
			SbDemo t =sbDemoService.get(SbDemo.class, sbDemo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sbDemo, t);
				sbDemoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "SbDemo例子: " + sbDemo.getUserName() + "被添加成功";
			sbDemo.setStatus("0");
			sbDemoService.save(sbDemo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	
	
	/**
	 * 审核报错
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveAuthor")
	@ResponseBody
	public AjaxJson saveAuthor(SbDemo sbDemo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sbDemo.getId())) {
			message = "测试-用户申请成功";
			SbDemo t =sbDemoService.get(SbDemo.class, sbDemo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sbDemo, t);
				t.setStatus("1");
				sbDemoService.saveOrUpdate(t);
				j.setMessage(message);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return j;
	}

	/**
	 * SbDemo例子列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SbDemo sbDemo, HttpServletRequest req) {
		//获取部门信息
		List<Department> departList = systemService.getList(Department.class);
		req.setAttribute("departList", departList);
		
		Map sexMap = new HashMap();
		sexMap.put(0, "男");
		sexMap.put(1, "女");
		req.setAttribute("sexMap", sexMap);
		
		if (StringUtil.isNotEmpty(sbDemo.getId())) {
			sbDemo = sbDemoService.getEntity(SbDemo.class, sbDemo.getId());
			req.setAttribute("jgDemo", sbDemo);
		}
		return new ModelAndView("sb/demo/sbDemo/sbDemo");
	}
	
	
	/**
	 * 设置签名跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doCheck")
	public ModelAndView doCheck(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		if (StringUtil.isNotEmpty(id)) {
			SbDemo sbDemo = sbDemoService.getEntity(SbDemo.class,id);
			request.setAttribute("sbDemo", sbDemo);
		}
		return new ModelAndView("sb/demo/sbDemo/sbDemo-check");
	}

	/**
	 * 全选删除Demo实例管理
	 * 
	 * @return
	 * @author tanghan
	 * @date 2013-07-13 14:53:00
	 */
	@RequestMapping(params = "doDeleteALLSelect")
	@ResponseBody
	public AjaxJson doDeleteALLSelect(SbDemo sbDemo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String ids = request.getParameter("ids");
		String[] entitys = ids.split(",");
	    List<SbDemo> list = new ArrayList<SbDemo>();
		for(int i=0;i<entitys.length;i++){
			sbDemo = systemService.getEntity(SbDemo.class, entitys[i]);
            list.add(sbDemo);			
		}
		message = "删除成功";
		sbDemoService.deleteAllEntitie(list);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMessage(message);
		return j;
	}
}
