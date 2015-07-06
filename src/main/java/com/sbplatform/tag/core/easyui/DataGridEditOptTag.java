package com.sbplatform.tag.core.easyui;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 类描述：列表删除操作项标签
 * 
 * @author 黄世民
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class DataGridEditOptTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2465769956717720054L;
	protected String url;
	protected String title;
	private String message;// 询问链接的提示语
	private String exp;// 判断链接是否显示的表达式
	private String funname;// 自定义函数名称

	private String operationCode;// 按钮的操作Code

	@Override
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	@Override
	public int doEndTag() throws JspTagException {
		Tag t = findAncestorWithClass(this, DataGridTag.class);
		DataGridTag parent = (DataGridTag) t;
		parent.setEditUrl(this.url, this.title, this.message, this.exp, this.funname, this.operationCode);
		return EVAL_PAGE;
	}

	public void setFunname(String funname) {
		this.funname = funname;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
}
