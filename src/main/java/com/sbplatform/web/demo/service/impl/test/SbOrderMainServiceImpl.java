package com.sbplatform.web.demo.service.impl.test;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import com.sbplatform.web.demo.entity.test.SbOrderCustomEntity;
import com.sbplatform.web.demo.entity.test.SbOrderMainEntity;
import com.sbplatform.web.demo.entity.test.SbOrderProductEntity;
import com.sbplatform.web.demo.service.test.SbOrderMainServiceI;

@Service("sbOrderMainService")
@Transactional
public class SbOrderMainServiceImpl extends CommonServiceImpl implements SbOrderMainServiceI {

	
	public void addMain(SbOrderMainEntity sbOrderMain,
			List<SbOrderProductEntity> sbOrderProducList,
			List<SbOrderCustomEntity> sbOrderCustomList){
		//保存订单主信息
		this.save(sbOrderMain);
		//保存订单产品明细
		for(SbOrderProductEntity product:sbOrderProducList){
			//外键设置
			product.setGoOrderCode(sbOrderMain.getGoOrderCode());
			this.save(product);
		}
		//保存订单客户明细
		for(SbOrderCustomEntity custom:sbOrderCustomList){
			//外键设置
			custom.setGoOrderCode(sbOrderMain.getGoOrderCode());
			this.save(custom);
		}
	}

	
	public void updateMain(SbOrderMainEntity sbOrderMain,
			List<SbOrderProductEntity> sbOrderProducList,
			List<SbOrderCustomEntity> sbOrderCustomList,
			boolean sbOrderCustomShow) {
		//保存订单主信息
		this.saveOrUpdate(sbOrderMain);
		//删除订单产品明细
		this.commonDao.deleteAllEntitie(this.findByProperty(SbOrderProductEntity.class, "goOrderCode", sbOrderMain.getGoOrderCode()));
		//保存订单产品明细
		for(SbOrderProductEntity product:sbOrderProducList){
			//外键设置
			product.setGoOrderCode(sbOrderMain.getGoOrderCode());
			this.save(product);
		}
		if(sbOrderCustomShow){
			//删除订单客户明细
			this.commonDao.deleteAllEntitie(this.findByProperty(SbOrderCustomEntity.class, "goOrderCode", sbOrderMain.getGoOrderCode()));
			//保存订单客户明细
			for(SbOrderCustomEntity custom:sbOrderCustomList){
				//外键设置
				custom.setGoOrderCode(sbOrderMain.getGoOrderCode());
				this.save(custom);
			}
		}
	}

	
	public void delMain(SbOrderMainEntity sbOrderMain) {
		//删除主表信息
		this.delete(sbOrderMain);
		//删除订单产品明细
		this.deleteAllEntitie(this.findByProperty(SbOrderProductEntity.class, "goOrderCode", sbOrderMain.getGoOrderCode()));
		//删除订单客户明细
		this.commonDao.deleteAllEntitie(this.findByProperty(SbOrderCustomEntity.class, "goOrderCode", sbOrderMain.getGoOrderCode()));
	}
}