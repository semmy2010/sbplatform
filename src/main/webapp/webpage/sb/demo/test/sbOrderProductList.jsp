<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addBtn').bind('click', function(){   
 		 var tr =  $("#add_sbOrderProduct_table_template tr").clone();
	 	 $("#add_sbOrderProduct_table").append(tr);
	 	 resetTrNum('add_sbOrderProduct_table');
    });  
	$('#delBtn').bind('click', function(){   
       $("#add_sbOrderProduct_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_sbOrderProduct_table');
    });
	$(document).ready(function(){
		$(".datagrid-toolbar").parent().css("width","auto");
		//将表格的表头固定
	    $("#sbOrderProduct_table").createhftable({
	    	height:'200px',
			width:'auto',
			fixFooter:false
			});
});
</script>

<div style="padding: 3px; height: 25px; width: width: 900px;" class="datagrid-toolbar"><a id="addBtn" href="#">添加</a> <a id="delBtn" href="#">删除</a></div>
<table border="0" cellpadding="2" cellspacing="0" id="sbOrderProduct_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="left" bgcolor="#EEEEEE">产品名称</td>
		<td align="left" bgcolor="#EEEEEE">个数</td>
		<td align="left" bgcolor="#EEEEEE">服务项目类型</td>
		<td align="left" bgcolor="#EEEEEE">单价</td>
		<td align="left" bgcolor="#EEEEEE">小计</td>
		<td align="left" bgcolor="#EEEEEE">备注</td>
	</tr>
	<tbody id="add_sbOrderProduct_table">
		<c:if test="${fn:length(sbOrderProductList)  <= 0 }">
			<tr>
				<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
				<td align="left"><input nullmsg="请输入订单产品明细的产品名称！" datatype="s6-10" errormsg="订单产品明细的产品名称应该为6到10位" name="sbOrderProductList[0].gopProductName" maxlength="100" type="text" value=""
					style="width: 220px;"></td>
				<td align="left"><input nullmsg="请输入订单产品明细的产品个数！" datatype="n" errormsg="订单产品明细的产品个数必须为数字" name="sbOrderProductList[0].gopCount" maxlength="10" type="text" value="" style="width: 120px;"></td>
				<td align="left"><%--			<input name="sbOrderProductList[0].gopProductType" maxlength="3" type="text" value=""  style="width:120px;" >--%> <t:dictSelect
					field="sbOrderProductList[0].gopProductType" typeGroupCode="service" hasLabel="false" defaultVal="${poVal.gocSex}"></t:dictSelect></td>
				<td align="left"><input nullmsg="请输入订单产品明细的产品单价！" datatype="d" errormsg="订单产品明细的产品单价填写不正确" name="sbOrderProductList[0].gopOnePrice" maxlength="10" type="text" value=""
					style="width: 120px;"></td>
				<td align="left"><input nullmsg="请输入订单产品明细的产品小计！" datatype="d" errormsg="订单产品明细的产品小计填写不正确" name="sbOrderProductList[0].gopSumPrice" maxlength="10" type="text" value=""
					style="width: 120px;"></td>
				<td align="left"><input name="sbOrderProductList[0].gopContent" maxlength="200" type="text" value="" style="width: 120px;"></td>
			</tr>
		</c:if>
		<c:if test="${fn:length(sbOrderProductList)  > 0 }">
			<c:forEach items="${sbOrderProductList}" var="poVal" varStatus="stuts">
				<tr>
					<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
					<td align="left"><input nullmsg="请输入订单产品明细的产品名称！" datatype="s6-10" errormsg="订单产品明细的产品名称应该为6到10位" name="sbOrderProductList[${stuts.index }].gopProductName" maxlength="100" type="text"
						value="${poVal.gopProductName}" style="width: 220px;"></td>
					<td align="left"><input nullmsg="请输入订单产品明细的产品个数！" datatype="n" errormsg="订单产品明细的产品个数必须为数字" name="sbOrderProductList[${stuts.index }].gopCount" maxlength="10" type="text"
						value="${poVal.gopCount }" style="width: 120px;"></td>
					<td align="left"><%--			<input name="sbOrderProductList[${stuts.index }].gopProductType" maxlength="3" type="text" value="${poVal.gopProductType }"  style="width:120px;" >--%> <t:dictSelect
						field="sbOrderProductList[${stuts.index }].gopProductType" typeGroupCode="service" hasLabel="false" defaultVal="${poVal.gopProductType}"></t:dictSelect></td>
					<td align="left"><input nullmsg="请输入订单产品明细的产品单价！" datatype="d" errormsg="订单产品明细的产品单价填写不正确" name="sbOrderProductList[${stuts.index }].gopOnePrice" maxlength="10" type="text"
						value="${poVal.gopOnePrice }" style="width: 120px;"></td>
					<td align="left"><input nullmsg="请输入订单产品明细的产品小计！" datatype="d" errormsg="订单产品明细的产品小计填写不正确" name="sbOrderProductList[${stuts.index }].gopSumPrice" maxlength="10" type="text"
						value="${poVal.gopSumPrice }" style="width: 120px;"></td>
					<td align="left"><input name="sbOrderProductList[${stuts.index }].gopContent" maxlength="200" type="text" value="${poVal.gopContent }" style="width: 120px;"></td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
