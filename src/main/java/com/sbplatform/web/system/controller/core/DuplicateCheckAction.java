package com.sbplatform.web.system.controller.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbplatform.common.Constant;
import com.sbplatform.core.common.controller.BaseController;
import com.sbplatform.core.common.dao.jdbc.JdbcDao;
import com.sbplatform.core.common.model.json.AjaxJson;
import com.sbplatform.web.system.pojo.base.DuplicateCheckPage;


/**   
 * @Title: Action
 * @Description: 校验工具Action
 * @author 黄世民
 * @date 2013-09-12 22:27:30
 * @version V1.0   
 */
@Controller
@RequestMapping("/duplicateCheckAction")
public class DuplicateCheckAction extends BaseController {

	private static final Logger logger = Logger.getLogger(DuplicateCheckAction.class);

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	
	/**
	 * 校验数据是否在系统中是否存在
	 * @return
	 */
	@RequestMapping(params = "doDuplicateCheck")
	@ResponseBody
	public AjaxJson doDuplicateCheck(DuplicateCheckPage duplicateCheckPage, HttpServletRequest request) {

		AjaxJson aj = new AjaxJson();
		Long num = null;
		
		if(StringUtils.isNotBlank(duplicateCheckPage.getRowObid())){
			//[2].编辑页面校验
			String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName()
						+" WHERE "+duplicateCheckPage.getFieldName() +" =? and id != ?";
			num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue(),duplicateCheckPage.getRowObid()});
		}else{
			//[1].添加页面校验
			String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName()
						+" WHERE "+duplicateCheckPage.getFieldName() +" =?";
			num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue()});
		}
		
		if(num==null||num==0){
			//该值可用
			aj.setStatus(Constant.CommonConstant.STATUS_SUCCESS);
			aj.setMessage("该值可用！");
		}else{
			//该值不可用
			aj.setStatus(Constant.CommonConstant.STATUS_FAILURE);
			aj.setMessage("该值不可用，系统中已存在！");
		}
		return aj;
	}
}
