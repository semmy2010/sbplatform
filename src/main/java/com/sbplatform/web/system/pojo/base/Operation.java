package com.sbplatform.web.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * 权限操作表
 *  @author  黄世民
 */
@Entity
@Table(name = "s_t_operation")
public class Operation extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3084049465665469259L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ICON_ID")
	private Icon icon = new Icon();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_FUNCTION_ID")
	private MenuFunction menuFunction = new MenuFunction();
	
	@Column(name = "NAME", length = 50)
	private String name;
	
	@Column(name = "CODE", length = 50)
	private String code;
	
	@Column(name = "STATUS")
	private Short status;

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public MenuFunction getMenuFunction() {
		return menuFunction;
	}

	public void setMenuFunction(MenuFunction menuFunction) {
		this.menuFunction = menuFunction;
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}