package com.sbplatform.web.demo.entity.test;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**   
 * @Title: Entity
 * @Description: WebOffice例子
 * @author 黄世民
 * @date 2013-07-08 10:54:19
 * @version V1.0   
 *
 */
@Entity
@Table(name = "demo_t_doc", schema = "")
@SuppressWarnings("serial")
public class WebOfficeEntity implements java.io.Serializable {
	/**id*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, length = 32)
	private java.lang.Integer id;
	/**docid*/
	@Column(name = "DOCID", nullable = true, length = 255)
	private java.lang.String docid;
	/**doctitle*/
	@Column(name = "DOCTITLE", nullable = true, length = 255)
	private java.lang.String doctitle;
	/**doctype*/
	@Column(name = "DOCTYPE", nullable = true, length = 255)
	private java.lang.String doctype;
	/**docdate*/
	@Column(name = "DOCDATE", nullable = true)
	private java.util.Date docdate;
	/**doccontent*/
	@Column(name = "DOCCONTENT", nullable = true)
	private Blob doccontent;

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	public java.lang.Integer getId() {
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  docid
	 */
	public java.lang.String getDocid() {
		return this.docid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  docid
	 */
	public void setDocid(java.lang.String docid) {
		this.docid = docid;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  doctitle
	 */
	public java.lang.String getDoctitle() {
		return this.doctitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  doctitle
	 */
	public void setDoctitle(java.lang.String doctitle) {
		this.doctitle = doctitle;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  doctype
	 */
	public java.lang.String getDoctype() {
		return this.doctype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  doctype
	 */
	public void setDoctype(java.lang.String doctype) {
		this.doctype = doctype;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  docdate
	 */
	public java.util.Date getDocdate() {
		return this.docdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  docdate
	 */
	public void setDocdate(java.util.Date docdate) {
		this.docdate = docdate;
	}

	/**
	 *方法: 取得Blob
	 *@return: Blob  doccontent
	 */
	public Blob getDoccontent() {
		return this.doccontent;
	}

	/**
	 *方法: 设置Blob
	 *@param: Blob  doccontent
	 */
	public void setDoccontent(Blob doccontent) {
		this.doccontent = doccontent;
	}
}
