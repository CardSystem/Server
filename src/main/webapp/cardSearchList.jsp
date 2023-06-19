<%@page import="dto.CardHistoryDTO"%>
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

<div class="result_table" style="width:80%;float: right;">
<h1>카드 상품 별 결제 내역</h1>
	<table class="table table-hover">
	<tr>
			<td>카드ID</td>
			<td>사용자ID</td>
			<td>가맹점명</td>
			<td>결제금액</td>
			<td>계좌잔액</td>
			<td>거래일자</td>
			<td>카테고리명</td>
			<td>할부여부</td>
			<td>할부개월</td>
			<td>신용/체크</td>
		</tr>
		<%
		ArrayList<CardHistoryDTO> list = (ArrayList<CardHistoryDTO>)request.getAttribute("searchCardId");
				for(CardHistoryDTO data : list){
		%>
		<tr>
			<td><%= data.getCardId() %></td>
			<td><%= data.getUserId() %></td>
			<td><%= data.getFranchisee() %></td>
			<td><%= data.getPayment() %></td>
			<td><%= data.getBalance() %></td>
			<td><%= data.getDate() %></td>
			<td><%= data.getfCategory() %></td>
			<td><%= data.getIsIns() %></td>
			<td><%= data.getInsMonth() %></td>
			<td><%= data.getCardType() %></td>
		</tr>
		<%
		}
		%>
	</table>
	</div>
</body>
</html>