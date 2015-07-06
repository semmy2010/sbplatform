package com.sbplatform.web.system.controller.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.sbplatform.core.common.model.json.ComboTree;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.common.model.json.TreeGrid;
import com.sbplatform.core.common.model.json.ValidForm;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.ExceptionUtil;
import com.sbplatform.core.util.NumberComparator;
import com.sbplatform.core.util.SetListSort;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.core.util.oConvertUtils;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.tag.vo.datatable.SortDirection;
import com.sbplatform.tag.vo.easyui.ComboTreeModel;
import com.sbplatform.tag.vo.easyui.TreeGridModel;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.pojo.base.Operation;
import com.sbplatform.web.system.pojo.base.Role;
import com.sbplatform.web.system.pojo.base.RoleMenuFunction;
import com.sbplatform.web.system.pojo.base.RoleUser;
import com.sbplatform.web.system.service.SystemService;
import com.sbplatform.web.system.service.UserService;

/**
 * 角色处理类
 * 
 * @author 黄世民
 * 
 */
@Controller
@RequestMapping("/roleController")
public class RoleController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RoleController.class);
	private UserService userService;
	private SystemService systemService;
	private String message = null;

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
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	public ModelAndView role() {
		return new ModelAndView("system/role/roleList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "roleGrid")
	public void roleGrid(Role role, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(Role.class, dataGrid);
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, role);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
		;
	}

	/**
	 * 删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delRole")
	@ResponseBody
	public AjaxJson delRole(Role role, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		int count = userService.getUsersOfThisRole(role.getId());
		if (count == 0) {
			//删除角色之前先删除角色权限关系
			delRoleFunction(role);
			role = systemService.getEntity(Role.class, role.getId());
			userService.delete(role);
			message = "角色: " + role.getRoleName() + "被删除成功";
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} else {
			message = "角色: 仍被用户使用，请先删除关联关系";
		}
		j.setMessage(message);
		return j;
	}

	/**
	 * 检查角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "checkRole")
	@ResponseBody
	public ValidForm checkRole(Role role, HttpServletRequest request, HttpServletResponse response) {
		ValidForm v = new ValidForm();
		String roleCode = oConvertUtils.getString(request.getParameter("param"));
		String code = oConvertUtils.getString(request.getParameter("code"));
		List<Role> roles = systemService.findByProperty(Role.class, "roleCode", roleCode);
		if (roles.size() > 0 && !code.equals(roleCode)) {
			v.setInfo("角色编码已存在");
			v.setStatus("n");
		}
		return v;
	}

	/**
	 * 删除角色权限
	 * @param role
	 */
	protected void delRoleFunction(Role role) {
		List<RoleMenuFunction> roleFunctions = systemService.findByProperty(RoleMenuFunction.class, "role.id", role.getId());
		if (roleFunctions.size() > 0) {
			for (RoleMenuFunction roleMenuFunction : roleFunctions) {
				systemService.delete(roleMenuFunction);
			}
		}
		List<RoleUser> roleUsers = systemService.findByProperty(RoleUser.class, "role.id", role.getId());
		for (RoleUser roleUser : roleUsers) {
			systemService.delete(roleUser);
		}
	}

	/**
	 * 角色录入
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveRole")
	@ResponseBody
	public AjaxJson saveRole(Role role, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(role.getId())) {
			message = "角色: " + role.getRoleName() + "被更新成功";
			userService.saveOrUpdate(role);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "角色: " + role.getRoleName() + "被添加成功";
			userService.save(role);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}

		return j;
	}

	/**
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "fun")
	public ModelAndView fun(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		request.setAttribute("roleId", roleId);
		return new ModelAndView("system/role/roleSet");
	}

	/**
	 * 设置权限
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "setAuthority")
	@ResponseBody
	public List<ComboTree> setAuthority(Role role, HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(MenuFunction.class);
		if (comboTree.getId() != null) {
			cq.eq("parent.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("parent");
		}
		cq.notEq("level", Short.parseShort("-1"));
		cq.add();
		List<MenuFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		Collections.sort(functionList, new NumberComparator());
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		String roleId = request.getParameter("roleId");
		List<MenuFunction> loginActionlist = new ArrayList<MenuFunction>();// 已有权限菜单
		role = this.systemService.get(Role.class, roleId);
		if (role != null) {
			List<RoleMenuFunction> roleFunctionList = systemService.findByProperty(RoleMenuFunction.class, "role.id", role.getId());
			if (roleFunctionList.size() > 0) {
				for (RoleMenuFunction rmf : roleFunctionList) {
					MenuFunction function = (MenuFunction) rmf.getMenuFunction();
					loginActionlist.add(function);
				}
			}
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "name", "subs");
		comboTrees = systemService.ComboTree(functionList, comboTreeModel, loginActionlist);
		return comboTrees;
	}

	/**
	 * 更新权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateAuthority")
	@ResponseBody
	public AjaxJson updateAuthority(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String roleId = request.getParameter("roleId");
			String rolefunction = request.getParameter("rolefunctions");
			Role role = this.systemService.get(Role.class, roleId);
			List<RoleMenuFunction> roleFunctionList = systemService.findByProperty(RoleMenuFunction.class, "role.id", role.getId());
			Map<String, RoleMenuFunction> map = new HashMap<String, RoleMenuFunction>();
			for (RoleMenuFunction rmf : roleFunctionList) {
				map.put(rmf.getMenuFunction().getId(), rmf);
			}
			String[] roleFunctions = rolefunction.split(",");
			Set<String> set = new HashSet<String>();
			for (String s : roleFunctions) {
				set.add(s);
			}
			updateCompare(set, role, map);
			j.setMessage("权限更新成功");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMessage("权限更新失败");
		}
		return j;
	}

	/**
	 * 权限比较
	 * @param set 最新的权限列表
	 * @param role 当前角色
	 * @param map 旧的权限列表
	 * @param entitys 最后保存的权限列表
	 */
	private void updateCompare(Set<String> set, Role role, Map<String, RoleMenuFunction> map) {
		List<RoleMenuFunction> entitys = new ArrayList<RoleMenuFunction>();
		List<RoleMenuFunction> deleteEntitys = new ArrayList<RoleMenuFunction>();
		for (String s : set) {
			if (map.containsKey(s)) {
				map.remove(s);
			} else {
				RoleMenuFunction rf = new RoleMenuFunction();
				MenuFunction f = this.systemService.get(MenuFunction.class, s);
				rf.setMenuFunction(f);
				rf.setRole(role);
				entitys.add(rf);
			}
		}
		Collection<RoleMenuFunction> collection = map.values();
		Iterator<RoleMenuFunction> it = collection.iterator();
		for (; it.hasNext();) {
			deleteEntitys.add(it.next());
		}
		systemService.batchSave(entitys);
		systemService.deleteAllEntitie(deleteEntitys);

	}

	/**
	 * 角色页面跳转
	 * 
	 * @param role
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(Role role, HttpServletRequest req) {
		if (role.getId() != null) {
			role = systemService.getEntity(Role.class, role.getId());
			req.setAttribute("role", role);
		}
		return new ModelAndView("system/role/role");
	}

	/**
	 * 权限操作列表
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "setOperate")
	@ResponseBody
	public List<TreeGrid> setOperate(HttpServletRequest request, TreeGrid treegrid) {
		String roleId = request.getParameter("roleId");
		CriteriaQuery cq = new CriteriaQuery(MenuFunction.class);
		if (treegrid.getId() != null) {
			cq.eq("parent.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("parent");
		}
		cq.add();
		List<MenuFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		Collections.sort(functionList, new SetListSort());
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setRoleId(roleId);
		treeGrids = systemService.treegrid(functionList, treeGridModel);
		return treeGrids;

	}

	/**
	 * 操作录入
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveOperate")
	@ResponseBody
	public AjaxJson saveOperate(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String fop = request.getParameter("fp");
		String roleId = request.getParameter("roleId");
		//录入操作前清空上一次的操作数据
		clearp(roleId);
		String[] fun_op = fop.split(",");
		String aa = "";
		String bb = "";
		//只有一个被选中
		if (fun_op.length == 1) {
			bb = fun_op[0].split("_")[1];
			aa = fun_op[0].split("_")[0];
			savep(roleId, bb, aa);
		} else {
			//至少2个被选中
			for (int i = 0; i < fun_op.length; i++) {
				String cc = fun_op[i].split("_")[0]; //操作id
				if (i > 0 && bb.equals(fun_op[i].split("_")[1])) {
					aa += "," + cc;
					if (i == (fun_op.length - 1)) {
						savep(roleId, bb, aa);
					}
				} else if (i > 0) {
					savep(roleId, bb, aa);
					aa = fun_op[i].split("_")[0]; //操作ID
					if (i == (fun_op.length - 1)) {
						bb = fun_op[i].split("_")[1]; //权限id
						savep(roleId, bb, aa);
					}

				} else {
					aa = fun_op[i].split("_")[0]; //操作ID
				}
				bb = fun_op[i].split("_")[1]; //权限id

			}
		}

		return j;
	}

	/**
	 *更新操作
	 * @param roleId
	 * @param functionId
	 * @param ids
	 */
	public void savep(String roleId, String functionId, String ids) {
		CriteriaQuery cq = new CriteriaQuery(RoleMenuFunction.class);
		cq.eq("role.id", roleId);
		cq.eq("menuFunction.id", functionId);
		cq.add();
		List<RoleMenuFunction> rFunctions = systemService.getListByCriteriaQuery(cq, false);
		if (rFunctions.size() > 0) {
			RoleMenuFunction roleFunction = rFunctions.get(0);
			roleFunction.setOperation(ids);
			systemService.saveOrUpdate(roleFunction);
		}
	}

	/**
	 * 清空操作
	 * @param roleId
	 */
	public void clearp(String roleId) {
		List<RoleMenuFunction> rFunctions = systemService.findByProperty(RoleMenuFunction.class, "role.id", roleId);
		if (rFunctions.size() > 0) {
			for (RoleMenuFunction tRoleFunction : rFunctions) {
				tRoleFunction.setOperation(null);
				systemService.saveOrUpdate(tRoleFunction);
			}
		}
	}

	/**
	 * 按钮权限展示
	 * @param request
	 * @param functionId
	 * @param roleId
	 * @return
	 */
	@RequestMapping(params = "operationListForFunction")
	public ModelAndView operationListForFunction(HttpServletRequest request, String functionId, String roleId) {
		CriteriaQuery cq = new CriteriaQuery(Operation.class);
		cq.eq("menuFunction.id", functionId);
		cq.add();
		List<Operation> operationList = this.systemService.getListByCriteriaQuery(cq, false);
		Set<String> operationCodes = systemService.getOperationCodesByRoleIdAndFunctionId(roleId, functionId);
		request.setAttribute("operationList", operationList);
		request.setAttribute("operationcodes", operationCodes);
		request.setAttribute("functionId", functionId);
		return new ModelAndView("system/role/operationListForFunction");
	}

	/**
	 * 更新按钮权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateOperation")
	@ResponseBody
	public AjaxJson updateOperation(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String roleId = request.getParameter("roleId");
		String functionId = request.getParameter("functionId");
		String operationcodes = request.getParameter("operationcodes");
		CriteriaQuery cq1 = new CriteriaQuery(RoleMenuFunction.class);
		cq1.eq("role.id", roleId);
		cq1.eq("menuFunction.id", functionId);
		cq1.add();
		List<RoleMenuFunction> rFunctions = systemService.getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			RoleMenuFunction roleFunction = rFunctions.get(0);
			roleFunction.setOperation(operationcodes);
			systemService.saveOrUpdate(roleFunction);
		}
		j.setMessage("按钮权限更新成功");
		return j;
	}
}
