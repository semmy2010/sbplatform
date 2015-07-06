package com.sbplatform.web.cgform.controller.upload;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbplatform.common.Constant;
import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.model.common.UploadFile;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.core.util.oConvertUtils;
import com.sbplatform.web.cgform.entity.upload.CgUploadEntity;
import com.sbplatform.web.cgform.service.upload.CgUploadServiceI;
import com.sbplatform.web.system.pojo.base.Attachment;
import com.sbplatform.web.system.service.SystemService;

/**
 * 
 * @Title:CgUploadController
 * @description:智能表单文件上传控制器
 * @author 赵俊夫
 * @date Jul 24, 2013 9:10:44 PM
 * @version V1.0
 */
@Controller
@RequestMapping("/cgUploadController")
public class CgUploadController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CgUploadController.class);
	@Autowired
	private SystemService systemService;
	@Autowired
	private CgUploadServiceI cgUploadService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 保存文件
	 * @param request
	 * @param response
	 * @param cgUploadEntity 智能表单文件上传实体
	 * @return
	 */
	@RequestMapping(params = "saveFiles", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveFiles(HttpServletRequest request, HttpServletResponse response, CgUploadEntity cgUploadEntity) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		String id = oConvertUtils.getString(request.getParameter("cgFormId"));//动态表主键ID
		String tableName = oConvertUtils.getString(request.getParameter("cgFormName"));//动态表名
		String cgField = oConvertUtils.getString(request.getParameter("cgFormField"));//动态表上传控件字段
		if(!StringUtil.isEmpty(id)){
			cgUploadEntity.setCgformId(id);
			cgUploadEntity.setCgformName(tableName);
			cgUploadEntity.setCgformField(cgField);
		}
		if (StringUtil.isNotEmpty(fileKey)) {
			cgUploadEntity.setId(fileKey);
			cgUploadEntity = systemService.getEntity(CgUploadEntity.class, fileKey);
		}
		UploadFile uploadFile = new UploadFile(request, cgUploadEntity);
		uploadFile.setCusPath("files");
		uploadFile.setSwfpath("swfpath");
		uploadFile.setByteField(null);//不存二进制内容
		cgUploadEntity = systemService.uploadFile(uploadFile);
		cgUploadService.writeBack(id, tableName, cgField, fileKey, cgUploadEntity.getRealPath());
		attributes.put("fileKey", cgUploadEntity.getId());
		attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + cgUploadEntity.getId());
		attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + cgUploadEntity.getId());
		j.setMessage("操作成功");
		j.setExpandData(attributes);
		return j;
	}
	
	/**
	 * 删除文件
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delFile")
	@ResponseBody
	public AjaxJson delFile( HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id  = request.getParameter("id");
		CgUploadEntity file = systemService.getEntity(CgUploadEntity.class, id);
		message = "" + file.getAttachmentName() + "被删除成功";
		cgUploadService.deleteFile(file);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		j.setStatus(Constant.CommonConstant.STATUS_SUCCESS);
		j.setMessage(message);
		return j;
	}
}
