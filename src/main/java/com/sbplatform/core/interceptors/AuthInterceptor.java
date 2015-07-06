package com.sbplatform.core.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.sbplatform.core.util.ContextHolderUtils;
import com.sbplatform.core.util.ResourceUtil;
import com.sbplatform.core.util.oConvertUtils;
import com.sbplatform.web.system.manager.ClientManager;
import com.sbplatform.web.system.pojo.base.Client;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.service.SystemService;


/**
 * 权限拦截器
 * 
 * @author  黄世民
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {
	 
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
	private SystemService systemService;
	private List<String> excludeUrls;
	private static List<MenuFunction> functionList;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if(client == null){ 
			client = ClientManager.getInstance().getClient(
					request.getParameter("sessionId"));
		}
		if (excludeUrls.contains(requestPath)) {
			return true;
		} else {
			if (client != null && client.getUser()!=null ) {
				 if(!hasMenuAuth(request)){
					 response.sendRedirect("loginController.do?noAuth");
					//request.getRequestDispatcher("webpage/common/noAuth.jsp").forward(request, response);
					return false;
				} 
				String functionId=oConvertUtils.getString(request.getParameter("clickFunctionId"));
				if(!oConvertUtils.isEmpty(functionId)){
					Set<String> operationCodes = systemService.getOperationCodesByUserIdAndFunctionId(client.getUser().getId(), functionId);
					request.setAttribute("operationCodes", operationCodes);
				 
				}
				if(!oConvertUtils.isEmpty(functionId)){
					List<String> allOperation=this.systemService.findListbySql("SELECT CODE FROM s_t_operation  WHERE MENU_FUNCTION_ID='"+functionId+"'"); 
					  
					List<String> newall = new ArrayList<String>();
					if(allOperation.size()>0){
						for(String s:allOperation){ 
						    s=s.replaceAll(" ", "");
							newall.add(s); 
						}						 
						String hasOperSql="SELECT OPERATION FROM s_t_role_menu_function fun, s_t_role_user role WHERE  " +
							"fun.MENU_FUNCTION_ID='"+functionId+"' AND fun.OPERATION!=''  AND fun.ROLE_ID=role.ROLE_ID AND role.USER_ID='"+client.getUser().getId()+"' ";
						List<String> hasOperList = this.systemService.findListbySql(hasOperSql); 
					    for(String strs:hasOperList){
							    for(String s:strs.split(",")){
							        s=s.replaceAll(" ", "");
							    	newall.remove(s);
							    } 
						} 
					}
					 request.setAttribute("noauto_operationCodes", newall);
				}
				return true;
			} else {
				//forword(request);
				forward(request, response);
				return false;
			}

		}
	}
	private boolean hasMenuAuth(HttpServletRequest request){
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		// 是否是功能表中管理的url
		boolean bMgrUrl = false;
		if (functionList == null) {
			functionList = systemService.loadAll(MenuFunction.class);
		}
		for (MenuFunction function : functionList) {
			if (function.getUrl() != null && function.getUrl().startsWith(requestPath)) {
				bMgrUrl = true;
				break;
			}
		}
		if (!bMgrUrl) {
			return true;
		}
		 
		String funcid=oConvertUtils.getString(request.getParameter("clickFunctionId"));
		if(!bMgrUrl && (requestPath.indexOf("loginController.do")!=-1||funcid.length()==0)){
			return true;
		} 
		String userid = ClientManager.getInstance().getClient(
				ContextHolderUtils.getSession().getId()).getUser().getId();
		//requestPath=requestPath.substring(0, requestPath.indexOf("?")+1);
		String sql = "SELECT DISTINCT f.ID FROM s_t_menu_function f,s_t_role_menu_function  rf,s_t_role_user ru " +
					" WHERE f.ID=rf.MENU_FUNCTION_ID AND rf.ROLE_ID=ru.ROLE_ID AND " +
					"ru.USER_ID='"+userid+"' AND f.URL LIKE '"+requestPath+"%'"; 
		List list = this.systemService.findListbySql(sql);
		if(list.size()==0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 转发
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "forword")
	public ModelAndView forword(HttpServletRequest request) {
		return new ModelAndView(new RedirectView("loginController.do?login"));
	}

	private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("webpage/login/timeout.jsp").forward(request, response);
	}

}
