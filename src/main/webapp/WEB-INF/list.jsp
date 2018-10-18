<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>列表页面</title>
</head>
<body>
	<table border="1" width="200px" height="200px" align="center">
		<tr>
			<td>
				<a href="http://127.0.0.1:8080/SSM_Maven_Demo/AuthorController/getAllUser.do">用户</a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="http://127.0.0.1:8080/SSM_Maven_Demo/BookController/getAllBook.do">书</a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="http://127.0.0.1:8080/SSM_Maven_Demo/BookController/AddBook.do">添加一本图书</a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="http://127.0.0.1:8080/SSM_Maven_Demo/BookController/AddMoreBook.do">添加多本图书</a>
			</td>
		</tr>
	</table>
</body>
</html>