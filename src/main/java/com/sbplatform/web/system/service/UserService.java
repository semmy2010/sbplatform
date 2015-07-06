package com.sbplatform.web.system.service;

import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.web.system.pojo.base.User;
/**
 * 
 * @author  黄世民
 *
 */
public interface UserService extends CommonService{

	public User checkUserExits(User user);
	public String getUserRole(User user);
	public void pwdInit(User user, String newPwd);
	/**
	 * 判断这个角色是不是还有用户使用
	 *@Author JueYue
	 *@date   2013-11-12
	 *@param id
	 *@return
	 */
	public int getUsersOfThisRole(String id);
}
