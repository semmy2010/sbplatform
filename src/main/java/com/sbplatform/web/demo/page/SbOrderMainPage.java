package com.sbplatform.web.demo.page;

import java.util.ArrayList;
import java.util.List;

import com.sbplatform.web.demo.entity.test.SbOrderCustomEntity;
import com.sbplatform.web.demo.entity.test.SbOrderProductEntity;

/**   
 * @Title: Entity
 * @Description: 订单信息 VO
 * @author 黄世民
 * @date 2013-03-19 22:01:34
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class SbOrderMainPage implements java.io.Serializable {
	/**订单客户明细*/
	private List<SbOrderCustomEntity> sbOrderCustomList = new ArrayList<SbOrderCustomEntity>();
	public List<SbOrderCustomEntity> getSbOrderCustomList() {
		return sbOrderCustomList;
	}
	public void setSbOrderCustomList(List<SbOrderCustomEntity> sbOrderCustomList) {
		this.sbOrderCustomList = sbOrderCustomList;
	}
	/**订单产品明细*/
	private List<SbOrderProductEntity> sbOrderProductList = new ArrayList<SbOrderProductEntity>();
	public List<SbOrderProductEntity> getSbOrderProductList() {
		return sbOrderProductList;
	}
	public void setSbOrderProductList(List<SbOrderProductEntity> sbOrderProductList) {
		this.sbOrderProductList = sbOrderProductList;
	}
}
