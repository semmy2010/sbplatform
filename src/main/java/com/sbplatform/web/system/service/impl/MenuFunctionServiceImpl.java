package com.sbplatform.web.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import com.sbplatform.core.constant.Globals;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.service.MenuFunctionService;
import com.sbplatform.web.system.service.SystemService;

@Service("menuFunctionService")
@Transactional
public class MenuFunctionServiceImpl extends CommonServiceImpl implements MenuFunctionService {

	@Resource
	private SystemService systemService;

	@Override
	public void deleteMenu(MenuFunction function) {
		String message = "权限: " + function.getName() + "被删除成功";
		List<String> ids = getChildFuns(function.getId());
		StringBuilder sb = new StringBuilder();
		for (String id : ids) {
			sb.append("'");
			sb.append(id);
			sb.append("'");
			sb.append(",");
		}
		sb.append("'");
		sb.append(function.getId());
		sb.append("'");
		// 删除权限与菜单的关联
		this.systemService.updateBySqlString("delete from s_t_role_menu_function where menu_function_id in (" + sb.toString() + ")");
		// 删除菜单下面的关联的按钮
		this.systemService.updateBySqlString("delete from s_t_operation where menu_function_id in (" + sb.toString() + ")");

		if (function.getParent() != null) {
			function.getParent().getSubs().remove(function);
			function.setParent(null);
		}
		this.systemService.delete(function);
		this.systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
	}

	private List<String> getChildFuns(String funId) {
		List<String> ids = new ArrayList<String>();
		String sql = "SELECT id FROM s_t_menu_function WHERE parent_id='" + funId + "'";
		List<String> childs = this.systemService.findListbySql(sql);
		ids.addAll(childs);
		for (String id : childs) {
			ids.addAll(getChildFuns(id));
		}
		return ids;

	}

}
