<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;"><t:datagrid name="sbBlobDataList" title="Blob型数据操作例子" actionUrl="sbBlobDataController.do?datagrid" idField="id" fit="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="标题" field="attachmenttitle"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgFunOpt title="下载" funname="sbBlobData_funcDownloadFile(id)" />
	<t:dgDelOpt title="删除" url="sbBlobDataController.do?del&id={id}" />
	<t:dgToolBar title="上传" icon="icon-add" url="sbBlobDataController.do?addorupdate" funname="add"></t:dgToolBar>
</t:datagrid></div>
</div>
<script type="text/javascript">
function sbBlobData_funcDownloadFile(id) {
	window.location.href = "sbBlobDataController.do?download&fileId="+id; 
}
 </script>