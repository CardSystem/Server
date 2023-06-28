<%@page import="dto.UserDto"%>
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
<table class="table">
		<thead>
			<tr>
				<th width="40%">id</th>
				<th width="15%">이름</th>
				<th width="15%">생년월일</th>
				<th width="10%">성별</th>
				<th width="10%">신용등급</th>
				<th width="10%">정지여부</th>
			</tr>
		</thead>
		<tbody>
			<%
			ArrayList<UserDto> list = (ArrayList<UserDto>) request.getAttribute("userList");
				for (UserDto data : list) {
			%>
			<tr>
				<td><%=data.getId()%></td>
				<td><%=data.getUserName()%></td>
				<td><%=data.getUserBirth()%></td>
				<td><%=data.getGender()%></td>
				<td><%=data.getCredit()%></td>
				<td><%=data.getIsBlocked()%>
					<form method="post" action="UserServlet">
						<input type="hidden" name="action" value="changeIsBlock" />
						<input type="hidden" name="id" value="<%=data.getId()%>" />
						<input type="hidden" name="is_blocked" value="<%=data.getIsBlocked()%>" />
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