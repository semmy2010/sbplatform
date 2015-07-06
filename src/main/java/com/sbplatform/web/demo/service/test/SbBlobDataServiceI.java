package com.sbplatform.web.demo.service.test;

import org.springframework.web.multipart.MultipartFile;

import com.sbplatform.core.common.service.CommonService;

public interface SbBlobDataServiceI extends CommonService{
	public void saveObj(String documentTitle, MultipartFile file);

}
