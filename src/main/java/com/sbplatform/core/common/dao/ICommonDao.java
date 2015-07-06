package com.sbplatform.core.common.dao;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.sbplatform.core.common.model.common.UploadFile;
import com.sbplatform.core.common.model.json.ComboTree;
import com.sbplatform.core.common.model.json.ImportFile;
import com.sbplatform.core.common.model.json.TreeGrid;
import com.sbplatform.core.extend.template.Template;
import com.sbplatform.tag.vo.easyui.ComboTreeModel;
import com.sbplatform.tag.vo.easyui.TreeGridModel;
import com.sbplatform.web.system.pojo.base.Department;
import com.sbplatform.web.system.pojo.base.User;

public interface ICommonDao extends IGenericBaseCommonDao{
	
	
	/**
	 * admin账户密码初始化
	 * @param user
	 */
	public void pwdInit(User user,String newPwd);
	/**
	 * 检查用户是否存在
	 * */
	public User getUserByUserIdAndUserNameExits(User user);
	public String getUserRole(User user);
	/**
	 * 文件上传
	 * @param request
	 */
	public <T> T  uploadFile(UploadFile uploadFile);
	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	public Map<Object,Object> getDataSourceMap(Template template);
	/**
	 * 生成XML文件
	 * @param fileName XML全路径
	 */
	public HttpServletResponse createXml(ImportFile importFile);
	/**
	 * 解析XML文件
	 * @param fileName XML全路径
	 */
	public void parserXml(String fileName);
	public List<ComboTree> comTree(List<Department> all,ComboTree comboTree);
	/**
	 * 根据模型生成JSON
	 * @param all 全部对象
	 * @param in  已拥有的对象
	 * @param comboBox 模型
	 * @return
	 */
	public  List<ComboTree> ComboTree(List all,ComboTreeModel comboTreeModel,List in);
	public  List<TreeGrid> treegrid(List all,TreeGridModel treeGridModel);
}

