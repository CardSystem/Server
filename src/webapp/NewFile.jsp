<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

���� on
<form method="POST" action="card/check">
		<input type="hidden" name="action" value="insert">
		ī��id :<input type="number" name="card_id"/><br>
		�����id :<input type="text" name="user_id"/><br>
		���� :<input type="number" name="payment"/><br>
		������:<input type="text" name="franchisee"/><br>
		����ī�װ� :<input type="number" name="f_category"/><br>
		
		<input type="submit" value="���"/>
	</form>
</body>
</html>