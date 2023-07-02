<%@page import="dto.UserResponseDto"%>
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
<%@ include file="sidebar.jsp" %>


       <!-- Begin Page Content -->
       <div class="container-fluid">
           
           <!-- 카드 사용 내역 datatable -->
             <div class="card shadow mb-4">
                 <div class="card-header py-3">
                     <h4 class="m-0 font-weight-bold text-primary">고객 목록 조회 / 수정</h4>
                 </div>
             <div class="card-body">
	<div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
		<thead>
			<tr>
				<th>id</th>
				<th>이름</th>
				<th>생년월일</th>
				<th>성별</th>
				<th>신용등급</th>
				<th>관리자정지여부</th>
				<th>연체정지여부</th>
				<th>정지</th>
				<th>취소</th>
			</tr>
		</thead>
		<tbody>
			<%
			ArrayList<UserResponseDto> list = (ArrayList<UserResponseDto>) request.getAttribute("userList");
					for (UserResponseDto data : list) {
			%>
			<tr>
				<td><%=data.getId()%></td>
				<td><%=data.getUserName()%></td>
				<td><%=data.getUserBirth()%></td>
				<td><%=data.getGender()%></td>
				<td><%=data.getCredit()%></td>
				<td><%=data.getAdminBlock()%></td>
				<td><%=data.getDelayBlock()%></td>
				<td>
					<form method="post" action="UserServlet">
						<input type="hidden" name="action" value="block" />
						<input type="hidden" name="id" value="<%=data.getId()%>" />
						<input type="hidden" name="is_blocked" value="<%=data.getAdminBlock()%>" />
						<button type="submit" class="btn btn-danger">정지</button>
					</form>
				</td>
				<td>
					<form method="post" action="UserServlet">
						<input type="hidden" name="action" value="cancel" />
						<input type="hidden" name="id" value="<%=data.getId()%>" />
						<input type="hidden" name="is_blocked" value="<%=data.getAdminBlock()%>" />
						<button type="submit" class="btn btn-success">취소</button>
					</form>
				</td>
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