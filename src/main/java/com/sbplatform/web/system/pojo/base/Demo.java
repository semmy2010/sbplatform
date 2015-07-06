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
 * 例子演示
 * @author  黄世民
 */
@Entity
@Table(name = "s_t_demo")
public class Demo extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -6374003827871647855L;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	private List<Demo> subs = new ArrayList<Demo>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Demo parent;//上级
	
	@Column(name = "DEMO_TITLE", length = 200)
	private String demoTitle;//DEMO标题
	
	@Column(name = "DEMO_URL", length = 200)
	private String demoUrl;//DEMO地址
	
	@Column(name = "DEMO_ORDER")
	private Short demoOrder;//DEMO排序
	
	@Column(name = "DEMO_CODE", length = 8000)
	private String demoCode;//例子代码


	public String getDemoTitle() {
		return demoTitle;
	}

	public void setDemoTitle(String demoTitle) {
		this.demoTitle = demoTitle;
	}

	public String getDemoUrl() {
		return demoUrl;
	}

	public void setDemoUrl(String demoUrl) {
		this.demoUrl = demoUrl;
	}

	public Short getDemoOrder() {
		return demoOrder;
	}

	public void setDemoOrder(Short demoOrder) {
		this.demoOrder = demoOrder;
	}

	public String getDemoCode() {
		return demoCode;
	}

	public void setDemoCode(String demoCode) {
		this.demoCode = demoCode;
	}

	public List<Demo> getSubs() {
		return subs;
	}

	public void setSubs(List<Demo> subs) {
		this.subs = subs;
	}

	public Demo getParent() {
		return parent;
	}

	public void setParent(Demo parent) {
		this.parent = parent;
	}

}