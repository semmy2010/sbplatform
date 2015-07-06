package com.sbplatform.web.demo.service.impl.test;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.sbplatform.web.demo.service.test.TaskDemoServiceI;
@Service("taskDemoService")
public class TaskDemoServiceImpl implements TaskDemoServiceI {

	
	public void work() {
		com.sbplatform.core.util.LogUtil.info(new Date().getTime());
		com.sbplatform.core.util.LogUtil.info("----------任务测试-------");
	}

}
