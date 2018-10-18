<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/css/newtable.css" />
<title>添加图书</title>
</head>
<body>
	<form action="<%=basePath%>/BookController/AddMoreBook.do" method="post">
		<table border="1" width="500px" height="200px" align="center">
			<input type="hidden" name="reg" value="1">
			<tr>
				<td colspan="2">第一本书</td>
			</tr>
			<tr>
				<td>书名</td>
				<td>
					<input type="text" name="bookname">
				</td>
			</tr>
			<tr>
				<td>作者</td>
				<td>
					<select name="authorid" id="authorid">
						<option>--选择作者--</option>
						<c:forEach var="author" items="${authors }">
							<option value="${author.id }" <c:if test="${author.id==book.authorid }">selected</c:if>>${author.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>价格</td>
				<td>
					<input type="text" name="price">
				</td>
			</tr>

			<tr>
				<td colspan="2">第二本书</td>
			</tr>
			<tr>
				<td>书名</td>
				<td>
					<input type="text" name="bookname1">
				</td>
			</tr>
			<tr>
				<td>作者</td>
				<td>
					<select name="authorid1" id="authorid">
						<option>--选择作者--</option>
						<c:forEach var="author" items="${authors }">
							<option value="${author.id }" <c:if test="${author.id==book.authorid }">selected</c:if>>${author.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>价格</td>
				<td>
					<input type="text" name="price1">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="提交">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>