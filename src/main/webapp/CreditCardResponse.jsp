<%@page import="dto.CreditCardResponseDto"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
CreditCardResponseDto dto=(CreditCardResponseDto)request.getAttribute("data");
%>



상태코드:<%=dto.getStatusCode() %><br>
상태메시지:<%=dto.getStatusMsg() %><br>
카드아이디:<%=dto.getCardId()%><br>
유저아이디:<%=dto.getUserId()%><br>
가맹점:<%=dto.getFranchisee()%><br>
결제금액:<%=dto.getPayment()%><br>
잔액:<%=dto.getBalance()%><br>
성공여부:<%=dto.getIsSuccess()%><br>
결제일시:<%=dto.getDate()%><br>
제휴카테고리 아이디:<%=dto.getFCategory()%><br>
할부여부:<%=dto.getIsIns()%><br>
할부개월:<%=dto.getInsMonth()%><br>
카드타입:<%=dto.getCardType()%><br>


</body>
</html>