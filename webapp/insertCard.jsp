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
		카드상품 id :<input type="number" name="card_id"/><br>
		가맹점 :<input type="text" name="agency"/><br>
		담당자 :<input type="text" name="issuer"/><br>
		연결계좌 id :<input type="number" name="account_id"/><br>
		<input type="submit" value="등록"/>
		
		
		
	</form>
</body>
</html>