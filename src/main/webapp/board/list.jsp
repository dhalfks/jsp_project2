<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>게시글 리스트 보기</h1>
	<table border="1">
		<thead>
			<tr>
				<th>no.</th>
				<th>title</th>
				<th>writer</th>
				<th>regdate</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="board">
				<tr>
					<td>${board.bno }</td>
					<td>
					 	<a href="/brd/detail?bno=${board.bno }">${board.title }</a> 
					</td>
					<td>${board.writer }</td>
					<td>${board.regdate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	${ph }
	
	<!-- paging line -->
	<div>
		<!-- 이전 -->
		<c:if test="true">
			<a> < </a>
		</c:if>
		
		<!-- 1~10 -->
		<c:forEach begin="1" end="10" var="i">
			<a>${i } </a>
		</c:forEach>
		
		<!-- 다음 -->
		<c:if test="true">
			<a> > </a>
		</c:if>
	
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>