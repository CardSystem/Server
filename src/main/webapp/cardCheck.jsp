<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 결제 내역 조회</title>
</head>
<body>
	카드 결제 내역 조회하기
	<form method="GET" action="CardControllerServlet">
		<input type="hidden" name="action" value="list" />
		<input type="submit" value="조회" />
	</form>
</body>
</html>