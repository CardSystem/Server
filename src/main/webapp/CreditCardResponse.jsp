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



�����ڵ�:<%=dto.getStatusCode() %><br>
���¸޽���:<%=dto.getStatusMsg() %><br>
ī����̵�:<%=dto.getCardId()%><br>
�������̵�:<%=dto.getUserId()%><br>
������:<%=dto.getFranchisee()%><br>
�����ݾ�:<%=dto.getPayment()%><br>
�ܾ�:<%=dto.getBalance()%><br>
��������:<%=dto.getIsSuccess()%><br>
�����Ͻ�:<%=dto.getDate()%><br>
����ī�װ� ���̵�:<%=dto.getFCategory()%><br>
�Һο���:<%=dto.getIsIns()%><br>
�Һΰ���:<%=dto.getInsMonth()%><br>
ī��Ÿ��:<%=dto.getCardType()%><br>


</body>
</html>