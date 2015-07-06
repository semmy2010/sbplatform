package com.sbplatform.web.system.pojo.base;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * TConfig entity.
 * 系统配置类
 * @author  黄世民
 */
@Entity
@Table(name = "s_t_config")
public class Config extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5197403927810558210L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "CODE", length = 100)
	private String code;

	@Column(name = "NAME", nullable = false, length = 100)
	private String name;

	@Column(name = "CONTENT", length = 300)
	private String contents;

	@Column(name = "NOTE", length = 300)
	private String note;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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

}