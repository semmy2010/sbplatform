package com.sbplatform.web.system.service.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import com.sbplatform.web.system.pojo.base.RoleUser;
import com.sbplatform.web.system.pojo.base.User;
import com.sbplatform.web.system.service.UserService;

/**
 * 
 * @author  黄世民
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	public User checkUserExits(User user){
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}
	public String getUserRole(User user){
		return this.commonDao.getUserRole(user);
	}
	
	public void pwdInit(User user,String newPwd) {
			this.commonDao.pwdInit(user,newPwd);
	}
	
	public int getUsersOfThisRole(String id) {
		Criteria criteria = getSession().createCriteria(RoleUser.class);
		criteria.add(Restrictions.eq("role.id", id));
		int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		return allCounts;
	}
	
}
