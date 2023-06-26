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
<link href="css/lookup.css" rel="stylesheet">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<script>
  function handleInput() {
    var selectOption = document.getElementById("mySelect").value;
    var inputField = document.getElementById("customPeriodInput");
    var input = document.getElementById("inputField");
    
    if (selectOption === "custom") {
    	input.value = null;
    	inputField.style.display = "block";
      
    } else {
      inputField.style.display = "none";
      if(selectOption !== "title"){
    	input.value = selectOption;
      	document.forms["periodOption"].submit();
      }
     
    }
  }
  
  function changeInputField() {
	    var keywordInput = document.getElementById("keywordInput");
	    var selectElement = document.getElementById("searchForm").elements["searchType"];
	    var selectedValue = selectElement.options[selectElement.selectedIndex].value;

	    if (selectedValue === "customerId") {
	        keywordInput.placeholder = "고객 ID를 입력하세요.";
	    	document.getElementById("searchForm").elements["action"].value = "searchId";
	    } else if (selectedValue === "cardId") {
	    	keywordInput.placeholder = "카드 ID를 입력하세요.";
	      	document.getElementById("searchForm").elements["action"].value = "searchCardId";
	    } else {
	    	keywordInput.placeholder = "고객ID or 카드ID을 선택하세요.";
	    }
  }
</script>
</head>
<body>
<%@ include file="menu.jsp" %>

<div class="result_table" style="width:80%;float: right;">
	<h1>카드 사용 내역</h1>
	<form method="GET" action="CardControllerServlet">
		<input type="hidden" name="action" value="recentlist" />
		<input type="submit" class="btn btn-secondary" value="최근 결제 일 순" />
	</form>
	
	<form id="searchForm" method="GET" action="CardControllerServlet">
	  <select name="searchType" onchange="changeInputField()">
	  	<option value="title">검색 조건</option>
	    <option value="customerId">고객 ID</option>
	    <option value="cardId">카드 ID</option>
	  </select>
	  <input id="keywordInput" type="text" name="keyword" placeholder="고객ID or 카드ID을 선택하세요.">
	  <input type="hidden" name="action">
	  <input type="submit" value="검색">
	</form>

	<form name="periodOption" method="GET" action="CardControllerServlet">
	  <div class ="selectBox">
		  <select name="list" id="mySelect" onchange="handleInput()">
		  	<option value="title">월별 조회하기</option>
		    <option value="custom">직접입력</option>
			<option value="1">1개월</option>
			<option value="3">3개월</option>
		  </select>
		  
		  <div id="customPeriodInput" style="display: none;">
			  <input type="text" id="inputField" name="keyword" placeholder="기간을 입력하세요.(개월단위)">
			  <input type="hidden" name="action" value="searchPeriod" />
			  <input type="submit" value="조회하기">
		  </div>
	  </div>
	</form>



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
                <td><%= data.getFCategory() %></td>
                <td><%= data.getIsIns() %></td>
                <td><%= data.getInsMonth() %></td>
                <td><%= data.getCardType() %></td>
            </tr>
        <% } %>
</table>

</body>
</html>