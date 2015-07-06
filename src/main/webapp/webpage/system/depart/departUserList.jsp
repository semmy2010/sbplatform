<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="departUserList" title="用户管理" actionUrl="departController.do?userDatagrid&departId=${departId}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"/>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true"/>
	<t:dgCol title="真实姓名" field="realName" query="true"/>
	<t:dgCol title="状态" sortable="true" field="status" replace="正常_1,禁用_0,超级管理员_-1"/>
	<t:dgCol title="操作" field="opt"/>
	<t:dgDelOpt title="删除" url="userController.do?del&id={id}&userName={userName}" />
	<t:dgToolBar title="用户录入" icon="icon-add" url="userController.do?addorupdate&departId=${departId}" funname="add"/>
	<t:dgToolBar title="用户编辑" icon="icon-edit" url="userController.do?addorupdate&departId=${departId}" funname="update"/>
</t:datagrid>
