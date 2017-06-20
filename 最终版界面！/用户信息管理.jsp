<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="Entity.User"%>
<!DOCTYPE HTML>
<!--
	Forty by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>用户信息管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="assets/css/main.css" />
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
	</head>
	<body>

		<!-- Wrapper -->
			<div id="wrapper">

				<!-- Header -->
					<header id="header">
						<a href="index.html" class="logo"><strong>合同管理</strong> <span>by 第二小组</span></a>
						<nav>
							<a href="#menu">菜单</a>
						</nav>
					</header>

				<!-- Menu -->
					<nav id="menu">
						<ul class="links">
							<li><a href="index.html">登录界面</a></li>
							<li><a href="landing.html">主界面</a></li>
							<li><a href="generic.html">注册界面</a></li>
					</nav>

				<!-- Main -->
					<div id="main" class="alt">

						<!-- One -->
							<section id="one">
								<div class="inner">
									<header class="major">
										<h1>用户信息管理</h1>
									</header>
									<span class="image main"><img src="images/pic11.jpg" alt="" /></span>
    <form action="UserinfoServlet" method="post">
<%
				// Get user object
				User user = new User();
				if (request.getAttribute("user") != null) {
				 	user = (User)request.getAttribute("user");
				}
			%>
				<div class="register_main">
					<table>
						<tr>
							<td class="title" >
								用户信息更改
							</td>
						</tr>
						<tr>
							<td width="120" align="center">
								用户名:
							</td>
							<td>
								<input type="text" name="name" id="name" value="<%=user.getName()%>" />
							</td>
						</tr>
						<tr>
							<td class="info" colspan="2">
								用户名必须字母开头，且长度不得少于四个字节
							</td>
						</tr>

						<tr>
							<td align="center">
								密码:
							</td>
							<td>
								<input type="password" name="password" id="password" value="" />
							</td>
						</tr>
						<tr>
							<td class="info" colspan="2">
								密码不能过于简单，推荐字母和数字混合
							</td>
						</tr>

						<tr>
							<td align="center">
								确认密码:
							</td>
							<td>
								<input type="password" name="password2" id="password2" value="" />
							</td>
						</tr>
						<tr>
							<td class="info" colspan="2">
								确认密码要保证两次输入相同!
							</td>
						</tr>

						<tr>
							<td colspan="2">
								<input type="submit" value="提交" class="button" />
							</td>
						</tr>
					</table>
				</div>

			</form>
								</div>
							</section>

					</div>


				<!-- Footer -->
					<footer id="footer">
						<div class="inner">
							<ul class="icons">
								<li><a href="#" class="icon alt fa-twitter"><span class="label">Twitter</span></a></li>
								<li><a href="#" class="icon alt fa-facebook"><span class="label">Facebook</span></a></li>
								<li><a href="#" class="icon alt fa-instagram"><span class="label">Instagram</span></a></li>
								<li><a href="#" class="icon alt fa-github"><span class="label">GitHub</span></a></li>
								<li><a href="#" class="icon alt fa-linkedin"><span class="label">LinkedIn</span></a></li>
							</ul>
							<ul class="copyright">
								<li>&copy; Zzk</li>
								<li>Design: <a href="https://user.qzone.qq.com/656028284">张子开</a></li>
								
							</ul>
						</div>
					</footer>

			</div>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/jquery.scrolly.min.js"></script>
			<script src="assets/js/jquery.scrollex.min.js"></script>
			<script src="assets/js/skel.min.js"></script>
			<script src="assets/js/util.js"></script>
			<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
			<script src="assets/js/main.js"></script>

	</body>
</html>