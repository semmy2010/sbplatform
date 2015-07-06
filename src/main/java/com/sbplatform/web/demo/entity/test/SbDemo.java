package com.sbplatform.web.demo.entity.test;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * SbDemo测试表
 * 
 */
@Entity
@Table(name = "demo_t_demo")
@Inheritance(strategy = InheritanceType.JOINED)
public class SbDemo extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**手机号码*/
	@Column(name ="MOBILE_PHONE",nullable=true)
	private String mobilePhone;
	/**办公电话*/
	@Column(name ="OFFICE_PHONE",nullable=true)
	private String officePhone;
	/**电子邮箱*/
	@Column(name ="EMAIL",nullable=true)
	private String email;
	/**age*/
	@Column(name ="AGE",nullable=true)
	private java.lang.Integer age;
	/**工资*/
	@Column(name ="SALARY",nullable=true)
	private BigDecimal salary;
	/**生日*/
	@Column(name ="BIRTHDAY",nullable=true)
	private Date birthday;
	/**创建时间*/
	@Column(name ="create_date",nullable=true)
	private Date createDate;
	/**性别*/
	@Column(name ="SEX",nullable=true)
	private String sex;
	/**部门ID*/
	@Column(name ="DEP_ID",nullable=true)
	private String depId;
	/**用户名*/
	@Column(name ="USER_NAME",nullable=false)
	private String userName;
	/**状态*/
	@Column(name ="status",nullable=true)
	private String status;
	/**备注*/
	private String content;
	

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 *方法: 取得String
	 *@return: String  手机号码
	 */
	public String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置String
	 *@param: String  手机号码
	 */
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	/**
	 *方法: 取得String
	 *@return: String  办公电话
	 */
	public String getOfficePhone(){
		return this.officePhone;
	}

	/**
	 *方法: 设置String
	 *@param: String  办公电话
	 */
	public void setOfficePhone(String officePhone){
		this.officePhone = officePhone;
	}
	/**
	 *方法: 取得String
	 *@return: String  电子邮箱
	 */
	public String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置String
	 *@param: String  电子邮箱
	 */
	public void setEmail(String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  age
	 */
	public java.lang.Integer getAge(){
		return this.age;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  age
	 */
	public void setAge(java.lang.Integer age){
		this.age = age;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  工资
	 */
	public BigDecimal getSalary(){
		return this.salary;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  工资
	 */
	public void setSalary(BigDecimal salary){
		this.salary = salary;
	}
	/**
	 *方法: 取得Date
	 *@return: Date  生日
	 */
	public Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置Date
	 *@param: Date  生日
	 */
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	/**
	 *方法: 取得java.sql.Timestamp
	 *@return: java.sql.Timestamp  创建时间
	 */
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置Date
	 *@param: Date  创建时间
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  性别
	 */
	public String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  性别
	 */
	public void setSex(String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得String
	 *@return: String  部门ID
	 */
	public String getDepId(){
		return this.depId;
	}

	/**
	 *方法: 设置String
	 *@param: String  部门ID
	 */
	public void setDepId(String depId){
		this.depId = depId;
	}
	/**
	 *方法: 取得String
	 *@return: String  用户名
	 */
	public String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置String
	 *@param: String  用户名
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}