<%@page import="dto.MonthlyCreditDto"%>
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
<%
//ArrayList<MonthlyCreditDto> list = (ArrayList<MonthlyCreditDto>)request.getAttribute("monthlyCreditList");
//	for(MonthlyCreditDto monthlyCreditDto : list){
	
MonthlyCreditDto monthlyCreditDto = (MonthlyCreditDto)request.getAttribute("monthlyCreditList");
%>
id : <%= monthlyCreditDto.getId() %><br>
user_id : <%= monthlyCreditDto.getUserId() %><br>
card_id : <%= monthlyCreditDto.getCardId() %><br>
discount : <%= monthlyCreditDto.getDiscount() %><br>
total : <%= monthlyCreditDto.getTotal() %><br>
pay : <%= monthlyCreditDto.getPay() %><br>
is_payed : <%= monthlyCreditDto.getIsPayed() %><br>
delay_days : <%= monthlyCreditDto.getDelayDays() %><br>
delay_price : <%= monthlyCreditDto.getDelayPrice() %><br>
start_date : <%= monthlyCreditDto.getStartDate() %><br>
end_date : <%= monthlyCreditDto.getEndDate() %><br>
title : <%= monthlyCreditDto.getTitle() %><br>
//<%
//}
//%>
</body>
</html>