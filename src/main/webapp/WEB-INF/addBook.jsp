<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加图书</title>
<script type="text/javascript">
	function gohome(){
		window.location.href = "<%=basePath %>BookController/index.do";			
	}
</script>
</head>
<body>
	<button id="gohome" onclick="gohome()">返回主页</button>
	<form action="<%=basePath %>BookController/AddBook.do" method="post">
		<table border="1" width="500px" height="200px" align="center">
			<input type="hidden" name="reg" value="1">
			<input type="hidden" name="id" value="${book.id }">
			<tr align="center">
				<td>书名</td>
				<td>
					<input type="text" name="bookname" value="${book.name }">
				</td>
			</tr>
			<tr align="center">
				<td>作者</td>
				<td>
					<select name="authorid" id="authorid">
						<option>--选择作者--</option>
						<c:forEach var="author" items="${authors }">
							<option value="${author.id }" <c:if test="${author.id==book.authorid }">selected</c:if> >${author.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr align="center">
				<td>价格</td>
				<td>
					<input type="text" name="price" value="${book.price }">
				</td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<input type="submit" value="提交">
				</td>
			</tr>
			<tr align="center">
				<td colspan="2">${msg}</td>
			</tr>
		</table>
	</form>
</body>
</html>