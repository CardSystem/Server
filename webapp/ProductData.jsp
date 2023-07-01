<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 접속 성공!
<form method="POST" action="product">
		<input type="hidden" name="action" value="insert">
		카드이름 :<input type="text" name="card_name"/><br>
		카드타입 :<input type="text" name="card_type"/>
		한도 :<input type="number" name="card_limit"/>
		제휴카테고리 :<input type="number" name="category_id"/>
		
		<input type="submit" value="등록"/>
	</form>
</body>
</html>