<%@page import="service.ProductService"%>
<%@page import="java.util.List"%>
<%@page import="dto.ProductResponseDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	border: 1px solid black;
	padding: 8px;
	text-align: center;
}

th {
	background-color: lightgray;
}
</style>
<title>상품 조회</title>
</head>
<body>
	<h1>여기는 카드 상품 조회 페이지입니다.</h1>
	<h1>카드 상품 조회</h1>
	<table>
		<tr>
			<th width="5%">카드 아이디</th>
			<th width="5%">카드 이름</th>
			<th width="5%">카드 타입</th>
			<th width="5%">카드 한도</th>
			<th width="5%">카테고리 아이디</th>
			<th width="5%">카테고리명</th>
			<th width="5%">할인율</th>
		</tr>
		 <%
		@SuppressWarnings("unchecked")
        List<ProductResponseDto> productList = (List<ProductResponseDto>) request.getAttribute("productList");
		 if (productList != null && !productList.isEmpty()) {	
		 for (ProductResponseDto product : productList) {
        %>
        <tr>
            <td><%=product.getId()%></td>
            <td><%=product.getCardName()%></td>
            <td><%=product.getCardType()%></td>
            <td><%=product.getCardLimit()%></td>
            <td><%=product.getCategoryId()%></td>
            <td><%=product.getCategoryName()%></td>
            <td><%=product.getDiscount()%>%</td>
        </tr>
        <% 
		 }
        } else {
        	%>
        	<tr>
        	<td colspan="7">조회된 상품이 없습니다.</td>
        	</tr>
        <% 
        }
        %>
	</table>
</body>
</html>