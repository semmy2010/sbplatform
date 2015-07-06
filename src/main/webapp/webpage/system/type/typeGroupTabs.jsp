<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:tabs id="typeGroupTabs" iframe="false" tabPosition="bottom">
	<c:forEach items="${typeGroupList}" var="typeGroup">
		<t:tab iframe="systemController.do?typeList&typeGroupId=${typeGroup.id}" icon="icon-add" title="${typeGroup.typeGroupName}" id="${typeGroup.typeGroupCode}"></t:tab>
	</c:forEach>
</t:tabs>
