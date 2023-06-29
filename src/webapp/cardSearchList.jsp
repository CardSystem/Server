<%@page import="dto.CardHistoryDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 상품 별 사용 내역 조회</title>
<script>
$(document).ready(function() {
  $('#dataTable').DataTable({
    "order": [[0, "desc"]],
    "destroy" :true,
  });
});
</script>
</head>
<body>
<%@ include file="sidebar.jsp" %>

	 <!-- Begin Page Content -->
 <div class="container-fluid">
     

     <!-- 카드 사용 내역 datatable -->
     <div class="card shadow mb-4">
         <div class="card-header py-3">
             <h4 class="m-0 font-weight-bold text-primary">카드 상품 별 결제 내역 조회</h4>
         </div>
     <div class="card-body">
		
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                        	<th>결제 순번</th>
                            <th>카드ID</th>
                            <th>사용자ID</th>
                            <th>가맹점명</th>
                            <th>결제금액</th>
                            <th>계좌잔액</th>
                            <th>거래일자</th>
                            <th>카테고리명</th>
                            <th>할부여부</th>
                            <th>할부개월</th>
                            <th>신용/체크</th>
                        </tr>
                    </thead>
        			<tbody>
			        <% ArrayList<CardHistoryDto> list = (ArrayList<CardHistoryDto>) request.getAttribute("searchCardId");
			        for (CardHistoryDto data : list) { %>
			            <tr>
			            	<td><%= data.getId() %></td>
			                <td><%= data.getCardId() %></td>
			                <td><%= data.getUserId() %></td>
			                <td><%= data.getFranchisee() %></td>
			                <td><%= data.getPayment() %></td>
			                <td><%= data.getBalance() %></td>
			                <td><%= data.getDate() %></td>
			                <td><%= data.getFCategory() %></td>
			                <td><%= data.getIsIns() %></td>
			                <td><%= data.getInsMonth() %></td>
			                <td><%= data.getCardType() %></td>
			            </tr>
			        <% } %>
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
    </div>
	
</body>
</html>