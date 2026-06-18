<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>수정 페이지</h1>
	
	<form action="/brd/update" method="post">
		번호: <input type="text" name="bno" value="${board.bno }" readonly="readonly"><br>
		제목: <input type="text" name="title" value="${board.title }"><br>
		작성자 : <input type="text" name="writer" value="${board.writer }" readonly="readonly"><br>
		작성일 : <span>${board.regdate }</span><br>
		수정일 : <span>${board.moddate }</span><br>
		내용:
		<textarea rows="10" cols="30" name="contents" >${board.contents }</textarea><br>
		<button type="submit">수정</button>
		<a href="/brd/detail?bno=${board.bno }"><button type="button">취소</button></a>
	</form>

</body>
</html>