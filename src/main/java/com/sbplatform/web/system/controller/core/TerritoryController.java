package com.sbplatform.web.system.controller.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.ComboTree;
import com.sbplatform.core.common.model.json.TreeGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.vo.datatable.SortDirection;
import com.sbplatform.tag.vo.easyui.ComboTreeModel;
import com.sbplatform.tag.vo.easyui.TreeGridModel;
import com.sbplatform.web.system.pojo.base.Territory;
import com.sbplatform.web.system.service.SystemService;


/**
 * 地域处理类
 * @author wushu
 */
@Controller
@RequestMapping("/territoryController")
public class TerritoryController extends BaseController {
	
	private String message = null;
	
	@Autowired
	private SystemService systemService;

	/**
	 * 地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "territory")
	public ModelAndView function() {
		return new ModelAndView("system/territory/territoryList");
	}

	
	/**
	 * 地域列表
	 */
	@RequestMapping(params = "territoryGrid")
	@ResponseBody
	public List<TreeGrid> territoryGrid(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(Territory.class);
			if (treegrid.getId() != null) {
				cq.eq("parent.id", treegrid.getId());
			}
			if (treegrid.getId() == null) {
				cq.eq("parent.id","1");//这个是全国最高级
			}
		
		cq.addOrder("sort", SortDirection.asc);
		cq.add();
		List<Territory> territoryList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIcon("");
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("parent_name");
		treeGridModel.setParentId("parent_id");
		treeGridModel.setSrc("code");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("subs");
		treeGridModel.setOrder("sort");
		treeGrids = systemService.treegrid(territoryList, treeGridModel);
		return treeGrids;
	}
	/**
	 *地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(Territory territory, HttpServletRequest req) {
		String functionId = req.getParameter("id");
		if (functionId != null) {
			territory = systemService.getEntity(Territory.class, functionId);
			req.setAttribute("territory", territory);
		}
		if(territory.getParent()!=null && territory.getParent().getId()!=null){
			territory.setParent((Territory)systemService.getEntity(Territory.class, territory.getParent().getId()));
			req.setAttribute("territory", territory);
		}
		return new ModelAndView("system/territory/territory");
	}
	/**
	 * 地域父级下拉菜单
	 */
	@RequestMapping(params = "setPTerritory")
	@ResponseBody
	public List<ComboTree> setPTerritory(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(Territory.class);
		if (comboTree.getId() != null) {
			cq.eq("parent.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("parent");
		}
		cq.add();
		List<Territory> territoryList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "name", "subs");
		comboTrees = systemService.ComboTree(territoryList, comboTreeModel, null);
		return comboTrees;
	}
	/**
	 * 地域保存
	 */
	@RequestMapping(params = "saveTerritory")
	@ResponseBody
	public AjaxJson saveTerritory(Territory territory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String functionOrder = territory.getSort();
		if(StringUtils.isEmpty(functionOrder)){
			territory.setSort("0");
		}
		if (territory.getParent().getId().equals("")) {
			territory.setParent(null);
		}else{
			Territory parent = systemService.getEntity(Territory.class, territory.getParent().getId());
			territory.setLevel(Short.valueOf(parent.getLevel()+1+""));
		}
		if (StringUtil.isNotEmpty(territory.getId())) {
			message = "地域: " + territory.getName() + "被更新成功";
			systemService.saveOrUpdate(territory);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			territory.setSort(territory.getSort());
			message = "地域: " + territory.getName() + "被添加成功";
			systemService.save(territory);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}
		
		return j;
	}

	/**
	 * 地域删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(Territory territory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		territory = systemService.getEntity(Territory.class, territory.getId());
		message = "地域: " + territory.getName() + "被删除成功";
		systemService.delete(territory);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		return j;
	}

}
