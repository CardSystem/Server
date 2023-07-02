<%@page import="dto.CardHistoryDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css" />

 	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="resources/css/default.css">
	
	<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
	
    <!-- Custom fonts for this template -->
    <link href="resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="resources/css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="resources/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    
    <!-- bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>    
	<style>
		label {
		display:none;
		}
	</style>
	
	<link href="resources/css/lookup.css" rel="stylesheet">
	<script type="text/javascript">
		window.history.forward(); 
			function noBack(){
			window.history.forward();
		} 
	</script>
	<title>사이드 바</title>
</head>

<body id="page-top" onload="noBack();" onpageshow="if(event.persisted) noBack();" onunload="">

    <!-- Page Wrapper -->
    <div id="wrapper" >

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - projectName -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="bi bi-credit-card" style="color:white;"></i>
                </div>
                <div class="sidebar-brand-text mx-3" style="color:white;"> Card Tracker</div>
            </a>

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>
            
            <!-- Nav Item - 조회 Menu -->
            <li class="nav-item active">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
                    aria-expanded="true" aria-controls="collapseTwo">
                    <i class="bi bi-table" ></i>
                    <span>조회</span>
                </a>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">- 고객 정보 조회</h6>
                        <form method="GET" id="showUserList" action="UserServlet">
							<input type="hidden" name="action" value="userList" />
						</form>
                        <a class="collapse-item" href="#" onclick="document.getElementById('showUserList').submit(); return false;">신상 정보 조회</a>
                        
                        <form method="GET" id="cardIssueHistory" action="CardServlet">
							<input type="hidden" name="action" value="cardIssueHistoryList" />
						</form>
                        <a class="collapse-item" href="#" onclick="document.getElementById('cardIssueHistory').submit(); return false;">카드 발급 내역 조회</a>
                        
                        <a class="collapse-item" >계좌 정보 조회</a>
                         <h6 class="collapse-header">- 카드 상품 조회</h6>
                        <a class="collapse-item" >카드 상품 조회</a>
                        <h6 class="collapse-header">- 카드 사용 내역 조회</h6>
                        <form class="collapse-item" id="cardForm" method="GET" action="CardServlet" style="display: none;">
							 <input type="hidden" name="action" value="list" />
						</form>	
						<a class="collapse-item" href="#" onclick="document.getElementById('cardForm').submit(); return false;">카드 사용 내역</a>
                        <a class="collapse-item" >카드 월별 명세서</a>
                        <a class="collapse-item" >할부 내역</a>
                    </div>
                </div>
            </li>

            <!-- Nav Item - 수정 Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
                    aria-expanded="true" aria-controls="collapseUtilities">
                    <i class="bi bi-pencil-square"></i>
                    <span>수정</span>
                </a>
                <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
                    data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">- 정보 수정</h6>
                        <a class="collapse-item">고객 정보 수정</a>
                        <a class="collapse-item">카드 정보 수정</a>
                        <a class="collapse-item" >카드 정지</a>
                    </div>
                </div>
            </li>
            
        </ul>
        <!-- End of Sidebar -->
        
        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
                        
                <!-- Topbar 로그아웃 버튼 -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                    <div class="ml-auto">
                    <form method="POST" id="logout" action="UserServlet">
						<input type="hidden" name="action" value="logout" />
					</form>
                    <a class="nav-link" href="#" onclick="document.getElementById('logout').submit(); return false;">
                    <i class="bi bi-box-arrow-left" style="font-size: 15px; text-align:center; "></i>
                    <span>로그아웃</span></a>
                    </div>
                </nav>
                <!-- End of Topbar -->
        

    <!-- Bootstrap core JavaScript-->
    <script src="resources/vendor/jquery/jquery.min.js"></script>
    <script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="resources/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="resources/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="resources/vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="resources/vendor/datatables/dataTables.bootstrap4.min.js"></script>



</body>

</html>