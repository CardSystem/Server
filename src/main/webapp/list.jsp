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
ArrayList<MonthlyCreditDto> list = (ArrayList<MonthlyCreditDto>)request.getAttribute("list");
for(MonthlyCreditDto monthlyCreditDTO : list){
%>
id : <%= monthlyCreditDTO.getId() %><br>
user_id : <%= monthlyCreditDTO.getUserId() %><br>
card_id : <%= monthlyCreditDTO.getCardId() %><br>
discount : <%= monthlyCreditDTO.getDiscount() %><br>
total : <%= monthlyCreditDTO.getTotal() %><br>
pay : <%= monthlyCreditDTO.getPay() %><br>
is_payed : <%= monthlyCreditDTO.getIsPayed() %><br>
delay_days : <%= monthlyCreditDTO.getDelayDays() %><br>
delay_price : <%= monthlyCreditDTO.getDelayPrice() %><br>
<%
}
%>
</body>
</html>