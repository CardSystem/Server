<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>


<title>adminPage</title>
  
</head>
<body>
<%@ include file="menu.jsp" %>
  
  <div class="result_table" style="width:80%;float: right;">
  	카드 결제 내역 조회하기
	<form method="GET" action="CardControllerServlet">
		<input type="hidden" name="action" value="list" />
		<input class="btn btn-success" type="submit" value="조회" />
	</form>
  </div>
</body>
</html>
</body>
</html>
