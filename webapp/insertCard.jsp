<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form method="POST" action="card/insert">
		<input type="hidden" name="action" value="insert">
		ī���ǰ id :<input type="number" name="card_id"/><br>
		������ :<input type="text" name="agency"/><br>
		����� :<input type="text" name="issuer"/><br>
		������� id :<input type="number" name="account_id"/><br>
		<input type="submit" value="���"/>
		
		
		
	</form>
</body>
</html>