package com.sbplatform.web.system.controller.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.ComboTree;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.common.model.json.TreeGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.core.util.oConvertUtils;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.tag.vo.easyui.ComboTreeModel;
import com.sbplatform.tag.vo.easyui.TreeGridModel;
import com.sbplatform.web.system.pojo.base.Department;
import com.sbplatform.web.system.pojo.base.User;
import com.sbplatform.web.system.service.SystemService;
import com.sbplatform.web.system.service.UserService;


/**
 * 部门信息处理类
 * 
 * @author 黄世民
 * 
 */
@Controller
@RequestMapping("/departController")
public class DepartController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartController.class);
	private UserService userService;
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 部门列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "depart")
	public ModelAndView depart() {
		return new ModelAndView("system/depart/departList");
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
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(Department.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除部门
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(Department depart, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		depart = systemService.getEntity(Department.class, depart.getId());
		message = "部门: " + depart.getName() + "被删除 成功";
		// 删除部门之前更新与之相关的实体
		upEntity(depart);
		systemService.delete(depart);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMessage(message);
		return j;
	}

	public void upEntity(Department depart) {
		List<User> users = systemService.findByProperty(User.class, "parent.id", depart.getId());
		if (users.size() > 0) {
			for (User user : users) {
				systemService.delete(user);
			}
		}
	}

	/**
	 * 添加部门
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(Department depart, HttpServletRequest request) {
		// 设置上级部门
		String pid = request.getParameter("depart.id");
		if (pid.equals("")) {
			depart.setParent(null);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(depart.getId())) {
			message = "部门: " + depart.getName() + "被更新成功";
			userService.saveOrUpdate(depart);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "部门: " + depart.getName() + "被添加成功";
			userService.save(depart);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	@RequestMapping(params = "add")
	public ModelAndView add(Department depart, HttpServletRequest req) {
		List<Department> departList = systemService.getList(Department.class);
		req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(depart.getId())) {
			Department parentDepart = new Department();
			Department department = new Department();
			depart = systemService.getEntity(Department.class, depart.getId());
			parentDepart.setId(depart.getId());
			parentDepart.setName(depart.getName());
			department.setParent(parentDepart);
			req.setAttribute("depart", department);
		}
		return new ModelAndView("system/depart/depart");
	}
	/**
	 * 部门列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(Department depart, HttpServletRequest req) {
		List<Department> departList = systemService.getList(Department.class);
		req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(depart.getId())) {
			depart = systemService.getEntity(Department.class, depart.getId());
			req.setAttribute("depart", depart);
		}
		return new ModelAndView("system/depart/depart");
	}
	
	/**
	 * 父级权限列表
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(Department.class);
		if(null != request.getParameter("selfId")){
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("depart.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("depart");
		}
		cq.add();
		List<Department> departsList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "name", "subs");
		comboTrees = systemService.ComboTree(departsList, comboTreeModel, null);
		return comboTrees;

	}
	/**
	 * 部门列表，树形展示
	 * @param request
	 * @param response
	 * @param treegrid
	 * @return
	 */
	@RequestMapping(params = "departgrid")
	@ResponseBody
	public  List<TreeGrid>  departgrid(Department depart,HttpServletRequest request, HttpServletResponse response, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(Department.class);
		if("yes".equals(request.getParameter("isSearch"))){
			treegrid.setId(null);
			depart.setId(null);
		} 
		if(null != depart.getName()){
			com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, depart);
		}
		if (treegrid.getId() != null) {
			cq.eq("parent.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("parent");
		}
		cq.add();
		List<TreeGrid> departList =null;
		departList=systemService.getListByCriteriaQuery(cq, false);
		if(departList.size()==0&&depart.getName()!=null){ 
			cq = new CriteriaQuery(Department.class);
			Department parDepart = new Department();
			depart.setParent(parDepart);
			com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, depart);
		     departList =systemService.getListByCriteriaQuery(cq, false);
		}
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("parent_name");
		treeGridModel.setParentId("parent_id");
		treeGridModel.setSrc("description");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("subs");
		treeGrids = systemService.treegrid(departList, treeGridModel);
		return treeGrids;
	}
	//----
	/**
	 * 方法描述:  查看成员列表
	 * 作    者： yiming.zhang
	 * 日    期： Dec 4, 2013-8:53:39 PM
	 * @param request
	 * @param departid
	 * @return 
	 * 返回类型： ModelAndView
	 */
	@RequestMapping(params = "userList")
	public ModelAndView userList(HttpServletRequest request, String departId) {
		request.setAttribute("departId", departId);
		return new ModelAndView("system/depart/departUserList");
	}
	
	/**
	 * 方法描述:  成员列表dataGrid
	 * 日    期： Dec 4, 2013-10:40:17 PM
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid 
	 * 返回类型： void
	 */
	@RequestMapping(params = "userDatagrid")
	public void userDatagrid(User user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(User.class, dataGrid);
		//查询条件组装器
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
		String departId = oConvertUtils.getString(request.getParameter("departId"));
		if (!StringUtil.isEmpty(departId)) {
			DetachedCriteria dc = cq.getDetachedCriteria();
			DetachedCriteria dcDepart = dc.createCriteria("department");
			dcDepart.add(Restrictions.eq("id", departId));
		}
		Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN };
		cq.in("status", userstate);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	//----
	
	
}
