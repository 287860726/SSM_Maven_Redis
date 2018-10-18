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
<script src="<%=basePath %>/js/jquery.js"></script>
<title>测试页面</title>
</head>
<script type="text/javascript">
function gohome(){
	window.location.href = "<%=basePath %>BookController/index.do";		
}
// 		$("#xiu").click(function(){
//			 		$.ajax(
//			 		{
			<%-- 			url:'<%=basePath%>/BookController/delOneBook.do', --%>
//			 			type:'post',
//			 			async:true,
//			 			data:{
//			 				id:$('#xiu').attr('value')
//			 			},
//			 			success:function(data){
//			 				alert('删除成功');
//			 			},
//			 			error:function(data){
//			 				alert('删除失败');
//			 			}
//			 		});
// 				});

		
// 		function deleteBook(varid,reg){
// 			$.ajax({
<%-- 				url:'<%=basePath%>/BookController/delOneBook.do', --%>
// 				type:'post',
// 				async:true,
// 				data:{
// 					'id':varid
// 				},
// 				success:function(data){
// 					$(reg).parents('tr').remove();
// 					alert('删除成功');
// 				},
// 				error:function(data){
// 					alert('删除失败');
// 				}
// 			});
// 		}
		
		//模仿事物，删除书籍同时，删除作者
		function deleteBook(varid,reg){
			$.ajax({
				url:'<%=basePath%>/BookController/DelBookAndAuthor.do',
				type:'post',
				async:true,
				data:{
					'id':varid
				},
				success:function(data){
					$(reg).parents('tr').remove();
					alert('删除成功');
				},
				error:function(data){
					alert('删除失败');
				}
			});
		}
</script>
<body>
	<button id="gohome" onclick="gohome()">返回主页</button>
	<table align="center" width="500px" border="1px">
		<thead align="center">
			<td>编号</td>
			<td>书名</td>
			<td>作者</td>
			<td>价格</td>
			<td>操作</td>
		</thead>
		<c:forEach var="bookAuthor" items="${bookAuthors}">
			<tr align="center">
				<td>${bookAuthor.bookid}</td>
				<td>${bookAuthor.bookname}</td>
				<td>${bookAuthor.authorname}</td>
				<td>${bookAuthor.bookprice}</td>
				<td>
					<a id="xiu${bookAuthor.bookid}" value="${bookAuthor.bookid}"
						href="<%=basePath%>book/bookAction_AddBook.action?id=${bookAuthor.bookid}&reg=update">修改</a>
					<button id="del${bookAuthor.bookid}" value="${bookAuthor.bookid}" onclick="deleteBook(${bookAuthor.bookid},this)">删除</button>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>