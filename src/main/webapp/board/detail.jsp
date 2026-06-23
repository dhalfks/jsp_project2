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
	
	<!-- comment input line -->
	<div>
		<h3>댓글 입력</h3>
		<input type="text" id="cmtWriter" value="${ses.id }" 
				placeholder="writer..."> <br>
		<textarea rows="3" cols="30" id="cmtText" placeholder="Add Comment..."></textarea>
		<button type="button" id="cmtAddBtn">post</button>
	</div>
	
	<hr>
	
	<!-- comment print line -->
	
	<div id="commentLine">
		<div>
			<div>cno, bno, writer, regdate</div>
			<div>
				<input type="text" value="contents...">
				<button type="button">수정</button>
				<button type="button">삭제</button>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		const bno = `<c:out value="${board.bno}" />`;
	</script>
		
	<script type="text/javascript" src="/resources/boardDetails_comment.js"></script>
	
	<script type="text/javascript">
		printCommentList(bno);
	</script>
	
</body>
</html>