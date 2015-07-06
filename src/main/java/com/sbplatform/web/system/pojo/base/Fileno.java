package com.sbplatform.web.system.pojo.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sbplatform.core.common.entity.IdEntity;

/**
 *  @author  黄世民
 */
@Entity
@Table(name = "s_t_fileno")
public class Fileno extends IdEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 1459742657480133564L;

	@Column(name = "FILENO_BEFORE", length = 32)
	private String filenoBefore;
	
	@Column(name = "FILENO_NUM")
	private int filenoNum;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fileno_Year", length = 13)
	private Date filenoYear;
	
	@Column(name = "filenotype", length = 32)
	private String filenoType;

	public String getFilenoBefore() {
		return filenoBefore;
	}

	public void setFilenoBefore(String filenoBefore) {
		this.filenoBefore = filenoBefore;
	}

	public int getFilenoNum() {
		return filenoNum;
	}

	public void setFilenoNum(int filenoNum) {
		this.filenoNum = filenoNum;
	}

	public String getFilenoType() {
		return filenoType;
	}

	public void setFilenoType(String filenoType) {
		this.filenoType = filenoType;
	}

	public Date getFilenoYear() {
		return filenoYear;
	}

	public void setFilenoYear(Date filenoYear) {
		this.filenoYear = filenoYear;
	}

}