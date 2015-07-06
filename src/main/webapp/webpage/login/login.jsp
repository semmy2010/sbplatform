<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统登录</title>
<link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
<link href="plug-in/login/css/login.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all" />
<link href="plug-in/login/css/demo.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all" />

</head>
<body>
	<div class="header">
		<h1 class="headerLogo">
			<a title="后台管理系统" target="_blank" href="#"><img alt="logo" src="plug-in/login/images/logo.gif"></a>
		</h1>
	</div>

	<div class="banner">

		<div class="login-aside">
			<div id="o-box-up"></div>
			<div id="o-box-down" style="table-layout: fixed;">
				<div class="error-box"></div>

				<form name="formLogin" id="formLogin" action="loginController.do?login" check="loginController.do?checkuser" method="post">
					<input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900" />
					<div class="fm-item">
						<label for="logonId" class="form-label">用户名</label> <input type="text" nullmsg="请输入用户名!" value="请输入用户名"  maxlength="100" id="userName" name="userName"
							class="i-text" datatype="s1-18" errormsg="用户名至少6个字符,最多18个字符！">
						<div class="ui-form-explain"></div>
					</div>

					<div class="fm-item">
						<label for="logonId" class="form-label">密&nbsp;&nbsp;码</label> <input type="password" nullmsg="请输入密码!" value="请输入密码"  name="password" maxlength="100" id="password" class="i-text"
							datatype="*1-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！">
						<div class="ui-form-explain"></div>
					</div>

					<div class="fm-item pos-r">
						<label for="logonId" class="form-label">验证码</label>
						<div class="ui-form-explain">
							<input type="text" value="输入验证码" maxlength="100" id="randCode" name="randCode" class="i-text yzm" nullmsg="请输入验证码！"> <img id="randCodeImage" src="randCodeImage"
								class="yzm-img" />
						</div>
					</div>

					<div class="fm-item">
						<label for="logonId" class="form-label"></label> <input type="button" value="" tabindex="4" id="btn_login" class="btn-login">
						<div class="ui-form-explain"></div>
					</div>
				</form>
			</div>
		</div>

		<div class="bd">
			<ul>
				<li style="background: url(plug-in/login/themes/theme-pic1.jpg) #CCE1F3 center 0 no-repeat;"><a target="_blank" href="#"></a></li>
				<li style="background: url(plug-in/login/themes/theme-pic2.jpg) #BCE0FF center 0 no-repeat;"><a target="_blank" href="#"></a></li>
			</ul>
		</div>

		<div class="hd">
			<ul></ul>
		</div>
	</div>

	<div class="banner-shadow"></div>

	<div class="footer">
		<p>Copyright &copy; 2014.Company name All rights reserved.</p>
	</div>

	<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
	<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
	<script type="text/javascript" src="plug-in/login/js/login.js"></script>
	<script type="text/javascript" src="plug-in/login/js/jquery.SuperSlide.js"></script>
	<script type="text/javascript" src="plug-in/login/js/Validform_v5.3.2_min.js"></script>
	<script type="text/javascript">
		jQuery(".banner").slide({
			titCell : ".hd ul",
			mainCell : ".bd ul",
			effect : "fold",
			autoPlay : true,
			autoPage : true,
			trigger : "click"
		});
	</script>
	<script type="text/javascript">
		$(function() {

			$(".i-text").focus(function() {
				$(this).addClass('h-light');
			});

			$(".i-text").focusout(function() {
				$(this).removeClass('h-light');
			});

			$("#userName").focus(function() {
				var username = $(this).val();
				if (username == '请输入用户名') {
					$(this).val('');
				}
			});

			$("#userName").focusout(function() {
				var username = $(this).val();
				if (username == '') {
					$(this).val('请输入用户名');
				}
			});

			$("#password").focus(function() {
				var password = $(this).val();
				if (password == '请输入密码') {
					$(this).val('');
				}
			});

			$("#randCode").focus(function() {
				var captcha = $(this).val();
				if (captcha == '输入验证码') {
					$(this).val('');
				}
			});

			$("#randCode").focusout(function() {
				var captcha = $(this).val();
				if (captcha == '') {
					$(this).val('输入验证码');
				}
			});

		});
	</script>
</body>
</html>
