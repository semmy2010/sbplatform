package com.sbplatform.tag.core.easyui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sbplatform.core.util.ListtoMenu;
import com.sbplatform.web.system.pojo.base.MenuFunction;


/**
 * 
 * 类描述：菜单标签
 * 
 * 黄世民
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String style="easyui";//菜单样式
	protected List<MenuFunction> parentFun;//一级菜单
	protected List<MenuFunction> childFun;//二级菜单
	protected Map<Integer, List<MenuFunction>> menuFun;//菜单Map
	
	
	public void setParentFun(List<MenuFunction> parentFun) {
		this.parentFun = parentFun;
	}

	public void setChildFun(List<MenuFunction> childFun) {
		this.childFun = childFun;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {	
		StringBuffer sb = new StringBuffer();
		if(style.equals("easyui"))
		{	sb.append("<div id=\"nav\" style=\"display:none;\" class=\"easyui-accordion\" fit=\"true\" border=\"false\">");
			sb.append(ListtoMenu.getEasyuiMultistageTree(menuFun));
			sb.append("</div>");
		}
		if(style.equals("bootstrap"))
		{
			sb.append(ListtoMenu.getBootMenu(parentFun, childFun));
		}
		if(style.equals("json"))
		{
			sb.append("<script type=\"text/javascript\">");
			sb.append("var _menus="+ListtoMenu.getMenu(parentFun, childFun));
			sb.append("</script>");
		}
		if(style.equals("june_bootstrap"))
		{
			sb.append(ListtoMenu.getBootstrapMenu(menuFun));
		}
		return sb;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public void setMenuFun(Map<Integer, List<MenuFunction>> menuFun) {
		this.menuFun = menuFun;
	}

	

}
