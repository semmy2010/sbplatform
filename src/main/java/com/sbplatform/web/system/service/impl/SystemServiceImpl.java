package com.sbplatform.web.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbplatform.core.common.hibernate.qbc.CriteriaQuery;
import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import com.sbplatform.core.util.BrowserUtils;
import com.sbplatform.core.util.ContextHolderUtils;
import com.sbplatform.core.util.DataUtils;
import com.sbplatform.core.util.ResourceUtil;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.core.util.oConvertUtils;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.pojo.base.Icon;
import com.sbplatform.web.system.pojo.base.Log;
import com.sbplatform.web.system.pojo.base.Role;
import com.sbplatform.web.system.pojo.base.RoleMenuFunction;
import com.sbplatform.web.system.pojo.base.RoleUser;
import com.sbplatform.web.system.pojo.base.Type;
import com.sbplatform.web.system.pojo.base.TypeGroup;
import com.sbplatform.web.system.pojo.base.User;
import com.sbplatform.web.system.service.SystemService;

@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
	public User checkUserExits(User user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}

	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short loglevel, Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		Log log = new Log();
		log.setLogContent(logcontent);
		log.setLogLevel(loglevel);
		log.setOperateType(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOccurTime(DataUtils.gettimestamp());
		commonDao.save(log);
	}

	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * 
	 * @param typeCode
	 * @param typeName
	 * @return
	 */
	public Type getType(String typeCode,String typeName,TypeGroup typeGroup) {
		Type actType = commonDao.findUniqueByProperty(Type.class, "code", typeCode);
		if (actType == null) {
			actType = new Type();
			actType.setCode(typeCode);
			actType.setName(typeName);
			actType.setTypeGroup(typeGroup);
			commonDao.save(actType);
		}
		return actType;

	}

	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * 
	 * @param typeCode
	 * @param typeName
	 * @return
	 */
	public TypeGroup getTypeGroup(String typeGroupCode,String typeGroupName) {
		TypeGroup typeGroup = commonDao.findUniqueByProperty(TypeGroup.class, "typeGroupCode", typeGroupCode);
		if (typeGroup == null) {
			typeGroup = new TypeGroup();
			typeGroup.setTypeGroupCode(typeGroupCode);
			typeGroup.setTypeGroupName(typeGroupName);
			commonDao.save(typeGroup);
		}
		return typeGroup;
	}

	public TypeGroup getTypeGroupByCode(String typeGroupCode) {
		TypeGroup typeGroup = commonDao.findUniqueByProperty(TypeGroup.class, "typeGroupCode", typeGroupCode);
		return typeGroup;
	}

	public void initAllTypeGroups() {
		List<TypeGroup> typeGroups = this.commonDao.loadAll(TypeGroup.class);
		for (TypeGroup typeGroup : typeGroups) {
			TypeGroup.allTypeGroups.put(typeGroup.getTypeGroupCode().toLowerCase(), typeGroup);
			List<Type> types = this.commonDao.findByProperty(Type.class, "typeGroup.id", typeGroup.getId());
			TypeGroup.allTypes.put(typeGroup.getTypeGroupCode().toLowerCase(), types);
		}
	}

	public void refleshTypesCach(Type type) {
		TypeGroup typeGroup = type.getTypeGroup();
		TypeGroup typeGroupEntity = this.commonDao.get(TypeGroup.class, typeGroup.getId());
		List<Type> types = this.commonDao.findByProperty(Type.class, "typeGroup.id", typeGroup.getId());
		TypeGroup.allTypes.put(typeGroupEntity.getTypeGroupCode().toLowerCase(), types);
	}

	public void refleshTypeGroupCach() {
		TypeGroup.allTypeGroups.clear();
		List<TypeGroup> typeGroups = this.commonDao.loadAll(TypeGroup.class);
		for (TypeGroup typeGroup : typeGroups) {
			TypeGroup.allTypeGroups.put(typeGroup.getTypeGroupCode().toLowerCase(), typeGroup);
		}
	}

	// ----------------------------------------------------------------
	// ----------------------------------------------------------------

	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		Role role = commonDao.get(Role.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(RoleMenuFunction.class);
		cq1.eq("role.id", role.getId());
		cq1.eq("menuFunction.id", functionId);
		cq1.add();
		List<RoleMenuFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			RoleMenuFunction roleMenuFunction = rFunctions.get(0);
			if (null != roleMenuFunction.getOperation()) {
				String[] operationArry = roleMenuFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	public Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		List<RoleUser> rUsers = findByProperty(RoleUser.class, "user.id", userId);
		for (RoleUser ru : rUsers) {
			Role role = ru.getRole();
			CriteriaQuery cq1 = new CriteriaQuery(RoleMenuFunction.class);
			cq1.eq("role.id", role.getId());
			cq1.eq("menuFunction.id", functionId);
			cq1.add();
			List<RoleMenuFunction> roleMenuFunctions = getListByCriteriaQuery(cq1, false);
			if (null != roleMenuFunctions && roleMenuFunctions.size() > 0) {
				RoleMenuFunction roleMenuFunction = roleMenuFunctions.get(0);
				if (null != roleMenuFunction.getOperation()) {
					String[] operationArry = roleMenuFunction.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		}
		return operationCodes;
	}

	// ----------------------------------------------------------------
	// ----------------------------------------------------------------

	public void flushRoleFunciton(String id, MenuFunction newFunction) {
		MenuFunction functionEntity = this.getEntity(MenuFunction.class, id);
		if (functionEntity.getIcon() == null || !StringUtil.isNotEmpty(functionEntity.getIcon().getId())) {
			return;
		}
		Icon oldIcon = this.getEntity(Icon.class, functionEntity.getIcon().getId());
		if (!oldIcon.getIconClass().equals(newFunction.getIcon().getIconClass())) {
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			User user = ResourceUtil.getSessionUserName();
			List<RoleUser> rUsers = this.findByProperty(RoleUser.class, "user.id", user.getId());
			for (RoleUser ru : rUsers) {
				Role role = ru.getRole();
				session.removeAttribute(role.getId());
			}
		}
	}

}
