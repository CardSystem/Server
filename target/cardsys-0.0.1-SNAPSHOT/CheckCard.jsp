<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>

</head>
<body>

서버 on
<form id="myForm" method="POST" action="card/check">
    <input type="hidden" name="action" value="insert">
    카드id :<input type="number" id="card_id" name="card_id"/><br>
    사용자id :<input type="text" id="user_id" name="user_id"/><br>
    가격 :<input type="number" id="payment" name="payment"/><br>
    가맹점:<input type="text" id="franchisee" name="franchisee"/><br>
    제휴카테고리 :<input type="number" id="f_category" name="f_category"/><br>
    <input type="button" value="등록">
</form>


</body>
</html>