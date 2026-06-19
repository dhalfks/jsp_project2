<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입 페이지</h1>
	
	<form action="/user/insert" method="post">
		id : <input type="text" name="id" placeholder="ID..."> <br>
		password : <input type="password" name="pw" placeholder="password..."><br>
		email : <input type="text" name="email" placeholder="test@test.com"><br>
		phone : <input type="text" name="phone" placeholder="000-0000-0000"><br>
		
		<button type="submit">회원가입</button>
	</form>
	
	<a><button>home</button></a>
	

</body>
</html>