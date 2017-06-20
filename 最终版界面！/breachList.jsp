<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="Entity.CBEntity"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="css/style.css" rel="stylesheet" media="screen"
			type="text/css" />
		<title>List of contract to be approved</title>
		<!-- Use JavaScript script to open a new window display information when preview-->
		<script>
			function preview(url) {
				window.open(url,'Preview','toolbar=no,scrollbars=yes,width=720,height=560,top=50,left=100');
			}
		</script>
	</head>

	<body>
		<div class="mtitle">
			Contract to be approved
		</div>
		
		<div class="search">
			<form>
				Search contract to be approved:
				<input value="Enter the search conditions.." />
				&nbsp;&nbsp;
				<input type="submit" value="Search" class="search-submit"/> <br />
			</form>
		</div>
		
		<div class="list">
		  <table>
			<tr>
				<th>
					Contract name
				</th>
				<th class="th1">
					Draft time
				</th>
				<th class="th1">
					Breach Money
				</th>  
				<th class="th1">
					Operation
				</th>
			</tr>
			<%
			    List<CBEntity> contractList = (List<CBEntity>)request.getAttribute("contractList");  
			    for (CBEntity cbe : contractList) {
       	 	%>
			<tr>
				<td class="tdname">
					<a href="javascript:preview('contractDetail?conId=<%=cbe.getConId()%>')"><%=cbe.getConName()%></a>
				</td>
				<td>
					<%=cbe.getDrafTime()%>
				</td>
				<td>
					<%=cbe.getbreach()%>
				</td>
				<td>
					<a href="Breach?conId=<%=cbe.getConId()%>">
						<img src="images/icon-edit.png"  alt="Approve" />
						Breach
					</a>
				</td>
			</tr>
			<%} %>
			
			<tr>
				<td colspan="3"> </td>
			</tr>
		  </table>
		</div>

		<div align="right" class="pagelist">					
			<a href="#"><img src="images/page/first.png"  alt="" /></a> &nbsp;
			<a href="#"><img src="images/page/pre.png"  alt="" /></a>&nbsp;
			<a href="#"><img src="images/page/next.png"  alt="" /></a>&nbsp;
			<a href="#"><img src="images/page/last.png"  alt="" /></a>&nbsp;
					
			<span class="pageinfo">
				Total&nbsp;<strong>2</strong>&nbsp;pages&nbsp;<strong>13</strong>&nbsp;records
			</span>	
		</div>
	</body>
</html>
