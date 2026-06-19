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
	<h1>welcome To JSP World~!!</h1>
	
	<a href="/brd/register">글쓰기</a>
	<a href="/brd/list">리스트보기</a>
	<a href="/user/register">회원가입</a>
	
	<!-- eq ==  / ne != -->
	<c:if test="${ses.id eq null }">
		<!-- 로그인은 로그인이 안된 상태에서만 출력 -->
		<a href="/user/login">로그인</a>
	</c:if>
	<br>
	<c:if test="${ses.id ne null }">
		welcome!! ${ses.id }님!! 마지막접속일:(${ses.lastlogin }) <br>
		<a href="/user/logout">로그아웃</a>
		<a href="/user/modify">회원정보수정</a>
	</c:if>
	
	
	
	<script type="text/javascript">
	
		const login_msg = `<c:out value="${param.login_msg }"></c:out>`;
		if(login_msg == 'notUser'){
			alert('로그인 정보가 일치하지 않습니다.');
			location.href="/";
		}
	
	</script>
</body>
</html>