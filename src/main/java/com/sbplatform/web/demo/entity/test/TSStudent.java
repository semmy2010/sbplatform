package com.sbplatform.web.demo.entity.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

@Entity
@Table(name = "demo_t_student")
public class TSStudent extends IdEntity implements java.io.Serializable {
	@Column(name = "name")
	private String name;
	@Column(name = "sex")
	private String sex;
	@Column(name = "classname")
	private String className;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
