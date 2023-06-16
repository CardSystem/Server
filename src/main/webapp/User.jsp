<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form method="post" action="userServlet">
		<input type="hidden" name="action" value="login">
		<input type="text" placeholder="아이디" name="id">
		<input type="text" placeholder="생년월일" name="userBirth">
		<input type="submit" value="로그인"/>
	</form>
	
</body>
</html>