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
	<h1>회원정보 수정 페이지</h1>
	
	<form action="/user/update" method="post">
		id : <input type="text" name="id" value="${ses.id }" readonly="readonly"> <br>
		password : <input type="password" name="pw" placeholder="password..."><br>
		email : <input type="text" name="email" value="${ses.email }"><br>
		phone : <input type="text" name="phone" value="${ses.phone }"><br>
		
		최종로그인 : <span>${ses.lastlogin }</span><br>
		등록일 : <span>${ses.regdate }</span><br>
		
		<button type="submit">회원정보수정</button>
	 	<a href="/user/remove"><button type="button">회원탈퇴</button></a>
	</form>
	
	<a href="/"><button>home</button></a>
	
	
	<script type="text/javascript">
		const update_msg = `<c:out value="${update_msg }"></c:out>`;
		if(update_msg == 'Fail'){
			alert('회원정보 수정이 실패하였습니다. 다시 시도해주세요.');
		}
		
		const delete_msg = `<c:out value="${delete_msg }"></c:out>`;
		if(delete_msg == 'Fail'){
			alert('회원삭제 실패!');
		}
	</script>
	

</body>
</html>