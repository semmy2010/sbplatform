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
 * 通用类型字典表
 *  @author  黄世民
 */
@Entity
@Table(name = "s_t_type")
public class Type extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 5272347490189445164L;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	private List<Type> subs = new ArrayList<Type>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPE_GROUP_ID")
	private TypeGroup typeGroup;//类型分组

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Type parent;//父类型
	
	@Column(name = "NAME", length = 50)
	private String name;//类型名称
	
	@Column(name = "CODE", length = 50)
	private String code;//类型编码

	public List<Type> getSubs() {
		return subs;
	}

	public void setSubs(List<Type> subs) {
		this.subs = subs;
	}

	public TypeGroup getTypeGroup() {
		return typeGroup;
	}

	public void setTypeGroup(TypeGroup typeGroup) {
		this.typeGroup = typeGroup;
	}

	public Type getParent() {
		return parent;
	}

	public void setParent(Type parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}