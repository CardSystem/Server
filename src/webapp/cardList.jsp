<%@page import="dto.CardHistoryDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 사용 내역 조회</title>

</head>
<body>
<%@ include file="menu.jsp" %>

<div class="result_table" style="width:80%;float: right;">
	<h1>카드 결제 내역</h1>

	<form method="GET" action="CardControllerServlet">
		<input type="hidden" name="action" value="recentlist" />
		<input type="submit" class="btn btn-secondary" value="최근 결제 일 순" />
	</form>
	
	<form  method="GET" action="CardControllerServlet">
	  <input type="text" name="keyword" placeholder="고객ID를 입력하세요.">
	  <input type="hidden" name="action" value="searchId" />
	  <input type="submit" value="검색">
	</form>
	
	<form  method="GET" action="CardControllerServlet">
	  <input type="text" name="keyword" placeholder="카드ID를 입력하세요.">
	  <input type="hidden" name="action" value="searchCardId" />
	  <input type="submit" value="검색">
	</form>
	
	<form  method="GET" action="CardControllerServlet">
	  <input type="text" name="keyword" placeholder="기간을 입력하세요.(개월단위)">
	  <input type="hidden" name="action" value="searchPeriod" />
	  <input type="submit" value="조회하기">
	</form>
	
	
    <select id="monthOption">
        <option value="custom">직접입력</option>
        <option value="1">1개월</option>
        <option value="3">3개월</option>
    </select>
    

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
    
        <% ArrayList<CardHistoryDTO> list = (ArrayList<CardHistoryDTO>) request.getAttribute("list");
        for (CardHistoryDTO data : list) { %>
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
        <% } %>
</table>
</body>
</html>