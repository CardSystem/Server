<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	여기는 MonthlyCreditInput.jsp입니다.
	<form method="POST" action="CreditServlet">
		<input type="hidden" name="action" value="insert">
		user_id :<input type="text" name="user_id"/><br>
		card_id :<input type="number" name="card_id"/>
		discount :<input type="number" name="discount"/>
		total :<input type="number" name="total"/>
		pay :<input type="number" name="pay"/>
		is_payed :<input type="number" name="is_payed"/>
		delay_days :<input type="number" name="delay_days"/>
		delay_price :<input type="number" name="delay_price"/>
		start_date :<input type="text" name="start_date"/>
		end_date :<input type="text" name="end_date"/>
		title :<input type="text" name="title"/>
		<input type="submit" value="디비에 등록"/>
	</form>
</body>
</html>