package com.sbplatform.web.system.pojo.base;

// default package

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * TypeGroup entity.
 */
@Entity
@Table(name = "s_t_type_group")
public class TypeGroup extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 3175436803699351845L;
	
	public static Map<String, TypeGroup> allTypeGroups = new HashMap<String, TypeGroup>();
	public static Map<String, List<Type>> allTypes = new HashMap<String, List<Type>>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "typeGroup")
	private List<Type> subs = new ArrayList<Type>();
	
	@Column(name = "TYPE_GROUP_NAME", length = 50)
	private String typeGroupName;
	
	@Column(name = "TYPE_GROUP_CODE", length = 50)
	private String typeGroupCode;

	public List<Type> getSubs() {
		return subs;
	}

	public void setSubs(List<Type> subs) {
		this.subs = subs;
	}

	public String getTypeGroupName() {
		return typeGroupName;
	}

	public void setTypeGroupName(String typeGroupName) {
		this.typeGroupName = typeGroupName;
	}

	public String getTypeGroupCode() {
		return typeGroupCode;
	}

	public void setTypeGroupCode(String typeGroupCode) {
		this.typeGroupCode = typeGroupCode;
	}



}