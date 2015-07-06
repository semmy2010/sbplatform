package com.sbplatform.web.demo.entity.test;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OptimisticLock;

import javax.persistence.SequenceGenerator;

/**
 * @Title: Entity
 * @Description: 乐观锁测试
 * @author 黄世民
 * @date 2013-06-24 14:46:42
 * @version V1.0
 * 
 */
@Entity
@Table(name = "demo_t_optimistic_locking", schema = "")
@SuppressWarnings("serial")
public class OptimisticLockingEntity implements java.io.Serializable {
	/** id */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	private java.lang.String id;
	/** name */
	@Column(name = "NAME", nullable = true, length = 85)
	private java.lang.String name;
	/** age */
	@Column(name = "AGE", nullable = true, precision = 10, scale = 0)
	private java.lang.Integer age;
	/** account */
	@Column(name = "ACCOUNT", nullable = true, precision = 10, scale = 0)
	private java.lang.Integer account;
	/** ver */
	@Version
	@Column(name = "VER")
	private java.lang.Integer ver;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String id
	 */
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String name
	 */
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer age
	 */
	public java.lang.Integer getAge() {
		return this.age;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer age
	 */
	public void setAge(java.lang.Integer age) {
		this.age = age;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer account
	 */
	public java.lang.Integer getAccount() {
		return this.account;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer account
	 */
	public void setAccount(java.lang.Integer account) {
		this.account = account;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer ver
	 */
	public java.lang.Integer getVer() {
		return this.ver;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer ver
	 */
	public void setVer(java.lang.Integer ver) {
		this.ver = ver;
	}
}
