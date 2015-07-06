package com.sbplatform.web.system.pojo.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * @author  黄世民
 * 项目附件父表(其他附件表需继承该表)
 */
@Entity
@Table(name = "s_t_attachment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Attachment extends IdEntity implements java.io.Serializable {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = 1L;
	// 创建人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	// 业务类主键
	@Column(name = "BUSINESS_KEY", length = 32)
	private String businessKey;
	// 子类名称全路径
	@Column(name = "SUB_CLASS_NAME", length = 300)
	private String subClassName;
	// 附件名称
	@Column(name = "ATTACHMENT_NAME", length = 100)
	private String attachmentName;
	// 附件内容
	@Lob
	@Column(name = "ATTACHMENT_CONTENT", length = 3000)
	private byte[] attachmentContent;
	// 附件物理路径
	@Column(name = "REAL_PATH", length = 100)
	private String realPath;
	@Column(name = "CREATE_DATE", length = 35)
	private Timestamp createDate;
	@Column(name = "NOTE", length = 300)
	private String note;
	// swf格式路径
	@Column(name = "SWF_PATH", length = 300)
	private String swfPath;
	// 扩展名
	@Column(name = "EXTEND_NAME", length = 32)
	private String extendName;

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSubClassName() {
		return subClassName;
	}

	public void setSubClassName(String subClassName) {
		this.subClassName = subClassName;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public byte[] getAttachmentContent() {
		return attachmentContent;
	}

	public void setAttachmentContent(byte[] attachmentContent) {
		this.attachmentContent = attachmentContent;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getSwfPath() {
		return swfPath;
	}

	public void setSwfPath(String swfPath) {
		this.swfPath = swfPath;
	}

	public String getExtendName() {
		return extendName;
	}

	public void setExtendName(String extendName) {
		this.extendName = extendName;
	}

}