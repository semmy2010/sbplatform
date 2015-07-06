package com.sbplatform.web.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * 程序版本控制表
 * @author 黄世民
 *
 */
@Entity
@Table(name = "s_t_version")
public class Version extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -3073879311835119104L;

	@Column(name = "VERSION_NAME", length = 30)
	private String versionName;//版本名称
	
	@Column(name = "VERSION_CODE", length = 50)
	private String versionCode;//版本编码
	
	@Column(name = "LOGIN_PAGE", length = 100)
	private String loginPage;//登陆入口页面
	
	@Column(name = "VERSION_NUM", length = 20)
	private String versionNum;//版本号

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
}
