<script type="text/javascript">
	$(function() {
		$('#departList')
				.treegrid(
						{
							idField : 'id',
							treeField : 'text',
							title : '部门列表',
							url : 'departController.do?departgrid&field=id,name,description,',
							fit : true,
							loadMsg : '数据加载中...',
							pageSize : 10,
							pagination : false,
							pageList : [ 10, 20, 30 ],
							sortOrder : 'asc',
							rownumbers : true,
							singleSelect : true,
							fitColumns : true,
							showFooter : true,
							frozenColumns : [ [] ],
							columns : [ [
									{
										field : 'id',
										title : '编号',
										hidden : true
									},
									{
										field : 'text',
										title : '部门名称'
									},
									{
										field : 'src',
										title : '职能描述'
									},
									{
										field : 'null',
										title : '操作',
										formatter : function(value, rec, index) {
											if (!rec.id) {
												return '';
											}
											var href = '';
											href += "[<a href='#' onclick=delObj('departController.do?del&id="
													+ rec.id
													+ "','departList')>";
											href += "删除</a>]";
											href += "[<a href='#' onclick=queryUsersByDepart('"
													+ rec.id
													+ "','"
													+ index
													+ "')>";
											href += "查看成员</a>]";
											return href;
										}
									} ] ],
							onLoadSuccess : function(data) {
								$("#departList").treegrid("clearSelections");
							},
							onClickRow : function(rowData) {
								rowid = rowData.id;
								gridname = 'departList';
								queryUsersByRowData(rowData);
							}
						});
		$('#departList').treegrid('getPager').pagination({
			beforePageText : '',
			afterPageText : '/{pages}',
			displayMsg : '{from}-{to}共{total}条',
			showPageList : true,
			showRefresh : true
		});
		$('#departList').treegrid('getPager').pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});
	});
	function reloadTable() {
		try {
			$('#' + gridname).datagrid('reload');
			$('#' + gridname).treegrid('reload');
		} catch (ex) {
		}
	}
	function reloaddepartList() {
		$('#departList').treegrid('reload');
	}
	function getdepartListSelected(field) {
		return getSelected(field);
	}
	function getSelected(field) {
		var row = $('#' + gridname).treegrid('getSelected');
		if (row != null) {
			value = row[field];
		} else {
			value = '';
		}
		return value;
	}
	function getdepartListSelections(field) {
		var ids = [];
		var rows = $('#departList').treegrid('getSelections');
		for (var i = 0; i < rows.length; i++) {
			ids.push(rows[i][field]);
		}
		ids.join(',');
		return ids
	};
	function getSelectRows() {
		return $('#departList').datagrid('getChecked');
	}
	function departListsearch() {
		var queryParams = $('#departList').datagrid('options').queryParams;
		$('#departListtb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#departList')
				.treegrid(
						{
							url : 'departController.do?departgrid&field=id,name,description,',
							pageNumber : 1
						});
	}
	function dosearch(params) {
		var jsonparams = $.parseJSON(params);
		$('#departList')
				.treegrid(
						{
							url : 'departController.do?departgrid&field=id,name,description,',
							queryParams : jsonparams
						});
	}
	function searchReset(name) {
		$("#" + name + "tb").find(":input").val("");
		departListsearch();
	}
</script>
<table width="100%" id="departList" toolbar="#departListtb"></table>
<div id="departListtb" style="padding: 3px; height: auto">
	<div style="height: 0px;">
		<span style="float: left;"></span>
	</div>