package com.sbplatform.web.system.pojo.base;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 文档下载,新闻,法规表
 * @author  黄世民
 */
@Entity
@Table(name = "s_t_document")
@PrimaryKeyJoinColumn(name = "id")
public class Document extends Attachment implements java.io.Serializable {
	private static final long serialVersionUID = -2075480823667663444L;
	
	//文档分类
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPE_ID")
	private Type type;
	
	//文档标题
	@Column(name = "DOCUMENT_TITLE", length = 100)
	private String documentTitle;
	
	//焦点图导航
	@Column(name = "PICTURE_INDEX", length = 3000)
	private byte[] pictureIndex;
	
	//状态：0未发布，1已发布
	@Column(name = "DOCUMENT_STATE")
	private Short documentState;
	
	//是否首页显示
	@Column(name = "SHOW_HOME")
	private Short showHome;

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public byte[] getPictureIndex() {
		return pictureIndex;
	}

	public void setPictureIndex(byte[] pictureIndex) {
		this.pictureIndex = pictureIndex;
	}

	public Short getDocumentState() {
		return documentState;
	}

	public void setDocumentState(Short documentState) {
		this.documentState = documentState;
	}

	public Short getShowHome() {
		return showHome;
	}

	public void setShowHome(Short showHome) {
		this.showHome = showHome;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}