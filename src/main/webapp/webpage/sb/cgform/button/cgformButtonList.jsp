<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="cgformButtonList" title="自定义按钮[${tableName}]" actionUrl="cgformButtonController.do?datagrid&formId=${formId}" idField="id" fit="true">
			<t:dgCol title="编号" field="id" hidden="true" />
			<t:dgCol title="按钮编码" field="buttonCode" />
			<t:dgCol title="按钮名称" field="buttonName" />
			<t:dgCol title="按钮样式" field="buttonStyle" />
			<t:dgCol title="动作类型" field="optType" />
			<t:dgCol title="显示顺序" field="orderNum" />
			<t:dgCol title="显示图标样式" field="buttonIcon" />
			<t:dgCol title="显示表达式" field="exp" />
			<t:dgCol title="使用状态" field="buttonStatus" replace="使用_1,禁用_0" />
			<t:dgCol title="操作" field="opt" width="100" />
			<t:dgDelOpt title="删除" url="cgformButtonController.do?del&id={id}&formId=${formId}" />
			<t:dgToolBar title="录入" icon="icon-add" url="cgformButtonController.do?addorupdate&formId=${formId}" funname="add" />
			<t:dgToolBar title="编辑" icon="icon-edit" url="cgformButtonController.do?addorupdate" funname="update" />
			<t:dgToolBar title="查看" icon="icon-search" url="cgformButtonController.do?addorupdate" funname="detail" />
		</t:datagrid>
	</div>
</div>
