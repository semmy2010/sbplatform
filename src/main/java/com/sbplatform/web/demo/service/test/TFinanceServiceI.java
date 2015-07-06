package com.sbplatform.web.demo.service.test;

import com.sbplatform.core.common.service.CommonService;
import com.sbplatform.web.demo.entity.test.TFinanceEntity;
import com.sbplatform.web.demo.entity.test.TFinanceFilesEntity;

public interface TFinanceServiceI extends CommonService{

	void deleteFile(TFinanceFilesEntity file);

	void deleteFinance(TFinanceEntity finance);

}
