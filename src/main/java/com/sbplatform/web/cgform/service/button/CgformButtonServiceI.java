package com.sbplatform.web.cgform.service.button;

import java.util.List;

import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.web.cgform.entity.button.CgformButtonEntity;

/**
 * 
 * @author  黄世民
 *
 */
public interface CgformButtonServiceI extends CommonService{
	
	/**
	 * 查询按钮list
	 * @param formId
	 * @return
	 */
	public List<CgformButtonEntity> getCgformButtonListByFormId(String formId);

	/**
	 * 校验按钮唯一性
	 * @param cgformButtonEntity
	 * @return
	 */
	public List<CgformButtonEntity> checkCgformButton(CgformButtonEntity cgformButtonEntity);
	
	
}
