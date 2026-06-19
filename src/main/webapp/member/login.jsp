<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로그인 페이지</h1>
	
	<form action="/user/join" method="post">
		id : <input type="text" name="id" placeholder="ID..."> <br>
		password : <input type="password" name="pw" placeholder="password..."><br>
		
		<button type="submit">로그인</button>
	</form>
	

</body>
</html>