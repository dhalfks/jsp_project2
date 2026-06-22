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
	<h1>게시글 상세 페이지</h1>

	<table border="1">
		<tr>
			<th>no.</th>
			<td>${board.bno }</td>
		</tr>
		<tr>
			<th>title</th>
			<td>${board.title }</td>
		</tr>
		<tr>
			<th>writer</th>
			<td>${board.writer }</td>
		</tr>
		<tr>
			<th>regdate</th>
			<td>${board.regdate }</td>
		</tr>
		<tr>
			<th>moddate</th>
			<td>${board.moddate }</td>
		</tr>
		<tr>
			<th>contents</th>
			<td>${board.contents }</td>
		</tr>
	</table>
	<!-- 수정 버튼을 클릭하면 boardController > modify case로 이동 -->
	<!-- 해당 bno의 board 객체를 수정 페이지(modify.jsp)로 전달 -->
	<!-- modify.jsp에 화면을 수정할 수 있게 준비 -->
	
	<c:if test="${ses.id eq board.writer }">
		<a href="/brd/modify?bno=${board.bno }"><button>수정</button></a>
		<a href="/brd/delete?bno=${board.bno }"><button>삭제</button></a> 	
	</c:if>
	<a href="/brd/list"><button>리스트</button></a>
	
</body>
</html>