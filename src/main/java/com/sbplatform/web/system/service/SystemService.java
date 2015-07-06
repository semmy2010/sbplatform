package com.sbplatform.web.system.service;

import java.util.Set;

import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.pojo.base.Type;
import com.sbplatform.web.system.pojo.base.TypeGroup;
import com.sbplatform.web.system.pojo.base.User;

/**
 * 
 * @author  黄世民
 *
 */
public interface SystemService extends CommonService{
	/**
	 * 登陆用户检查
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User checkUserExits(User user) throws Exception;
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 类型
	 * @param TUser 操作人
	 */
	public void addLog(String LogContent, Short loglevel,Short operatetype);
	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * @param typeCode
	 * @param typeName
	 * @return
	 */
	public Type getType(String typeCode,String typeName,TypeGroup typeGroup);
	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * @param typeCode
	 * @param typeName
	 * @return
	 */
	public TypeGroup getTypeGroup(String typeGroupCode,String typeGroupName);
	/**
	 * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public  Set<String> getOperationCodesByUserIdAndFunctionId(String userId,String functionId);
	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public  Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId,String functionId);
	/**
	 * 根据编码获取字典组
	 * 
	 * @param typeGroupCode
	 * @return
	 */
	public TypeGroup getTypeGroupByCode(String typeGroupCode);
	/**
	 * 对数据字典进行缓存
	 */
	public void initAllTypeGroups();
	
	/**
	 * 刷新字典缓存
	 * @param type
	 */
	public void refleshTypesCach(Type type);
	/**
	 * 刷新字典分组缓存
	 */
	public void refleshTypeGroupCach();
	/**
	 * 刷新菜单
	 * 
	 * @param id
	 */
	public void flushRoleFunciton(String id, MenuFunction newFunciton);

	
}
