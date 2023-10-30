<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="dto.AccountResponseDto"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css" />

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="resources/css/default.css">

<script src="http://code.jquery.com/jquery-3.1.1.js"></script>

<!-- Custom fonts for this template -->
<link href="resources/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="resources/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="resources/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">

<!-- bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<style>
label {
	display: none;
}
.custom-input {
  border: 1px solid black;
  outline: none;
}
</style>

<link href="resources/css/lookup.css" rel="stylesheet">
<title>Insert title here</title>
<script type="text/javascript">
	window.history.forward(); 
		function noBack(){
		window.history.forward();
	} 
</script>
<title>user 페이지</title>
</head>
<body id="page-top" onload="noBack();"onpageshow="if(event.persisted) noBack();" onunload="">
	<%-- 
    <%
    String userId = (String) request.getSession().getAttribute("userId");
            String userBirth = (String) request.getSession().getAttribute("userBirth");
            if (userId != null && userBirth != null) {
    %>
    <p>ID: <%=userId%></p>
    <p>Birth: <%=userBirth%></p>
    <!-- 사용자 정보의 다른 필드들을 출력 -->
    <%
    } else {
    %>
    <p>로그인되지 않았습니다.</p>
    <%
    }
    %>
    --%>
	<div class="container-fluid">

		<!-- 카드 사용 내역 datatable -->
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h4 class="m-0 font-weight-bold text-primary">계좌 잔액 충전</h4>
			</div>
			<div class="card-body">
				<div class="table-responsive">
					<table class="table table-bordered" id="dataTable" width="100%"
						cellspacing="0">
						<thead>
							<tr>
								<th>계좌번호</th>
								<th>잔액</th>
								<th>은행</th>
								<th>정지여부</th>
								<th>충전</th>
							</tr>
						</thead>
						<tbody>
							<%
            // 이전 코드를 유지하면서 null 값을 방지하기 위해 null 체크 또는 변수 초기화를 수행합니다.
                        ArrayList<AccountResponseDto> list = (ArrayList<AccountResponseDto>) request.getAttribute("list");
                                    if (list != null) {
                                        for (AccountResponseDto data : list) {
            %>
							<tr>
								<td><%=data.getAccountNum()%></td>
								<td><%=data.getBalance()%></td>
								<td><%=data.getBankName()%></td>
								<td><%=data.getIsStopped()%></td>
								<td>
									<form method="post" action="AccountServlet">
										<input type="hidden" name="action" value="depositAccount" />
										<input type="hidden" name="accountNum" value="<%=data.getAccountNum()%>" />
										<input type="number"name="balance" class="custom-input"/>
										<input type="submit" class="btn btn-primary" value="충전" />
									</form>
								</td>
							</tr>
							<%
                }
            }
            %>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<button class="btn btn-primary" onclick="location.href='user.jsp'">뒤로가기</button>
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