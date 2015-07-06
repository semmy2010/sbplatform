package com.sbplatform.web.system.controller.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.ComboTree;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.common.model.json.TreeGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.core.util.oConvertUtils;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.tag.vo.datatable.SortDirection;
import com.sbplatform.tag.vo.easyui.ComboTreeModel;
import com.sbplatform.tag.vo.easyui.TreeGridModel;
import com.sbplatform.web.system.pojo.base.Icon;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.pojo.base.Operation;
import com.sbplatform.web.system.service.MenuFunctionService;
import com.sbplatform.web.system.service.SystemService;
import com.sbplatform.web.system.service.UserService;

/**
 * 菜单权限处理类
 * 
 * @author 黄世民
 * 
 */
@Controller
@RequestMapping("/functionController")
public class FunctionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FunctionController.class);
	private UserService userService;
	private SystemService systemService;
	private String message = null;

	@Resource
	private MenuFunctionService menuFunctionService;

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
	 * 权限列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "function")
	public ModelAndView function() {
		return new ModelAndView("system/function/functionList");
	}

	/**
	 * 操作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "operation")
	public ModelAndView operation(HttpServletRequest request, String functionId) {
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		request.setAttribute("functionId", functionId);
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		return new ModelAndView("system/operation/operationList");
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
		CriteriaQuery cq = new CriteriaQuery(MenuFunction.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "opdategrid")
	public void opdategrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(Operation.class, dataGrid);
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		String functionId = oConvertUtils.getString(request.getParameter("functionId"));
		cq.eq("menuFunction.id", functionId);
		cq.add();
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除权限
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(MenuFunction function, HttpServletRequest request) {
		AjaxJson aj = new AjaxJson();
		function = this.systemService.getEntity(MenuFunction.class, function.getId());
		this.menuFunctionService.deleteMenu(function);

		return aj;
	}

	/**
	 * 删除操作
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delop")
	@ResponseBody
	public AjaxJson delop(Operation operation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		operation = systemService.getEntity(Operation.class, operation.getId());
		message = "操作: " + operation.getName() + "被删除成功";
		userService.delete(operation);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * 权限录入
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveFunction")
	@ResponseBody
	public AjaxJson saveFunction(MenuFunction function, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		String functionOrder = function.getSort();
		if (StringUtils.isEmpty(functionOrder)) {
			function.setSort("0");
		}
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		if (function.getParent().getId().equals("")) {
			function.setParent(null);
		} else {
			MenuFunction parent = systemService.getEntity(MenuFunction.class, function.getParent().getId());
			function.setLevel(Short.valueOf(parent.getLevel() + 1 + ""));
		}
		if (StringUtil.isNotEmpty(function.getId())) {
			message = "权限: " + function.getName() + "被更新成功";
			userService.saveOrUpdate(function);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			// ----------------------------------------------------------------
			// ----------------------------------------------------------------

			systemService.flushRoleFunciton(function.getId(), function);

			// ----------------------------------------------------------------
			// ----------------------------------------------------------------

		} else {
			if (function.getLevel().equals(Globals.Function_Leave_ONE)) {
				List<MenuFunction> functionList = systemService.findByProperty(MenuFunction.class, "level", Globals.Function_Leave_ONE);
				// int ordre=functionList.size()+1;
				// function.setFunctionOrder(Globals.Function_Order_ONE+ordre);
				function.setSort(function.getSort());
			} else {
				List<MenuFunction> functionList = systemService.findByProperty(MenuFunction.class, "level", Globals.Function_Leave_TWO);
				// int ordre=functionList.size()+1;
				// function.setFunctionOrder(Globals.Function_Order_TWO+ordre);
				function.setSort(function.getSort());
			}
			message = "权限: " + function.getName() + "被添加成功";
			userService.save(function);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}

		return j;
	}

	/**
	 * 操作录入
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveop")
	@ResponseBody
	public AjaxJson saveop(Operation operation, HttpServletRequest request) {
		String pid = request.getParameter("menuFunction.id");
		if (pid.equals("")) {
			operation.setMenuFunction(null);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(operation.getId())) {
			message = "操作: " + operation.getName() + "被更新成功";
			userService.saveOrUpdate(operation);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "操作: " + operation.getName() + "被添加成功";
			userService.save(operation);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}

		return j;
	}

	/**
	 * 权限列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(MenuFunction function, HttpServletRequest req) {
		String functionId = req.getParameter("id");
		List<MenuFunction> fuinctionlist = systemService.getList(MenuFunction.class);
		req.setAttribute("flist", fuinctionlist);
		List<Icon> iconlist = systemService.getList(Icon.class);
		req.setAttribute("iconlist", iconlist);
		if (functionId != null) {
			function = systemService.getEntity(MenuFunction.class, functionId);
			req.setAttribute("function", function);
		}
		if (function.getParent() != null && function.getParent().getId() != null) {
			function.setLevel((short) 1);
			function.setParent((MenuFunction) systemService.getEntity(MenuFunction.class, function.getParent().getId()));
			req.setAttribute("function", function);
		}
		return new ModelAndView("system/function/function");
	}

	/**
	 * 操作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateop")
	public ModelAndView addorupdateop(Operation operation, HttpServletRequest req) {
		List<Icon> iconlist = systemService.getList(Icon.class);
		req.setAttribute("iconlist", iconlist);
		if (operation.getId() != null) {
			operation = systemService.getEntity(Operation.class, operation.getId());
			req.setAttribute("operation", operation);
		}
		String functionId = oConvertUtils.getString(req.getParameter("functionId"));
		req.setAttribute("functionId", functionId);
		return new ModelAndView("system/operation/operation");
	}

	/**
	 * 权限列表
	 */
	@RequestMapping(params = "functionGrid")
	@ResponseBody
	public List<TreeGrid> functionGrid(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(MenuFunction.class);
		String selfId = request.getParameter("selfId");
		if (selfId != null) {
			cq.notEq("id", selfId);
		}
		if (treegrid.getId() != null) {
			cq.eq("parent.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("parent");
		}
		cq.addOrder("sort", SortDirection.asc);
		cq.add();
		List<MenuFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIcon("icon_iconPath");
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("parent_name");
		treeGridModel.setParentId("parent_id");
		treeGridModel.setSrc("url");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("subs");
		// 添加排序字段
		treeGridModel.setOrder("sort");
		treeGrids = systemService.treegrid(functionList, treeGridModel);
		return treeGrids;
	}

	/**
	 * 权限列表
	 */
	@RequestMapping(params = "functionList")
	@ResponseBody
	public void functionList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MenuFunction.class, dataGrid);
		String id = oConvertUtils.getString(request.getParameter("id"));
		cq.isNull("parent");
		if (id != null) {
			cq.eq("parent.id", id);
		}
		cq.add();
		List<MenuFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 父级权限下拉菜单
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(MenuFunction.class);
		if (null != request.getParameter("selfId")) {
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("parent.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("parent");
		}
		cq.add();
		List<MenuFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "name", "subs");
		comboTrees = systemService.ComboTree(functionList, comboTreeModel, null);
		return comboTrees;
	}
}
