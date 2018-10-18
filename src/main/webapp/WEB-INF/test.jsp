<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试页面</title>
<script type="text/javascript">
	function gohome(){
		window.location.href = "<%=basePath %>BookController/index.do";		
	}
</script>
</head>
<body>
	<button id="gohome" onclick="gohome()">返回主页</button>
	<table align="center" border="1px" width="500px">
		<th colspan="3">用户列表</th>
		<c:forEach var="author" varStatus="i" items="${authors}">
			<tr align="center">
				<td>${author.id}</td>
				<td>${author.name}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>