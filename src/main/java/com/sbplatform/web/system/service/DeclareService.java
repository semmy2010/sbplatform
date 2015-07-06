package com.sbplatform.web.system.service;

import java.util.List;

import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.web.system.pojo.base.Attachment;

/**
 * 
 * @author  黄世民
 *
 */
public interface DeclareService extends CommonService{
	
	public List<Attachment> getAttachmentsByCode(String businessKey,String description);
	
}
