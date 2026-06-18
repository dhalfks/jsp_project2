<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>글쓰기 페이지</h1>
	
	<form action="/brd/insert" method="post">
		제목: <input type="text" name="title" placeholder="제목을 입력하세요."><br>
		작성자 : <input type="text" name="writer" placeholder="작성자..."><br>
		내용:
		<textarea rows="10" cols="30" name="contents" placeholder="내용을 입력하세요."></textarea><br>
		<button type="submit">등록</button>
		<button type="reset">취소</button>
	</form>
	
	
	
	
	
	
	
</body>
</html>