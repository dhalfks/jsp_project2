<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원정보 수정 페이지</h1>
	
	<form action="/user/update" method="post">
		id : <input type="text" name="id" value="${ses.id }" readonly="readonly"> <br>
		password : <input type="password" name="pw" placeholder="password..."><br>
		email : <input type="text" name="email" value="${ses.email }"><br>
		phone : <input type="text" name="phone" value="${ses.phone }"><br>
		
		최종로그인 : <span>${ses.lastLogin }</span><br>
		등록일 : <span>${ses.regdate }</span><br>
		
		<button type="submit">회원정보수정</button>
		<button type="reset">취소</button>
	</form>
	
	<a href="/"><button>home</button></a>
	

</body>
</html>