<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css" />
<link rel="stylesheet" href="resources/css/user.css">
<script type="text/javascript">
	window.history.forward(); 
		function noBack(){
		window.history.forward();
	} 
</script>
<title>user 페이지</title>
</head>
<body id="page-top" onload="noBack();"
	onpageshow="if(event.persisted) noBack();" onunload="">
	<div class="section">
		<div class="container">
			<div style="display: flex; height: 100%; justify-content: center;">
				<div class="col-12 text-center align-self-center py-5">
					<h1>Card Tracker (user)</h1>
					<div class="section pb-5 pt-5 pt-sm-2 text-center">
						<h6 class="mb-0 pb-3">
							<span>체크카드</span><span>신용카드</span>
						</h6>
						<input class="checkbox" type="checkbox" id="reg-log"
							name="reg-log" /> <label for="reg-log"></label>
						<div class="card-3d-wrap mx-auto">
							<div class="card-3d-wrapper">
								<div class="card-front">
									<div class="center-wrap">
										<div class="section-text-center">
										<form method="POST" action="card/check">
											<div class="form-group">
												<i style="color: white;"> 카드ID </i> <input type="number"
													name="card_id" class="form-style"
													placeholder="카드ID를 입력해주세요." id="cardid" autocomplete="off">
											</div>
											<br />
											<div class="form-group">
												<i style="color: white;"> 유저ID </i> <input type="text"
													name="user_id" class="form-style"
													placeholder="유저ID를 입력해주세요." id="cardid" autocomplete="off">
											</div>
											<br />
											<div class="row">
												<div class="col-6">
													<i style="color: white;"> 가맹점 명 </i> <input type="text"
														name="franchisee" class="form-style" id="franchisee"
														autocomplete="off">
												</div>
												<div class="col-6">
													<i style="color: white;"> 제휴카테고리 </i> <input type="number"
														name="f_category" class="form-style" id="fcategory"
														autocomplete="off">
												</div>
											</div>
											<br />
											<div class="row">
												<div class="col-6">
													<i style="color: white;"> 가격 </i> <input type="number"
														name="payment" class="form-style" id="cost"
														autocomplete="off">
												</div>
												<div class="col-6">
													<i style="color: white;"> 할부 개월 </i> <input type="number"
														name="ins_month" class="form-style" id="ins_month"
														autocomplete="off">
												</div>
											</div>
											
											<br />
											<input type="submit" class="btn mt-4" value="결제하기">
											</form>
										</div>
									</div>
								</div>
								<div class="card-back">
									<div class="center-wrap">
										<div class="section-text-center">
											<form method="POST" action="card/credit">
											<div class="form-group">
												<i style="color: white;"> 카드ID </i> <input type="number"
													name="card_id" class="form-style"
													placeholder="카드ID를 입력해주세요." id="cardid" autocomplete="off">
											</div>
											<br />
											<div class="form-group">
												<i style="color: white;"> 유저ID </i> <input type="text"
													name="user_id" class="form-style"
													placeholder="유저ID를 입력해주세요." id="cardid" autocomplete="off">
											</div>
											<br />
											<div class="row">
												<div class="col-6">
													<i style="color: white;"> 가맹점 명 </i> <input type="text"
														name="franchisee" class="form-style" id="franchisee"
														autocomplete="off">
												</div>
												<div class="col-6">
													<i style="color: white;"> 제휴카테고리 </i> <input type="number"
														name="f_category" class="form-style" id="fcategory"
														autocomplete="off">
												</div>
											</div>
											<br />
											<div class="row">
												<div class="col-6">
													<i style="color: white;"> 가격 </i> <input type="number"
														name="payment" class="form-style" id="cost"
														autocomplete="off">
												</div>
												<div class="col-6">
													<i style="color: white;"> 할부 개월 </i> <input type="number"
														name="ins_month" class="form-style" id="ins_month"
														autocomplete="off">
												</div>
											</div>
											
											<br />
											<input type="submit" class="btn mt-4" value="결제하기">
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<form method="POST" id="logout" action="UserServlet">
		<input type="hidden" name="action" value="logout" />
	</form>
	<button class="btn btn-primary" href="#"
		onclick="document.getElementById('logout').submit(); return false;"
		style="margin-top: 100px;">로그아웃</button>

<div class="form-group">
												<form method="GET" action="AccountServlet">
													<input type="hidden" name="action" value="list" /> <input
														type="submit" class="btn mt-4" value="계좌보기" />
												</form>
											</div>
</body>
</html>