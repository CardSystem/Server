<%@page import="dto.CreditCardResponseDto"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>

</head>
<body>
<%
CreditCardResponseDto dto=(CreditCardResponseDto)request.getAttribute("data");
int code=dto.getStatusCode();
String msg=dto.getStatusMsg();
response.setContentType("text/html; charset=UTF-8");
PrintWriter script = response.getWriter();

	%>
	
	<script>
	console.log("<%=dto.getStatusMsg() %>")
	alert("<%=dto.getStatusMsg() %>");
	location.href='/testpro/user.jsp'
	</script>




�����ڵ�:<%=dto.getStatusCode() %><br>
���¸޽���:<%=dto.getStatusMsg() %><br>
ī����̵�:<%=dto.getCardId()%><br>
�������̵�:<%=dto.getUserId()%><br>
������:<%=dto.getFranchisee()%><br>
�����ݾ�:<%=dto.getPayment()%><br>
�ܾ�:<%=dto.getBalance()%><br>
��������:<%=dto.getIsSuccess()%><br>
�����Ͻ�:<%=dto.getDate()%><br>
����ī�װ��� ���̵�:<%=dto.getFCategory()%><br>
�Һο���:<%=dto.getIsIns()%><br>
�Һΰ���:<%=dto.getInsMonth()%><br>
ī��Ÿ��:<%=dto.getCardType()%><br>


</body>
</html>