<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<form method="POST" action="card/credit">
		<input type="hidden" name="action" value="insert">
		ī��id :<input type="number" name="card_id"/><br>
		�����id :<input type="text" name="user_id"/>
		���ݾ� :<input type="number" name="payment"/>
		������ :<input type="text" name="franchisee"/>
		����ī�װ� :<input type="number" name="f_category"/>
		
		<input type="submit" value="���"/>
	</form>

</body>
</html>