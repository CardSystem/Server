<%@page import="dto.CardDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.table {
	border: "1px solid";
}
</style>
</head>
<body>
	<table class="table">
		<thead>
			<tr>
				<th width="5%">번호</th>
				<th width="15%">카드이름</th>
				<th width="20%">카드번호</th>
				<th width="15%">발급일</th>
				<th width="10%">발급지점</th>
				<th width="10%">발급자</th>
				<th width="5%">체크/신용</th>
				<th width="15%">유효기간</th>
				<th width="5%">정지여부</th>
			</tr>
		</thead>
		<tbody>
			<%
			ArrayList<CardDto> list = (ArrayList<CardDto>) request.getAttribute("list");
			for(CardDto data : list){
			%>
			<tr>
				<td><%=data.getId()%></td>
				<td><%=data.getCardId()%></td>
				<td><%=data.getCardNum()%></td>
				<td><%=data.getIssuedDate()%></td>
				<td><%=data.getAgency()%></td>
				<td><%=data.getIssuer()%></td>
				<td><%=data.getCardType()%></td>
				<td><%=data.getValidity()%></td>
				<td><%=data.getIsStopped()%>
					<form method="post" action="CardServlet">
						<input type="hidden" name="action" value="changeIsStop" />
						<input type="hidden" name="id" value="<%=data.getId()%>" />
						<input type="hidden" name="is_stopped" value="<%=data.getIsStopped()%>" />
						<input type="submit" value="변경" />
					</form></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</body>
</html>