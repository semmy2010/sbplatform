package com.sbplatform.web.system.pojo.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * TLog entity.
 *  @author  黄世民
 */
@Entity
@Table(name = "s_t_log")
public class Log extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 2602863241326992668L;

	@Column(name = "LOG_LEVEL")
	private Short logLevel;
	
	@Column(name = "OCCUR_TIME", nullable = false, length = 35)
	private Timestamp occurTime;
	
	@Column(name = "OPERATE_TYPE")
	private Short operateType;
	
	@Column(name = "LOG_CONTENT", nullable = false, length = 2000)
	private String logContent;
	
	@Column(name = "BROSWER", length = 100)
	private String broswer;//用户浏览器类型
	
	@Column(name = "NOTE", length = 300)
	private String note;

	public Short getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Short logLevel) {
		this.logLevel = logLevel;
	}

	public Timestamp getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Timestamp occurTime) {
		this.occurTime = occurTime;
	}

	public Short getOperateType() {
		return operateType;
	}

	public void setOperateType(Short operateType) {
		this.operateType = operateType;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getBroswer() {
		return broswer;
	}

	public void setBroswer(String broswer) {
		this.broswer = broswer;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}