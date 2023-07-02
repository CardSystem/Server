<%@page import="dto.CardHistoryResponseDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 사용 내역 조회</title>

<script src="http://code.jquery.com/jquery-3.1.1.js"></script>

<script>
  function handleInput() {
    var selectOption = document.getElementById("mySelect").value;
    var inputField = document.getElementById("customPeriodInput");
    var input = document.getElementById("inputField");
    
    if (selectOption === "custom") {
       input.value = null;
       inputField.style.display = "block";
      
    } else {
      inputField.style.display = "none";
      if(selectOption !== "title"){
       input.value = selectOption;
         document.forms["periodOption"].submit();
      }
     
    }
  }
  
  function changeInputField() {
       var keywordInput = document.getElementById("keywordInput");
       var selectElement = document.getElementById("searchForm").elements["searchType"];
       var selectedValue = selectElement.options[selectElement.selectedIndex].value;

       if (selectedValue === "customerId") {
           keywordInput.placeholder = "고객 ID를 입력하세요.";
          document.getElementById("searchForm").elements["action"].value = "searchId";
       } else if (selectedValue === "cardId") {
          keywordInput.placeholder = "카드 ID를 입력하세요.";
            document.getElementById("searchForm").elements["action"].value = "searchCardId";
       } else {
          keywordInput.placeholder = "고객ID or 카드ID을 선택하세요.";
       }
  }
</script>

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
                     <h4 class="m-0 font-weight-bold text-primary">카드 사용 내역</h4>
                 </div>
             <div class="card-body">
         <div id="inputContainer">
         <form id="searchForm" method="GET" action="CardServlet">
           <select name="searchType" onchange="changeInputField()">
              <option value="title">검색 조건</option>
             <option value="customerId">고객 ID</option>
             <option value="cardId">카드 ID</option>
           </select>
           <input id="keywordInput" type="text" name="keyword" placeholder="고객ID or 카드ID을 선택하세요.">
           <input type="hidden" name="action">
           <input class="btn btn-primary btn-sm" type="submit" value="검색">
         </form>
         
         <form name="periodOption" method="GET" action="CardServlet">
           <div class ="selectBox">
               <div id="customPeriodInput" style="display: none;">
                 <input type="text" id="inputField" name="keyword" placeholder="기간을 입력하세요.(개월단위)">
                 <input type="hidden" name="action" value="searchPeriod" />
                 <input class="btn btn-primary btn-sm" type="submit" value="조회하기">
              </div> 
              <select name="list" id="mySelect" onchange="handleInput()">
                 <option value="title">월별 조회하기</option>
                <option value="custom">직접입력</option>
               <option value="1">1개월</option>
               <option value="3">3개월</option>
              </select>
           </div>
         </form>
         </div>
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
                 <% ArrayList<CardHistoryResponseDto> list = (ArrayList<CardHistoryResponseDto>) request.getAttribute("list");
                 for (CardHistoryResponseDto data : list) { %>
                     <tr>
                        <td><%= data.getId() %></td>
                         <td><%= data.getCardId() %></td>
                         <td><%= data.getUserId() %></td>
                         <td><%= data.getFranchisee() %></td>
                         <td><%= data.getPayment() %></td>
                         <td><%= data.getBalance() %></td>
                         <td><%= data.getDate() %></td>
                         <td><%=data.getFCategory() %></td>
                         <td><%=data.getIsIns() %></td>
                         <td><%=data.getInsMonth() %></td>
                         <td><%=data.getCardType() %></td>
                     </tr>
                 <%} %>
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