<%@page import="dto.ProductDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="menu.jsp" %>
<h1>월별 결제 내역</h1>

	<table class="table table-hover">
	<tr>
			<td>카드ID</td>
			<td>카드이름</td>
			<td>카드유형</td>
			<td>카드한도</td>
			<td>제휴아이디</td>
	</tr>
	<%
	ArrayList<ProductDto> list = (ArrayList<ProductDto>)request.getAttribute("productList");
	for(ProductDto data : list){
	%>
	<tr>
		<td><%= data.getId() %></td>
		<td><%= data.getCardName() %></td>
		<td><%= data.getCardType() %></td>
		<td><%= data.getCardLimit() %></td>
		<td><%= data.getCategoryId() %></td>


	</tr>
	<%
	}
	%>
	</table>

</body>
</html>