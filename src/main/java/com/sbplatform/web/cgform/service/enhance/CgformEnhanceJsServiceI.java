package com.sbplatform.web.cgform.service.enhance;

import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.web.cgform.entity.enhance.CgformEnhanceJsEntity;

public interface CgformEnhanceJsServiceI extends CommonService{

	/**
	 * 根据cgJsType和formId查找数据
	 * @param cgJsType
	 * @param formId
	 * @return
	 */
	public CgformEnhanceJsEntity getCgformEnhanceJsByTypeFormId(String cgJsType,String formId);
}
