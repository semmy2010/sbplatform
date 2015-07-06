package com.sbplatform.web.system.pojo.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * 部门机构表
 * @author  黄世民
 */
@Entity
@Table(name = "s_t_department")
public class Department extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -5086876167339315107L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Department parent;//上级部门
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	private List<Department> subs = new ArrayList<Department>();//下属部门
	
	@Column(name = "NAME", nullable = false, length = 100)
	private String name;//部门名称
	
	@Column(name = "DESCRIPTION", length = 500)
	private String description;//部门描述

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public List<Department> getSubs() {
		return subs;
	}

	public void setSubs(List<Department> subs) {
		this.subs = subs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}