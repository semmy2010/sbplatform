<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;"><t:datagrid name="sbMatterBomList" title="物料Bom列表" actionUrl="sbMatterBomController.do?doTreeGrid" treegrid="true" idField="id"
	pagination="false">
	<t:dgCol title="编号" field="id" treefield="id" hidden="true" />
	<t:dgCol title="名称" field="name" treefield="text" />
	<t:dgCol title="编码" field="code" treefield="src" />
	<t:dgCol title="操作" field="opt" width="100" />
	<t:dgDelOpt title="删除" url="sbMatterBomController.do?doDelete&id={id}" />
	<t:dgToolBar title="录入" icon="icon-add" url="sbMatterBomController.do?goEdit" funname="add" operationCode="add" />
	<t:dgToolBar title="编辑" icon="icon-edit" url="sbMatterBomController.do?goEdit" funname="update" operationCode="update" />
</t:datagrid></div>
</div>