<%@page import="service.ProductService"%>
<%@page import="java.util.List"%>
<%@page import="dto.ProductResponseDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 조회</title>
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
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
	
	 <!-- Content Wrapper -->
       <!-- Begin Page Content -->
       <div class="container-fluid">
           
           <!-- 카드 사용 내역 datatable -->
             <div class="card shadow mb-4">
                 <div class="card-header py-3">
                     <h4 class="m-0 font-weight-bold text-primary">카드 상품 조회</h4>
                     </div>
                     <div class="card-body">
                     
					<div class="table-responsive">
                       <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                        	<th>카드ID</th>
                            <th>카드명</th>
                            <th>카드타입</th>
                            <th>한도</th>
                            <th>카테고리ID</th>
                            <th>카테고리명</th>
                            <th>할인율</th>

                        </tr>
                    </thead>
        			<tbody>
			        <% @SuppressWarnings("unchecked")
			        List<ProductResponseDto> productList = (List<ProductResponseDto>) request.getAttribute("productList");
					 if (productList != null && !productList.isEmpty()) {	
					 for (ProductResponseDto product : productList) {  %>
			            <tr>
			        <td><%=product.getId()%></td>
            		<td><%=product.getCardName()%></td>
            		<td><%=product.getCardType()%></td>
            		<td><%=product.getCardLimit()%></td>
            		<td><%=product.getCategoryId()%></td>
            		<td><%=product.getCategoryName()%></td>
                    <td><%=product.getDiscount()%>%</td>
			            <% 
		 				}
        			} else {
        					%>
        					<tr>
        					<td colspan="7">조회된 상품이 없습니다.</td>
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
    </div>
</body>
</html>