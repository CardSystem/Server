<%@page import="dto.UserResponseDto"%>
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
				<th width="5%">성별</th>
				<th width="5%">신용등급</th>
				<th width="10%">관리자정지여부</th>
				<th width="10%">연체정지여부</th>
			</tr>
		</thead>
		<tbody>
			<%
			ArrayList<UserResponseDto> list = (ArrayList<UserResponseDto>) request.getAttribute("userList");
					for (UserResponseDto data : list) {
			%>
			<tr>
				<td><%=data.getId()%></td>
				<td><%=data.getUserName()%></td>
				<td><%=data.getUserBirth()%></td>
				<td><%=data.getGender()%></td>
				<td><%=data.getCredit()%></td>
				<td><%=data.getAdminBlock()%>
					<form method="post" action="UserServlet">
						<input type="hidden" name="action" value="block" />
						<input type="hidden" name="id" value="<%=data.getId()%>" />
						<input type="hidden" name="is_blocked" value="<%=data.getAdminBlock()%>" />
						<input type="submit" value="정지" />
					</form>
					<form method="post" action="UserServlet">
						<input type="hidden" name="action" value="cancel" />
						<input type="hidden" name="id" value="<%=data.getId()%>" />
						<input type="hidden" name="is_blocked" value="<%=data.getAdminBlock()%>" />
						<input type="submit" value="취소" />
					</form>
				</td>
				<td><%=data.getDelayBlock()%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</body>
</html>