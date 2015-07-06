<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" data-options="fit:true" id="role_list_layout">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="roleList" title="角色列表" actionUrl="roleController.do?roleGrid" idField="id">
			<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="角色编码" field="roleCode" width="100"/>
			<t:dgCol title="角色名称" field="roleName" query="true"  width="100"/>
			<t:dgCol title="操作" field="opt"  width="100"/>
			<t:dgFunOpt funname="delRole(id)" title="删除"></t:dgFunOpt>
			<t:dgFunOpt funname="setfunbyrole(id,roleName)" title="权限设置"></t:dgFunOpt>
			<t:dgToolBar title="角色录入" icon="icon-add" url="roleController.do?addorupdate" funname="add"></t:dgToolBar>
			<t:dgToolBar title="角色编辑" icon="icon-edit" url="roleController.do?addorupdate" funname="update"></t:dgToolBar>
		</t:datagrid>
	</div>
	<div style="width: 400px;"
		data-options="region:'east',collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		showEast = 1;
	},
	onCollapse : function() {
	    showEast = 0;
	}">
		<div tools="#tt" class="easyui-panel" title="权限设置" style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
	</div>
	<div id="tt"></div>
</div>
<script type="text/javascript">
	$(function() {
		var showEast = 0;
	});
	function setfunbyrole(id, roleName) {
		if(showEast == 0){
			$('#role_list_layout').layout('expand', 'east');
		}
		$("#function-panel").panel({
			title : roleName + ":当前权限",
			href : "roleController.do?fun&roleId=" + id
		});
		$('#function-panel').panel("refresh");

	}
	//删除角色
	function delRole(id) {
		var tabName = 'roleList';
		var url = 'roleController.do?delRole&id=' + id;
		$.dialog.confirm('确定删除该记录吗', function() {
			doSubmit(url, tabName);
			rowid = '';
			$("#function-panel").html("");//删除角色后，清空对应的权限
		}, function() {
		});
	}
</script>