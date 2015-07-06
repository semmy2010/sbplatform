package com.sbplatform.web.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 系统用户表
 *  @author  黄世民
 */
@Entity
@Table(name = "s_t_user")
@PrimaryKeyJoinColumn(name = "id")
public class User extends BaseUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	// 签名文件
	@Column(name = "SIGNATURE_FILE", length = 100)
	private String signatureFile;

	// 手机
	@Column(name = "MOBILE_PHONE", length = 30)
	private String mobilePhone;

	// 办公电话
	@Column(name = "OFFICE_PHONE", length = 20)
	private String officePhone;

	// 邮箱
	@Column(name = "EMAIL", length = 50)
	private String email;

	public String getSignatureFile() {
		return this.signatureFile;
	}

	public void setSignatureFile(String signatureFile) {
		this.signatureFile = signatureFile;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}