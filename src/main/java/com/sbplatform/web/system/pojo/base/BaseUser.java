package com.sbplatform.web.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbplatform.core.common.entity.IdEntity;

/**
 * 系统用户父类表
 * @author  黄世民
 */
@MappedSuperclass
public class BaseUser extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	//getList查询转换为列表时处理json转换异常
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department = new Department();// 部门

	// 用户名
	@Column(name = "USER_NAME", nullable = false, length = 10)
	private String userName;
	// 真实姓名
	@Column(name = "REAL_NAME", length = 50)
	private String realName;
	// 用户使用浏览器类型
	@Column(name = "BROWSER", length = 20)
	private String browser;
	// 用户验证唯一标示
	@Column(name = "USER_KEY", length = 200)
	private String userKey;
	//用户密码
	@Column(name = "PASSWORD", length = 100)
	private String password;
	//是否同步工作流引擎
	@Column(name = "ACTIVITI_SYNC")
	private Short activitiSync;
	// 状态1：在线,2：离线,0：禁用
	@Column(name = "STATUS")
	private Short status;
	// 签名文件
	@Column(name = "SIGNATURE", length = 3000)
	private byte[] signature;

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getActivitiSync() {
		return activitiSync;
	}

	public void setActivitiSync(Short activitiSync) {
		this.activitiSync = activitiSync;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}