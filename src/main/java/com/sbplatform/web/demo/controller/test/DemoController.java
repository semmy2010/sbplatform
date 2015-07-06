package com.sbplatform.web.demo.controller.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.ComboTree;
import com.sbplatform.core.common.model.json.TreeGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.extend.template.Template;
import com.sbplatform.core.util.MyBeanUtils;
import com.sbplatform.core.util.ResourceUtil;
import com.sbplatform.core.util.StreamUtils;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.core.util.oConvertUtils;
import com.sbplatform.tag.vo.easyui.ComboTreeModel;
import com.sbplatform.tag.vo.easyui.TreeGridModel;
import com.sbplatform.web.system.pojo.base.Attachment;
import com.sbplatform.web.system.pojo.base.Demo;
import com.sbplatform.web.system.pojo.base.Document;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.pojo.base.User;
import com.sbplatform.web.system.service.SystemService;


/**
 * @ClassName: demoController
 * @Description: TODO(演示例子处理类)
 * @author sb
 */
@Controller
@RequestMapping("/demoController")
public class DemoController extends BaseController {
	private static final Logger logger = Logger.getLogger(DemoController.class);
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
	
	/**
	 * demo添加页面跳转
	 */
	@RequestMapping(params = "aorudemo")
	public ModelAndView aorudemo(Demo demo, HttpServletRequest request) {
		String type = oConvertUtils.getString(request.getParameter("type"));
		if (demo.getId() != null) {
			demo = systemService.getEntity(Demo.class, demo.getId());
			request.setAttribute("demo", demo);
		}
		if (type.equals("table")) {
			return new ModelAndView("sb/demo/base/tabledemo");
		} else {
			return new ModelAndView("sb/demo/base/demo");
		}

	}
	
	/**
	 * 父级DEMO下拉菜单
	 */
	@RequestMapping(params = "pDemoList")
	@ResponseBody
	public List<ComboTree> pDemoList(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(Demo.class);
		if (comboTree.getId() != null) {
			cq.eq("parent.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("parent");
		}
		cq.add();
		List<Demo> demoList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "demoTitle", "subs", "demoUrl");
		comboTrees = systemService.ComboTree(demoList, comboTreeModel, null);
		return comboTrees;
	}
	@RequestMapping(params = "demoTurn")
	@ResponseBody
	public String demoTurn(String id){
		String code = systemService.get(Demo.class, id).getDemoCode();
		return HtmlUtils.htmlUnescape(code);
	}
	
	/**
	 * demo页面跳转
	 */
	@RequestMapping(params = "demoIframe")
	public ModelAndView demoIframe(HttpServletRequest request) {
		CriteriaQuery cq = new CriteriaQuery(Demo.class);
		cq.isNull("parent.id");
		cq.add();
		List<Demo> demoList = systemService.getListByCriteriaQuery(cq, false);
		request.setAttribute("demoList", demoList);
		return new ModelAndView("sb/demo/base/demoIframe");
	}

	/**
	 * demo页面跳转
	 */
	@RequestMapping(params = "demoList")
	public ModelAndView demoList(HttpServletRequest request) {
		return new ModelAndView("sb/demo/base/demoList");
	}

	
	/**
	 * 权限列表
	 */
	@RequestMapping(params = "demoGrid")
	@ResponseBody
	public List<TreeGrid> demoGrid(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(Demo.class);
		if (treegrid.getId() != null) {
			cq.eq("parent.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("parent");
		}
		cq.add();
		List<Demo> demoList = systemService.getListByCriteriaQuery(cq, false);
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("demoTitle");
		treeGridModel.setParentText("parent_demoTitle");
		treeGridModel.setParentId("parent_id");
		treeGridModel.setSrc("demoUrl");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("subs");
		List<TreeGrid> treeGrids = systemService.treegrid(demoList, treeGridModel);
		return treeGrids;
	}

	/**
	 * demoCode页面跳转
	 */
	@RequestMapping(params = "demoCode")
	public ModelAndView demoCode(Demo demo, HttpServletRequest request) {
		List<Demo> list = systemService.getList(Demo.class);
		demo = list.get(0);
		request.setAttribute("demo", demo);
		return new ModelAndView("sb/demo/base/democode");
	}
	
	/**
	 * AJAX 示例下拉框
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getDemo")
	@ResponseBody
	public AjaxJson getDemo(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String id = StringUtil.getEncodePra(req.getParameter("id"));
		String floor = "";
		CriteriaQuery cq = new CriteriaQuery(MenuFunction.class);
		cq.eq("parent.id", id);
		cq.add();
		List<MenuFunction> functions = systemService.getListByCriteriaQuery(cq, false);
		if (functions.size() > 0) {
			for (MenuFunction function : functions) {
				floor += "<input type=\"checkbox\"  name=\"floornum\" id=\"floornum\" value=\"" + function.getId() + "\">" + function.getName() + "&nbsp;&nbsp;";
			}
		} else {
			floor += "没有子项目!";
		}

		j.setMessage(floor);
		return j;
	}

	
	/**
	 * 上传TABS跳转
	 */
	@RequestMapping(params = "uploadTabs")
	public ModelAndView uploadTabs(HttpServletRequest request) {
		return new ModelAndView("sb/demo/base/upload/uploadTabs");
	}
	/**
	 * 图片预览TABS跳转
	 */
	@RequestMapping(params = "imgViewTabs")
	public ModelAndView imgViewTabs(HttpServletRequest request) {
		return new ModelAndView("sb/demo/base/picview/imgViewTabs");
	}
	/**
	 * 表单验证TABS跳转
	 */
	@RequestMapping(params = "formTabs")
	public ModelAndView formTabs(HttpServletRequest request) {
		return new ModelAndView("sb/demo/base/formvalid/formTabs");
	}
	/**
	 * 动态模板TABS跳转
	 */
	@RequestMapping(params = "templeteTabs")
	public ModelAndView templeteTabs(HttpServletRequest request) {
		return new ModelAndView("sb/demo/base/template/templateiframe");
	}
	/**
	 * 上传演示
	 */
	@RequestMapping(params = "autoupload")
	public ModelAndView autoupload(HttpServletRequest request) {
		String turn=oConvertUtils.getString(request.getParameter("turn"));
		return new ModelAndView("sb/demo/base/"+turn+"");
	}

	/**
	 *下拉联动跳转
	 */
	@RequestMapping(params = "select")
	public ModelAndView select(HttpServletRequest request) {
		// 新闻
		CriteriaQuery cq2 = new CriteriaQuery(MenuFunction.class);
		cq2.eq("functionLevel",Globals.Function_Leave_ONE);
		cq2.add();
		List<MenuFunction> funList = systemService.getListByCriteriaQuery(cq2, true);
		request.setAttribute("funList", funList);
		return new ModelAndView("sb/demo/base/AJAX/select");
	}
	/**
	 *数据字典下拉
	 */
	@RequestMapping(params = "dictSelect")
	public ModelAndView dictSelect(HttpServletRequest request) {
		request.setAttribute("process", "default");
		return new ModelAndView("sb/demo/base/dict/dictSelect");
	}
	/**
	 * 地图demo
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "mapDemo")
	public ModelAndView mapDemo(HttpServletRequest request) {
		return new ModelAndView("sb/demo/base/map/mapDemo2");
	}
	/**
	 * 保存DEMO维护
	 * 
	 * @param sbDemo
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "saveDemo")
	@ResponseBody
	public AjaxJson saveDemo(Demo demo, HttpServletRequest request) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!StringUtil.isEmpty(demo.getId())) {
			message = "Demo维护例子: " + demo.getDemoTitle() + "被更新成功";
			Demo entity = this.systemService.get(Demo.class, demo.getId());
			MyBeanUtils.copyBeanNotNull2Bean(demo, entity);
			
			if (demo.getParent() == null || StringUtil.isEmpty(demo.getParent().getId())) {
				entity.setParent(null);
			}
			this.systemService.saveOrUpdate(entity);
		}else {
			message = "Demo例子: " + demo.getDemoTitle() + "被添加成功";
			if (demo.getParent() == null || StringUtil.isEmpty(demo.getParent().getId())) {
				demo.setParent(null);
			}
			this.systemService.save(demo);
		}
		j.setMessage(message);
		return j;
	}
	
	
	/**
	 * 删除Demo
	 * 
	 * @return
	 */
	@RequestMapping(params = "delDemo")
	@ResponseBody
	public AjaxJson del(Demo demo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		demo = systemService.getEntity(Demo.class, demo.getId());
		message = "Demo: " + demo.getDemoTitle() + "被删除 成功";
		// 删除部门之前更新与之相关的实体
		//upEntity(demo);
		systemService.delete(demo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMessage(message);
		return j;
	}
	
	
}
