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
 *菜单权限表
 * @author  黄世民
 */
@Entity
@Table(name = "s_t_menu_function")
public class MenuFunction extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 6397929475734532962L;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	private List<MenuFunction> subs = new ArrayList<MenuFunction>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private MenuFunction parent;//父菜单
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ICON_ID")
	private Icon icon = new Icon();//菜单图标
	
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;//菜单名称
	
	@Column(name = "LEVEL")
	private Short level;//菜单等级
	@Column(name = "URL", length = 100)
	private String url;//菜单地址
	//菜单地址打开方式
	@Column(name = "IFRAME")
	private Short iframe;
	@Column(name = "SORT")
	private String sort;//菜单排序
	
	public List<MenuFunction> getSubs() {
		return subs;
	}
	public void setSubs(List<MenuFunction> subs) {
		this.subs = subs;
	}
	public MenuFunction getParent() {
		return parent;
	}
	public void setParent(MenuFunction parent) {
		this.parent = parent;
	}
	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getLevel() {
		return level;
	}
	public void setLevel(Short level) {
		this.level = level;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Short getIframe() {
		return iframe;
	}
	public void setIframe(Short iframe) {
		this.iframe = iframe;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

}