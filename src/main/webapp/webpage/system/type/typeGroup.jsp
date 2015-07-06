<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>字典类型信息</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="systemController.do?saveTypeGroup">
	<input name="id" type="hidden" value="${typeGroup.id }">
	<fieldset class="step">
	<div class="form">
	<label class="Validform_label"> 字典编码: </label> 
	<input name="typeGroupCode" class="inputxt" validType="s_t_type_group,typeGroupCode,id" value="${typeGroup.typeGroupCode }" datatype="s2-10"> <span class="Validform_checktip">编码范围在2~8位字符</span></div>
	<div class="form">
	<label class="Validform_label"> 字典名称: </label> 
	<input name="typeGroupName" class="inputxt" value="${typeGroup.typeGroupName }" datatype="s2-10"> <span class="Validform_checktip">名称范围在2~10位字符</span></div>
	</fieldset>
</t:formvalid>
</body>
</html>
