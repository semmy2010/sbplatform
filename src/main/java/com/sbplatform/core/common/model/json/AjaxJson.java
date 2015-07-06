package com.sbplatform.core.common.model.json;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sbplatform.common.Constant;

/**
 * $.ajax后需要接受的JSON
 * 
 * @author
 * 
 */
public class AjaxJson {

	/**
	 *  是否成功
	 * 0表示成功，其他为失败
	 */
	private int status = Constant.CommonConstant.STATUS_SUCCESS;
	private String message = "操作成功";// 提示信息
	@JsonInclude(Include.NON_NULL)
	private Object data = null;// 其他信息
	@JsonInclude(Include.NON_NULL)
	private Map<String, Object> expandData;// 其他参数

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getExpandData() {
		return expandData;
	}

	public void setExpandData(Map<String, Object> expandData) {
		this.expandData = expandData;
	}
}
