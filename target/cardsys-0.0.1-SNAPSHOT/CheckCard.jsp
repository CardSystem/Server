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
<form id="myForm" method="POST" action="card/check">
    <input type="hidden" name="action" value="insert">
    ī��id :<input type="number" id="card_id" name="card_id"/><br>
    �����id :<input type="text" id="user_id" name="user_id"/><br>
    ���� :<input type="number" id="payment" name="payment"/><br>
    ������:<input type="text" id="franchisee" name="franchisee"/><br>
    ����ī�װ� :<input type="number" id="f_category" name="f_category"/><br>
    <input type="button" value="���">
</form>


</body>
</html>