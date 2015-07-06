package com.sbplatform.web.system.pojo.base;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sbplatform.core.common.entity.IdEntity;

/**
 * OpinTemplate entity.
 *  @author  黄世民
 */
@Entity
@Table(name = "s_t_opin_template")
public class OpinTemplate extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 3125450032158755558L;
	@Column(name = "DESCRIPT", length = 100)
	private String descript;

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

}