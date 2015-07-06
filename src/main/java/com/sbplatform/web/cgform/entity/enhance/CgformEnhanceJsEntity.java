package com.sbplatform.web.cgform.entity.enhance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: JS增强
 * @author 黄世民
 * @date 2013-08-11 09:47:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "cgform_t_enhance_js", schema = "")
@SuppressWarnings("serial")
public class CgformEnhanceJsEntity implements java.io.Serializable {
	/**id*/
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	private java.lang.String id;
	/**formId*/
	@Column(name ="FORM_ID",nullable=true,length=32)
	private java.lang.String formId;
	/**js增强类型（form/list）*/
	@Column(name ="CG_JS_TYPE",nullable=true,length=20)
	private java.lang.String cgJsType;
	/**增强js*/
	@Column(name ="CG_JS",nullable=true,length=65535)
	private byte[] cgJs;
	
	/**增强js Str*/
	@Transient
	private String cgJsStr;
	
	/**描述*/
	@Column(name ="CONTENT",nullable=true,length=1000)
	private java.lang.String content;
	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  formId
	 */
	public java.lang.String getFormId(){
		return this.formId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  formId
	 */
	public void setFormId(java.lang.String formId){
		this.formId = formId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  js增强类型（form/list）
	 */
	public java.lang.String getCgJsType(){
		return this.cgJsType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  js增强类型（form/list）
	 */
	public void setCgJsType(java.lang.String cgJsType){
		this.cgJsType = cgJsType;
	}
	/**
	 *方法: 取得byte[]
	 *@return: byte[]  增强js
	 */
	public byte[] getCgJs(){
		return this.cgJs;
	}

	/**
	 *方法: 设置byte[]
	 *@param: byte[]  增强js
	 */
	public void setCgJs(byte[] cgJs){
		this.cgJs = cgJs;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}

	public String getCgJsStr() {
		if(cgJs!=null){
			cgJsStr = new String(cgJs);
		}
		return cgJsStr;
	}

	public void setCgJsStr(String cgJsStr) {
		this.cgJsStr = cgJsStr;
		if(cgJsStr!=null){
		this.cgJs = cgJsStr.getBytes();
		}
	}
	
	/**
	 * 深度复制
	 * @return
	 * @throws Exception
	 */
	public CgformEnhanceJsEntity deepCopy() throws Exception{  
        //将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        ObjectOutputStream oos = new ObjectOutputStream(bos);  
        oos.writeObject(this);  
  
        //将流序列化成对象  
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());  
        ObjectInputStream ois = new ObjectInputStream(bis);  
        return (CgformEnhanceJsEntity) ois.readObject();  
    } 
}
