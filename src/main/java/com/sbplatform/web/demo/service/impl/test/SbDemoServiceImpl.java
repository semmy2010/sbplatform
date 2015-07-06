package com.sbplatform.web.demo.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import com.sbplatform.web.demo.service.test.SbDemoServiceI;


@Service("sbDemoService")
@Transactional
public class SbDemoServiceImpl extends CommonServiceImpl implements SbDemoServiceI {
	
}
