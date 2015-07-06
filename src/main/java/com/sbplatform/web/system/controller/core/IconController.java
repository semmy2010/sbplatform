package com.sbplatform.web.system.controller.core;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.common.Constant;
import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.common.UploadFile;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.core.util.oConvertUtils;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.pojo.base.Icon;
import com.sbplatform.web.system.pojo.base.Operation;
import com.sbplatform.web.system.service.SystemService;


/**
 * 图标信息处理类
 * 
 * @author 黄世民
 * 
 */
@Controller
@RequestMapping("/iconController")
public class IconController extends BaseController {
	 
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
	 * 图标列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "icon")
	public ModelAndView icon() {
		return new ModelAndView("system/icon/iconList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(Icon icon,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(Icon.class, dataGrid);
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, icon);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
        IconImageUtil.convertDataGrid(dataGrid, request);//先把数据库的byte存成图片到临时目录，再给每个Icon设置目录路径
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 上传图标
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "saveOrUpdateIcon", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveOrUpdateIcon(HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();		
		Icon icon = new Icon();
		Short iconType = oConvertUtils.getShort(request.getParameter("iconType"));
		String iconName = oConvertUtils.getString(request.getParameter("iconName"));
		String id = request.getParameter("id");
		icon.setId(id);
		icon.setIconName(iconName);
		icon.setIconType(iconType);
		// uploadFile.setBasePath("images/accordion");
		UploadFile uploadFile = new UploadFile(request, icon);
		uploadFile.setCusPath("plug-in/accordion/images");
		uploadFile.setExtend("extend");
		uploadFile.setTitleField("iconclass");
		uploadFile.setRealPath("iconPath");
		uploadFile.setObject(icon);
		uploadFile.setByteField("iconContent");
		uploadFile.setRename(false);
		systemService.uploadFile(uploadFile);
		// 图标的css样式
		String css = "." + icon.getIconClass() + "{background:url('../images/" + icon.getIconClass() + "." + icon.getExtendName() + "') no-repeat}";
		write(request, css);
		message = "上传成功";
		j.setMessage(message);
		return j;
	}	
	/**
	 * 没有上传文件时更新信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "update", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson update(HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		Short iconType = oConvertUtils.getShort(request.getParameter("iconType"));
		String iconName = java.net.URLDecoder.decode(oConvertUtils.getString(request.getParameter("iconName")));
		String id = request.getParameter("id");
		Icon icon = new Icon();
		if (StringUtil.isNotEmpty(id)) {
			icon = systemService.get(Icon.class, id);
			icon.setId(id);
		}
		icon.setIconName(iconName);
		icon.setIconType(iconType);
		systemService.saveOrUpdate(icon);
		// 图标的css样式
		String css = "." + icon.getIconClass() + "{background:url('../images/" + icon.getIconClass() + "." + icon.getExtendName() + "') no-repeat}";
		write(request, css);
		message = "更新成功";
		j.setMessage(message);
		return j;
	}
	/**
	 * 添加图标样式
	 * 
	 * @param request
	 * @param css
	 */
	protected void write(HttpServletRequest request, String css) {
		try {
			String path = request.getSession().getServletContext().getRealPath("/plug-in/accordion/css/icons.css");
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter out = new FileWriter(file, true);
			out.write("\r\n");
			out.write(css);
			out.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 恢复图标（将数据库图标数据写入图标存放的路径下）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "repair")
	@ResponseBody
	public AjaxJson repair(HttpServletRequest request) throws Exception {
		AjaxJson json = new AjaxJson();
		List<Icon> icons = systemService.loadAll(Icon.class);
		String rootpath = request.getSession().getServletContext().getRealPath("/");
		String csspath = request.getSession().getServletContext().getRealPath("/plug-in/accordion/css/icons.css");
		// 清空CSS文件内容
		clearFile(csspath);
		for (Icon c : icons) {
			File file = new File(rootpath + c.getIconPath());
			if (!file.exists()) {
				byte[] content = c.getIconContent();
				if (content != null) {
					BufferedImage imag = ImageIO.read(new ByteArrayInputStream(content));
					ImageIO.write(imag, "PNG", file);// 输出到 png 文件
				}
			}
			String css = "." + c.getIconClass() + "{background:url('../images/" + c.getIconClass() + "." + c.getExtendName() + "') no-repeat}";
			write(request, css);
			json.setMessage("样式表创建成功");
		}
		json.setStatus(Constant.CommonConstant.STATUS_SUCCESS);
		return json;
	}

	/**
	 * 清空文件内容
	 * 
	 * @param request
	 * @param css
	 */
	protected void clearFile(String path) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(path));
			fos.write("".getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除图标
	 * 
	 * @param icon
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(Icon icon, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		icon = systemService.getEntity(Icon.class, icon.getId());
		
		boolean isPermit=isPermitDel(icon);
		
		if(isPermit){
			systemService.delete(icon);
			
			message = "图标: " + icon.getIconName() + "被删除成功。";
			
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			
			return j;
		}
		
		message = "图标: " + icon.getIconName() + "正在使用，不允许删除。";

		j.setMessage(message);
		
		return j;
	}

	/**
	 * 检查是否允许删除该图标。
	 * @param icon 图标。
	 * @return true允许；false不允许；
	 */
	private boolean isPermitDel(Icon icon) {
		List<MenuFunction> functions = systemService.findByProperty(MenuFunction.class, "icon.id", icon.getId());
		if (functions==null||functions.isEmpty()) {
			return true;
		}
		return false;
	}

	public void upEntity(Icon icon) {
		List<MenuFunction> functions = systemService.findByProperty(MenuFunction.class, "icon.id", icon.getId());
		if (functions.size() > 0) {
			for (MenuFunction mf : functions) {
				mf.setIcon(null);
				systemService.saveOrUpdate(mf);
			}
		}
		List<Operation> operations = systemService.findByProperty(Operation.class, "icon.id", icon.getId());
		if (operations.size() > 0) {
			for (Operation operation : operations) {
				operation.setIcon(null);
				systemService.saveOrUpdate(operation);
			}
		}
	}

	/**
	 * 图标页面跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(Icon icon, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(icon.getId())) {
			icon = systemService.getEntity(Icon.class, icon.getId());
			req.setAttribute("icon", icon);
		}
		return new ModelAndView("system/icon/icons");
	}
}
