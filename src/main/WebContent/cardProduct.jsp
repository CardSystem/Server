<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>여기는 카드 상품 추가 페이지입니다.</h1>
	<form method="POST" action="ProductServlet">
		<input type="hidden" name="action" value="insert">
		카드 이름 : <input type="text" name="name"/><br>
		카드 타입 : <input type="text" name="type"/><br>
		카드 한도 : <input type="text" name="limit"/><br>
		카테고리 아이디: <input type="number" name="category"/><br>
		<input type="submit" value="등록"/>
	</form>
</body>
</html>