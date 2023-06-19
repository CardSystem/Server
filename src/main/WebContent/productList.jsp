<%@page import="java.util.List"%>
<%@page import="domain.ProductResponseDTO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 조회</title>
</head>
<body>
	<h1>여기는 카드 상품 조회 페이지입니다.</h1>
	 <h1>카드 상품 조회</h1>
    <table>
        <tr>
            <th>카드 아이디</th>
            <th>카드 이름</th>
            <th>카드 타입</th>
            <th>카드 한도</th>
            <th>카테고리 아이디</th>
        </tr>
        <% List<ProductResponseDTO> productList = (List<ProductResponseDTO>) request.getAttribute("productList");
        //(List<ProductResponseDTO>) request.getAttribute("productList");
           for (ProductResponseDTO product : productList) { %>
            <tr>
                <td><%= product.getProductId() %></td>
                <td><%= product.getCardName() %></td>
                <td><%= product.getCardType() %></td>
                <td><%= product.getCardLimit() %></td>
                <td><%= product.getCategoryId() %></td>
            </tr>
        <% } %>
    </table>
</body>
</html>