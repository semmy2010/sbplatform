package com.sbplatform.web.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * RoleMenuFunction entity. 
 *  @author  黄世民
 */
@Entity
@Table(name = "s_t_role_menu_function")
public class RoleMenuFunction extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5461619139333126644L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_FUNCTION_ID")
	private MenuFunction menuFunction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	@Column(name = "OPERATION", length = 100)
	private String operation;

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public MenuFunction getMenuFunction() {
		return menuFunction;
	}

	public void setMenuFunction(MenuFunction menuFunction) {
		this.menuFunction = menuFunction;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}