package com.sbplatform.web.demo.service.test;

import org.springframework.web.multipart.MultipartFile;

import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.web.demo.entity.test.WebOfficeEntity;

public interface WebOfficeServiceI extends CommonService{
	public void saveObj(WebOfficeEntity docObj, MultipartFile file);
}
