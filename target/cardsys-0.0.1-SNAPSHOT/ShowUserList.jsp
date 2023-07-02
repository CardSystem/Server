<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="GET" action="UserServlet">
		<input type="hidden" name="action" value="userList" />
		<input type="submit" value="유저 목록 확인" />
	</form>
</body>
</html>