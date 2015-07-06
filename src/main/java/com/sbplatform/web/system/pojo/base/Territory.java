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

import org.hibernate.annotations.ForeignKey;

import com.sbplatform.core.common.entity.IdEntity;

/**
 *地域管理表
 * @author  黄世民
 */
@Entity
@Table(name = "s_t_territory")
public class Territory extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	private List<Territory> subs = new ArrayList<Territory>();
	
	//父地域
	//取消hibernate的外键生成
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	@ForeignKey(name = "null")
	private Territory parent;
	
	//地域名称
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;
	
	//等级
	@Column(name = "LEVEL", nullable = false, length = 1)
	private Short level;
	
	//同区域中的显示顺序
	@Column(name = "SORT", nullable = false, length = 3)
	private String sort;
	
	//区域码
	@Column(name = "code", nullable = false, length = 10)
	private String code;
	
	//区域名称拼音
	@Column(name = "pinyin", nullable = true, length = 40)
	private String pinyin;
	
	//wgs84格式经度(mapabc 的坐标系)
	@Column(name = "X_WGS84", nullable = false, length = 40)
	private double xwgs84;
	
	//wgs84格式纬度(mapabc 的坐标系)
	@Column(name = "Y_WGS84", nullable = false, length = 40)
	private double ywgs84;

	public List<Territory> getSubs() {
		return subs;
	}

	public void setSubs(List<Territory> subs) {
		this.subs = subs;
	}

	public Territory getParent() {
		return parent;
	}

	public void setParent(Territory parent) {
		this.parent = parent;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public double getXwgs84() {
		return xwgs84;
	}

	public void setXwgs84(double xwgs84) {
		this.xwgs84 = xwgs84;
	}

	public double getYwgs84() {
		return ywgs84;
	}

	public void setYwgs84(double ywgs84) {
		this.ywgs84 = ywgs84;
	}

}