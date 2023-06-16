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
	<h1>카드 결제 내역</h1>

	<form method="GET" action="CardControllerServlet">
		<input type="hidden" name="action" value="recentlist" />
		<input type="submit" value="최근 결제 일 순" />
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
	
	<form method="GET" action="CardControllerServlet">
		<input type="hidden" name="action" value="searchPeriod" />
		<select name="period" onchange="handlePeriodChange(this.value)">
			<option value="custom">직접입력</option>
		    <option value="1">1개월</option>
		    <option value="3">3개월</option>
		</select>
	
		<div id="customPeriodInput">
			<input type="hidden" name="action" value="searchPeriod" />
		    <input type="text" name="customPeriod" placeholder="기간을 입력하세요.(개월단위)" />
		    <button type="button" onclick="sendDataToServer(document.querySelector('input[name=customPeriod]').value)">검색</button>
	  	</div>
	</form>
	
	<table border="1">
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
		ArrayList<CardHistoryDTO> list = (ArrayList<CardHistoryDTO>)request.getAttribute("list");
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
	
	<script>
	function handlePeriodChange(selectedValue) {
		  if (selectedValue === 'custom') {
		    document.getElementById('customPeriodInput').style.display = 'block';
		  } else {
		    document.getElementById('customPeriodInput').style.display = 'none';
		    sendDataToServer(selectedValue);
		  }
		}
	
	function sendDataToServer(period) {
		  var xhr = new XMLHttpRequest();
		  xhr.open('GET', 'CardControllerServlet?action=searchPeriod&period=' + encodeURIComponent(period), true);
		  xhr.onreadystatechange = function() {
		    if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
		      // AJAX 요청 성공시 수행할 작업
		      console.log(xhr.responseText);
		    }
		  };
		  xhr.send();
		}
	</script>
	
</body>
</html>