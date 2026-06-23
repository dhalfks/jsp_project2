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
	
	<!-- search line -->
	<div>
		<form action="/brd/list" method="get">
			<select name="type">
				<option >Choose...</option>
				<option value="t"> title </option>
				<option value="w"> writer </option>
				<option value="c"> contents </option>
				<option value="twc"> all </option>
			</select>	
			
			<input type="text" name="keyword" placeholder="keyword...">	
			
			<!-- /brd/list => parameter (pageNo, qty) -->
			<input type="hidden" name="pageNo" value="1">
			<input type="hidden" name="qty" value="10">
			<button type="submit">검색</button> 
			<span>검색결과 : ${ph.totalCount }개</span>
		</form>
	</div>

	
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
	<!-- paging line -->
	<div>
		<!-- 이전  11 ~ 20-->
		<c:if test="${ph.prev }">
			<a href="/brd/list?pageNo=${ph.startPage-1 }&qty=${10 }&type=${ph.pagingVO.type}&keyword=${ph.pagingVO.keyword}"> < </a>
		</c:if>
		
		<!-- 1~10 -->
		<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
			<a href="/brd/list?pageNo=${i }&qty=${10}&type=${ph.pagingVO.type}&keyword=${ph.pagingVO.keyword}">${i } </a>
		</c:forEach>
		
		<!-- 다음 -->
		<c:if test="${ph.next }">
			<a href="/brd/list?pageNo=${ph.endPage+1 }&qty=${10 }&type=${ph.pagingVO.type}&keyword=${ph.pagingVO.keyword}"> > </a>
		</c:if>
	
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>