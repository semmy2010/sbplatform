package com.sbplatform.web.demo.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import com.sbplatform.web.demo.service.test.SbMatterBomServiceI;

/**
 * <li>类型名称：
 * <li>说明：物料Bom业务接口实现类
 * <li>创建人： 温俊
 * <li>创建日期：2013-8-12
 * <li>修改人： 
 * <li>修改日期：
 */
@Service("sbMatterBomService")
@Transactional
public class SbMatterBomServiceImpl extends CommonServiceImpl implements
		SbMatterBomServiceI {

}
