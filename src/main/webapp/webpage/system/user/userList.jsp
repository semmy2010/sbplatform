<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',title:'查找',split:false" style="height: 110px;" id="userListSearchBar"></div>
	<div data-options="region:'center',border:false">
		<t:datagrid name="userList" title="用户管理" actionUrl="userController.do?datagrid" fit="true" fitColumns="true" idField="id" queryMode="group">
			<t:dgCol title="编号" field="id" hidden="true" width="100" />
			<t:dgCol title="用户名" sortable="false" field="userName" query="true" width="100" />
			<t:dgCol title="部门" field="depart_id" query="true" replace="${departsReplace}" width="100" />
			<t:dgCol title="真实姓名" field="realName" query="true" width="100" />
			<t:dgCol title="状态" sortable="true" field="status" replace="正常_1,禁用_0,超级管理员_-1" width="100" />
			<t:dgCol title="操作" field="opt" />
			<t:dgDelOpt title="删除" url="userController.do?del&id={id}&userName={userName}" />
			<t:dgToolBar title="用户录入" icon="icon-add" url="userController.do?addorupdate" funname="add"></t:dgToolBar>
			<t:dgToolBar title="用户编辑" icon="icon-edit" url="userController.do?addorupdate" funname="update"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>