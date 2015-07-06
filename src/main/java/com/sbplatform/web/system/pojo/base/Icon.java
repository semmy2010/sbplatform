package com.sbplatform.web.system.pojo.base;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * TIcon entity.
 *  @author  黄世民
 */
@Entity
@Table(name = "s_t_icon")
public class Icon extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -6008555466218762776L;
	@Column(name = "ICON_NAME", nullable = false, length = 100)
	private String iconName;
	
	@Column(name = "ICON_TYPE")
	private Short iconType;
	
	@Column(name = "ICON_PATH", length = 300, precision = 300)
	private String iconPath;
	
	@Column(name = "ICON_CONTENT", length = 1000, precision = 3000)
	private byte[] iconContent;
	
	@Column(name = "ICON_CLASS", length = 200)
	private String iconClass;
	
	@Column(name = "EXTEND_NAME")
	private String extendName;

	public String getIconName() {
		return this.iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public Short getIconType() {
		return this.iconType;
	}

	public void setIconType(Short iconType) {
		this.iconType = iconType;
	}

	public String getIconPath() {
		return this.iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public void setIconContent(byte[] iconContent) {
		this.iconContent = iconContent;
	}

	public byte[] getIconContent() {
		return iconContent;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getExtendName() {
		return extendName;
	}

	public void setExtendName(String extendName) {
		this.extendName = extendName;
	}

}