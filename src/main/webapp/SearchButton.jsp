<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
    String adminId = (String) request.getSession().getAttribute("adminId");
    String adminBirth = (String) request.getSession().getAttribute("adminBirth");
    if (adminId != null && adminBirth != null) {
    %>
    <p>AdminID: <%=adminId%></p>
    <p>AdminBirth: <%=adminBirth%></p>
    <!-- 사용자 정보의 다른 필드들을 출력 -->
    <%
    } else {
    %>
    <p>로그인되지 않았습니다.</p>
    <%
    }
    %>
    
	<form method="GET" action="CardServlet">
		<input type="hidden" name="action" value="list" />
		<input type="submit" value="조회" />
	</form>
	
</body>
</html>