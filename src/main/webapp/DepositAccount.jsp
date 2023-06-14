<%@page import="dto.UserDTO"%>
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
String userId = (String) request.getSession().getAttribute("userId");
String userBirth = (String) request.getSession().getAttribute("userBirth");
if (userId != null && userBirth != null) {
%>
    <p>ID: <%= userId %></p>
    <p>Birth: <%= userBirth %></p>
    <!-- 사용자 정보의 다른 필드들을 출력 -->
<%
} else {
%>
    <p>로그인되지 않았습니다.</p>
<%
}
%>
</body>
</html>