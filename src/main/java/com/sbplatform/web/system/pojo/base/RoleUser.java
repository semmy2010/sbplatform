package com.sbplatform.web.system.pojo.base;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * RoleUser entity.
 * @author  黄世民
 */
@Entity
@Table(name = "s_t_role_user")
public class RoleUser extends IdEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = -6590742561864514673L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}