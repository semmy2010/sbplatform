package com.sbplatform.web.demo.controller.test;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.core.common.model.json.DataGrid;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.core.util.ExceptionUtil;
import com.sbplatform.core.util.MyBeanUtils;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.tag.core.easyui.TagUtil;
import com.sbplatform.web.demo.entity.test.SbBlobDataEntity;
import com.sbplatform.web.demo.service.test.SbBlobDataServiceI;
import com.sbplatform.web.system.service.SystemService;

/**   
 * @Title: Controller
 * @Description: Blob型数据操作例子
 * @author Quainty
 * @date 2013-06-07 14:46:08
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sbBlobDataController")
public class SbBlobDataController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SbBlobDataController.class);

	@Autowired
	private SbBlobDataServiceI sbBlobDataService;
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
	 * Blob型数据操作例子列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sbBlobData")
	public ModelAndView sbBlobData(HttpServletRequest request) {
		return new ModelAndView("sb/demo/test/sbBlobDataList");
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
	public void datagrid(SbBlobDataEntity sbBlobData,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SbBlobDataEntity.class, dataGrid);
		//查询条件组装器
		com.sbplatform.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sbBlobData);
		this.sbBlobDataService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除Blob型数据操作例子
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SbBlobDataEntity sbBlobData, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		sbBlobData = systemService.getEntity(SbBlobDataEntity.class, sbBlobData.getId());
		message = "删除成功";
		sbBlobDataService.delete(sbBlobData);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMessage(message);
		return j;
	}
	
	@RequestMapping(params = "download")
	public void exportXls(HttpServletRequest request, String fileId, HttpServletResponse response) {
		// 从数据库取得数据
		SbBlobDataEntity obj = systemService.getEntity(SbBlobDataEntity.class, fileId);
	    try {      
	    	Blob attachment = obj.getAttachmentcontent();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String((obj.getAttachmenttitle()+"."+obj.getExtend()).getBytes("GBK"), "ISO8859-1"));
	        //从数据库中读取出来    , 输出给下载用
	        InputStream bis = attachment.getBinaryStream();      
	        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			long lTotalLen = 0;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
				lTotalLen += bytesRead;
			}
			response.setHeader("Content-Length", String.valueOf(lTotalLen));
			bos.flush();
			bos.close();
	    } catch (Exception  e){      
	        e.printStackTrace();      
	    }                 
	}
	@RequestMapping(params = "upload")
	@ResponseBody
    public AjaxJson upload(HttpServletRequest request, String documentTitle, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			try {
				sbBlobDataService.saveObj(documentTitle, file);
				j.setMessage("文件导入成功！");
			} catch (Exception e) {
				j.setMessage("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}
			//break; // 不支持多个文件导入？
		}

		return j;
    }


	/**
	 * 添加Blob型数据操作例子
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SbBlobDataEntity sbBlobData, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sbBlobData.getId())) {
			message = "更新成功";
			SbBlobDataEntity t = sbBlobDataService.get(SbBlobDataEntity.class, sbBlobData.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sbBlobData, t);
				sbBlobDataService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			sbBlobDataService.save(sbBlobData);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * Blob型数据操作例子列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SbBlobDataEntity sbBlobData, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sbBlobData.getId())) {
			sbBlobData = sbBlobDataService.getEntity(SbBlobDataEntity.class, sbBlobData.getId());
			req.setAttribute("sbBlobDataPage", sbBlobData);
		}
		return new ModelAndView("sb/demo/test/sbBlobData");
	}
}
