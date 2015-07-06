package com.sbplatform.web.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * 角色表
 *  @author  黄世民
 */
@Entity
@Table(name = "s_t_role")
public class Role extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 2660358403417306571L;
	//角色名称
	@Column(name = "ROLE_NAME", nullable = false, length = 100)
	private String roleName;
	
	//角色编码
	@Column(name = "ROLE_CODE", length = 10)
	private String roleCode;

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}