package com.sbplatform.web.demo.entity.test;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: HTML 编辑器
 * @author 黄世民
 * @date 2013-07-08 16:19:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "demo_t_ck_editor", schema = "")
@SuppressWarnings("serial")
public class CKEditorEntity implements java.io.Serializable {
	/**id*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	private java.lang.String id;
	/**contents*/
	@Column(name ="CONTENTS",nullable=true,length=65535)
	private byte[] contents;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得byte[]
	 *@return: byte[]  contents
	 */
	public byte[] getContents(){
		return this.contents;
	}

	/**
	 *方法: 设置byte[]
	 *@param: byte[]  contents
	 */
	public void setContents(byte[] contents){
		this.contents = contents;
	}
}
