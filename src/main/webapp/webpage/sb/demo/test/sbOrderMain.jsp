<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<script type="text/javascript">
  //初始化下标
	function resetTrNum(tableId) {
		$tbody = $("#"+tableId+"");
		$tbody.find('>tr').each(function(i){
			$(':input, select', this).each(function(){
				var $this = $(this), name = $this.attr('name'), val = $this.val();
				if(name!=null){
					if (name.indexOf("#index#") >= 0){
						$this.attr("name",name.replace('#index#',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
			});
		});
	}
 </script>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="sbOrderMainController.do?save">
	<input id="id" name="id" type="hidden" value="${sbOrderMainPage.id }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"> 订单号: </label></td>
			<td class="value"><input class="inputxt" id="goOrderCode" name="goOrderCode" datatype="*" value="${sbOrderMainPage.goOrderCode}"></td>
			<td align="right"><label class="Validform_label"> 订单类型: </label></td>
			<td class="value"><%--						<input class="inputxt" id="goderType" name="goderType" --%> <%--							   value="${sbOrderMainPage.goderType}">--%> <t:dictSelect field="goderType"
				typeGroupCode="order" hasLabel="false" defaultVal="${sbOrderMainPage.goderType}"></t:dictSelect></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 顾客类型 </label></td>
			<td class="value"><%--						<input class="inputxt" id="usertype" name="usertype" --%> <%--							   value="${sbOrderMainPage.usertype}">--%> <t:dictSelect field="usertype"
				typeGroupCode="custom" hasLabel="false" defaultVal="${sbOrderMainPage.usertype}"></t:dictSelect></td>
			<td align="right"><label class="Validform_label"> 联系人: </label></td>
			<td class="value"><input nullmsg="联系人不能为空" errormsg="联系人格式不对" class="inputxt" id="goContactName" name="goContactName" value="${sbOrderMainPage.goContactName}" datatype="*"></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 手机: </label></td>
			<td class="value"><input class="inputxt" id="goTelphone" name="goTelphone" value="${sbOrderMainPage.goTelphone}" datatype="m" errormsg="手机号码不正确!" ignore="ignore"></td>
			<td align="right"><label class="Validform_label"> 订单人数: </label></td>
			<td class="value"><input nullmsg="订单人数不能为空" errormsg="订单人数必须为数字" class="inputxt" id="goOrderCount" name="goOrderCount" value="${sbOrderMainPage.goOrderCount}" datatype="n"></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 总价(不含返款): </label></td>
			<td class="value"><input class="inputxt" id="goAllPrice" name="goAllPrice" value="${sbOrderMainPage.goAllPrice}" datatype="d"></td>
			<td align="right"><label class="Validform_label"> 返款: </label></td>
			<td class="value"><input nullmsg="返款不能为空" errormsg="返款格式不对" class="inputxt" id="goReturnPrice" name="goReturnPrice" value="${sbOrderMainPage.goReturnPrice}" datatype="d"></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 备注: </label></td>
			<td class="value" colspan="3"><input class="inputxt" id="goContent" name="goContent" value="${sbOrderMainPage.goContent}"></td>
		</tr>
	</table>
	<div style="width: auto; height: 200px;"><%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
	<div style="width: 690px; height: 1px;"></div>
	<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
		<t:tab href="sbOrderMainController.do?sbOrderProductList&goOrderCode=${sbOrderMainPage.goOrderCode}" icon="icon-search" title="产品明细" id="Product"></t:tab>
		<t:tab href="sbOrderMainController.do?sbOrderCustomList&goOrderCode=${sbOrderMainPage.goOrderCode}" icon="icon-search" title="客户明细" id="Custom"></t:tab>
	</t:tabs></div>
</t:formvalid>
<!-- 添加 产品明细 模版 -->
<table style="display: none">
	<tbody id="add_sbOrderProduct_table_template">
		<tr>
			<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
			<td align="left"><input nullmsg="请输入订单产品明细的产品名称！" datatype="s6-10" errormsg="订单产品明细的产品名称应该为6到10位" name="sbOrderProductList[#index#].gopProductName" maxlength="100" type="text"
				style="width: 220px;"></td>
			<td align="left"><input nullmsg="请输入订单产品明细的产品个数！" datatype="n" errormsg="订单产品明细的产品个数必须为数字" name="sbOrderProductList[#index#].gopCount" maxlength="10" type="text" style="width: 120px;"></td>
			<td align="left"><%--			 <input name="sbOrderProductList[#index#].gopProductType" maxlength="3" type="text" style="width:120px;">--%> <t:dictSelect
				field="sbOrderProductList[#index#].gopProductType" typeGroupCode="service" hasLabel="false"></t:dictSelect></td>
			<td align="left"><input nullmsg="请输入订单产品明细的产品单价！" datatype="d" errormsg="订单产品明细的产品单价填写不正确" name="sbOrderProductList[#index#].gopOnePrice" maxlength="10" type="text" style="width: 120px;"></td>
			<td align="left"><input nullmsg="请输入订单产品明细的产品小计！" datatype="d" errormsg="订单产品明细的产品小计填写不正确" name="sbOrderProductList[#index#].gopSumPrice" maxlength="10" type="text" style="width: 120px;"></td>
			<td align="left"><input name="sbOrderProductList[#index#].gopContent" maxlength="200" type="text" style="width: 120px;"></td>
		</tr>
	</tbody>
	<tbody id="add_sbOrderCustom_table_template">
		<tr>
			<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
			<td align="left"><input name="sbOrderCustomList[#index#].gocCusName" maxlength="50" type="text" style="width: 220px;"></td>
			<td align="left"><%--				<input name="sbOrderCustomList[#index#].gocSex" maxlength="2" type="text"  style="width:120px;"/>--%> <t:dictSelect field="sbOrderCustomList[#index#].gocSex"
				typeGroupCode="sex" hasLabel="false"></t:dictSelect></td>
			<td align="left"><input name="sbOrderCustomList[#index#].gocIdcard" maxlength="32" type="text" style="width: 120px;"></td>
			<td align="left"><input name="sbOrderCustomList[#index#].gocPassportCode" maxlength="32" type="text" style="width: 120px;"></td>
			<td align="left"><input name="sbOrderCustomList[#index#].gocBussContent" maxlength="100" type="text" style="width: 120px;"></td>
			<td align="left"><input name="sbOrderCustomList[#index#].gocContent" maxlength="200" type="text" style="width: 120px;"></td>
		</tr>
	</tbody>
</table>

</body>