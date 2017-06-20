<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="Entity.User,Entity.Contract"%>
<!DOCTYPE HTML>
<!--
	Forty by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>分配操作员</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="assets/css/main.css" />
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
                function moveOption(s1,s2){
				// Cteate cache array to store value and text
				var arrSelValue = new Array();  
				var arrSelText = new Array();  
				// This array stores the selected options, corresponding to value
				var arrValueTextRelation = new Array();  
				var index = 0;// Assist to establish the cache array
				  
				// Store all data in the source list to the cache, and establish the corresponding relationship of value and selected option
			   for ( var i = 0; i < s1.options.length; i++) {  
					if (s1.options[i].selected) {  
						arrSelValue[index] = s1.options[i].value;  
						arrSelText[index] = s1.options[i].text;  
						// Cteate the corresponding relationship of value and selected option
						arrValueTextRelation[arrSelValue[index]] = s1.options[i];  
						index++;  
					}  
				}  
		  
				// Increase cache data to purpose list, and delete the corresponding item in source list
				for ( var i = 0; i < arrSelText.length; i++) {  
					var oOption = document.createElement("option");  
					oOption.text = arrSelText[i];  
					oOption.value = arrSelValue[i];  
					s2.add(oOption);
					s2.options[i].selected=true;  
					// Delete the corresponding item in source list
					s1.removeChild(arrValueTextRelation[arrSelValue[i]]);  
				} 
			}
			
			// Judgment whether user has assigned operator, if does not assign, giving prompt message; Or submit form to assign operator
			function check(){
				// Cteate cache array to store assigned operator
      		   	var assignedOperator = new Array(3); 
				assignedOperator[0] = document.assignOperForm.hqht;
				assignedOperator[1] = document.assignOperForm.spht;
				assignedOperator[2] = document.assignOperForm.qdht;

				// If there is no assigned operator, giving a prompt message
				if((assignedOperator[0].length) < 1){
					alert("Please assign countersign operator!");
					return false;
				} 
				
				if((assignedOperator[1].length) < 1){
					alert("Please assign approver!");
					return false;
				} 
				
				if((assignedOperator[2].length) < 1){
					alert("Please assign signer!");
					return false;
				} 
			}
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
										<h1>分配操作员</h1>
									</header>
									<span class="image main"><img src="images/pic11.jpg" alt="" /></span>
    <%
			Contract contract = (Contract)request.getAttribute("contract");
		%>
		<div class="mtitle">
			Assign operator: <%=contract.getName()%>
		</div>
		<%
			List<User> userList = (List<User>)request.getAttribute("userList");
        %>
		<br /><br />
		<form name="assignOperForm" action="assignOper" method="post">
			<input type="hidden" name="conId" value="<%=contract.getId()%>">
			<h3>
				<img src="images/cog_edit.png"  alt="Assign countersign people" />
				分配会签操作者
			</h3>
			<table border="0" width="200" class="update"> 
				<tr>
					<td width="45%"> 
						可分配的操作员: 
						<select style="WIDTH:100%" multiple name="dfphqht" size="12">
						    <%  
						    	for (User user : userList) {
			       	 		%> 
							<option value="<%=user.getId()%>"><%=user.getName()%></option> 
							<%} %>
						</select> 
					</td> 
					<td width="10%" align="center"> 
						<input type="button" value="&gt&gt" 
					onclick="moveOption(document.assignOperForm.dfphqht, document.assignOperForm.hqht)">
						<br/> <br/> 
						<input type="button" value="&lt&lt" 
					onclick="moveOption(document.assignOperForm.hqht, document.assignOperForm.dfphqht)"> 
					</td> 
					<td width="45%"> 
						被分配的操作者:
						<select style="WIDTH:100%" multiple name="hqht" size="12"> 
						</select> 
					</td> 
				</tr> 				
			</table> 
			<br />
			<h3>
				<img src="images/cog_edit.png"  alt="Assign approver" />
				分配审批操作者
			</h3>
			<table border="0" width="400"  class="update"> 
				<tr>
					<td width="45%"> 
						可分配的操作员: 
						<select style="WIDTH:100%" multiple name="dfpspht" size="12"> 
							 <%  
						    	for (User user : userList) {
			       	 		%>  
							<option value="<%=user.getId()%>"><%=user.getName()%></option> 
							<%} %>
						</select> 
					</td> 
					<td width="10%" align="center"> 
						<input type="button" value="&gt&gt" 
					onclick="moveOption(document.assignOperForm.dfpspht, document.assignOperForm.spht)">
						<br/> <br/> 
						<input type="button" value="&lt&lt" 
					onclick="moveOption(document.assignOperForm.spht, document.assignOperForm.dfpspht)"> 
					</td> 
					<td width="45%"> 
						被分配的操作者:
						<select style="WIDTH:100%" multiple name="spht" size="12"> 
						</select> 
					</td> 
				</tr> 				
			</table>
			<br />
			<h3>
				<img src="images/cog_edit.png"  alt="Assign signer" />
				分配签字者
			</h3>
			<table border="2" width="400"  class="update"> 
				<tr>
					<td width="45%"> 
						可分配的操作员: 
						<select style="WIDTH:100%" multiple name="dfpqdht" size="12"> 
							 <%  
						    	for (User user : userList) {
			       	 		%> 
							<option value="<%=user.getId()%>"><%=user.getName()%></option> 
							<%} %> 
						</select> 
					</td> 
					<td width="10%" align="center"> 
						<input type="button" value="&gt&gt" 
					onclick="moveOption(document.assignOperForm.dfpqdht, document.assignOperForm.qdht)">
						<br/> <br/> 
						<input type="button" value="&lt&lt" 
					onclick="moveOption(document.assignOperForm.qdht, document.assignOperForm.dfpqdht)"> 
					</td> 
					<td width="45%"> 
						被分配的操作者:
						<select style="WIDTH:100%" multiple name="qdht" size="12"> 
						</select> 
					</td> 
				</tr> 				
			</table>
			
			<table width="400" class="update"> 
				<tr>
					<td colspan="2" style="text-align:center;">
				<input type="submit" value="提交" class="button" onclick="return check()"> &nbsp; &nbsp; &nbsp; 
				<input type="reset" value="重写" class="button">
				</td>
				</tr>
			</table>
			<br/>
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