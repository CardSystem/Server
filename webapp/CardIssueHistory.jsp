<%@page import="dto.CardResponseDto"%>
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
<%@ include file="sidebar.jsp" %>


       <!-- Begin Page Content -->
       <div class="container-fluid">
           
           <!-- 카드 사용 내역 datatable -->
             <div class="card shadow mb-4">
                 <div class="card-header py-3">
                     <h4 class="m-0 font-weight-bold text-primary">카드 사용 내역</h4>
                 </div>
             <div class="card-body">
	<div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
		<thead>
			<tr>
				<th>번호</th>
				<th>카드번호</th>
				<th>발급일</th>
				<th>발급지점</th>
				<th>발급자</th>
				<th>체크/신용</th>
				<th>유효기간</th>
				<th>정지여부</th>
				<th>총결제</th>
				<%--
				<th>정지처리</th>
				--%>
			</tr>
		</thead>
		<tbody>
			<%
			ArrayList<CardResponseDto> list = (ArrayList<CardResponseDto>) request.getAttribute("cardIssueHistoryList");
				for(CardResponseDto data : list){
			%>
			<tr>
				<td><%=data.getId()%></td>
				<td><%=data.getCardNum()%></td>
				<td><%=data.getIssuedDate()%></td>
				<td><%=data.getAgency()%></td>
				<td><%=data.getIssuer()%></td>
				<td><%=data.getCardType()%></td>
				<td><%=data.getValidity()%></td>
				<td><%=data.getIsStopped()%></td>
				<td><%=data.getTotalPayment()%></td>
				<%-- 
				<td>
					<form method="post" action="CardServlet">
						<input type="hidden" name="action" value="block" />
						<input type="hidden" name="id" value="<%=data.getId()%>" />
						<input type="hidden" name="is_stopped" value="<%=data.getIsStopped()%>" />
						<input type="submit" value="정지" />
					</form>
					<form method="post" action="CardServlet">
						<input type="hidden" name="action" value="cancel" />
						<input type="hidden" name="id" value="<%=data.getId()%>" />
						<input type="hidden" name="is_stopped" value="<%=data.getIsStopped()%>" />
						<input type="submit" value="해제" />
					</form>
				</td>
				--%>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	</div>
                </div>
     		</div>

   <!-- Footer -->
   <footer class="sticky-footer bg-white">
       <div class="container my-auto">
           <div class="copyright text-center my-auto">
               <span>digital Hana路 &copy; card Tracker </span>
           </div>
       </div>
   </footer>
   <!-- End of Footer -->
</div>
</body>
</html>