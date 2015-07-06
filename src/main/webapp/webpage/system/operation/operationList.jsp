<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="operationList" title="操作管理" actionUrl="functionController.do?opdategrid&functionId=${functionId}" idField="id">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="操作名称" field="name" width="100"></t:dgCol>
	<t:dgCol title="代码" field="code"></t:dgCol>
	<t:dgCol title="权限名称" field="menuFunction_name"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt url="functionController.do?delop&id={id}" title="删除"></t:dgDelOpt>
	<t:dgFunOpt funname="editoperation(id,operationname)" title="编辑"></t:dgFunOpt>
	<t:dgToolBar title="操作录入" icon="icon-add" url="functionController.do?addorupdateop&functionId=${functionId}" funname="add"></t:dgToolBar>
	<%-- <t:dgToolBar title="操作编辑" icon="icon-edit" url="functionController.do?addorupdateop&functionId=${functionId}" funname="update"></t:dgToolBar>--%>
</t:datagrid>
<script type="text/javascript">
function editoperation(operationId,operationname)
{
	createwindow("操作编辑","functionController.do?addorupdateop&functionId=${functionId}&id="+operationId);
}
</script>
