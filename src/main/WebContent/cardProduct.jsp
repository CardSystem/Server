<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>����� ī�� ��ǰ �߰�, ����, ���� �������Դϴ�.</h1>
	<form method="POST" action="product">
		<input type="hidden" name="action" value="register">
		ī�� �̸� : <input type="text" name="name"/><br>
		ī�� Ÿ�� : <input type="text" name="type"/><br>
		ī�� �ѵ� : <input type="text" name="limit"/><br>
		ī�װ� ���̵�: <input type="number" name="category"/><br>
		<input type="submit" value="���"/>
	</form>
	<br>
	<form method="POST" action="product">
		<input type="hidden" name="action" value="update">
		ī�� ���̵�: <input type="text" name="productId"/><br>
		ī�� �̸� : <input type="text" name="name"/><br>
		ī�� Ÿ�� : <input type="text" name="type"/><br>
		ī�� �ѵ� : <input type="text" name="limit"/><br>
		ī�װ� ���̵�: <input type="number" name="category"/><br>
		<input type="submit" value="���"/>
	</form>
	<br>
	<form method="POST" action="product">
		<input type="hidden" name="action" value="delete">
		ī�� ���̵�: <input type="text"	name="productId"/><br>
		
		<input type="submit" value="���"/>
	</form>
</body>
</html>