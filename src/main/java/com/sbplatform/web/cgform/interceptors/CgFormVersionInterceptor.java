package com.sbplatform.web.cgform.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sbplatform.core.interceptors.AuthInterceptor;
import com.sbplatform.core.util.ResourceUtil;
import com.sbplatform.core.util.StringUtil;
import com.sbplatform.web.cgform.service.config.CgFormFieldServiceI;

/**
 * 
 * @author 黄世民
 *
 */
public class CgFormVersionInterceptor  implements HandlerInterceptor {
	private List<String> includeUrls;
	
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		String requestPath = ResourceUtil.getRequestPath(arg0);// 用户访问的资源地址
		if(!includeUrls.contains(requestPath)){
			return;
		}
		//获取表单ID
		String formId = arg0.getParameter("formId");
		if(StringUtil.isEmpty(formId)){
			return;
		}else{
			try{
				cgFormFieldService.updateVersion(formId);
			}catch (Exception e) {
				e.printStackTrace();
				logger.debug(e.getMessage());
			}
		}
	}

	
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		return true;
	}

	public List<String> getIncludeUrls() {
		return includeUrls;
	}

	public void setIncludeUrls(List<String> includeUrls) {
		this.includeUrls = includeUrls;
	}

}
