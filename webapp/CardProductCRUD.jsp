<!--  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	border: 1px solid black;
	padding: 8px;
	text-align: center;
}

th {
	background-color: lightgray;
}
</style>
<title>상품 조회</title>
</head>
<body>
	<h1>여기는 카드 상품 CRUD 확인 페이지입니다.</h1>
	<form method="POST" action="product">
		<input type="hidden" name="action" value="register">
		카드 이름 : <input type="text" name="name"/><br>
		카드 타입 : <input type="text" name="type"/><br>
		카드 한도 : <input type="text" name="limit"/><br>
		카테고리 아이디: <input type="number" name="category"/><br>
		<input type="submit" value="등록"/>
	</form>
	<br>
	<form method="POST" action="product">
		<input type="hidden" name="action" value="update">
		카드 아이디: <input type="text" name="productId"/><br>
		카드 이름 : <input type="text" name="name"/><br>
		카드 타입 : <input type="text" name="type"/><br>
		카드 한도 : <input type="text" name="limit"/><br>
		카테고리 아이디: <input type="number" name="category"/><br>
		<input type="submit" value="등록"/>
	</form>
	<br>
	<form method="POST" action="product">
		<input type="hidden" name="action" value="delete">
		카드 아이디: <input type="text"	name="productId"/><br>
		<input type="submit" value="등록"/>
	</form>
</body>
</html>
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>카드 수정 삭제 추가</title>
  <link rel="stylesheet" href="resources/css/default.css">
  <link rel="stylesheet" href="resources/css/cardListTab.css">
  <script src="resources/js/jquery.js" charset="utf-8"></script>
  <script>
  $(document).ready(function(){
	  var tab = $('.tab li');

	  tab.on('click', function(){
	    var idx = $(this).index();
	    var tab_con = $(this).parents('.tab_group').children('.tab_content').eq(idx);

	    $(this).addClass('on');
	    $(this).siblings().removeClass('on');
	    tab_con.addClass('on');
	    tab_con.siblings('.tab_content').removeClass('on');
	  });
	});
  </script>
    
  <style>
  main {display: block; width: 1240px; margin: 0 auto;}
main h2 {padding: 30px 0 58px; line-height: 1; font-size: 38px; color: #333; text-align: center;}
.tab {padding: 0 0 14px;}
.tab ul {display: flex;} /* 1번 설명 */ 
.tab ul li {flex: 1; position: relative; margin: 0 0 0 -1px; border: 1px solid #d2d2d2;} /* 1번설명 */
.tab ul li.on {z-index: 2; border-color: rgb(0, 128, 255);} /* 2번 설명 */
.tab ul li.on button {background: rgb(0, 128, 255); color: #fff;}
.tab ul li button {width: 100%; height: 56px; background: #fff; font-size: 17px; color: #333;}
.tab ul li:first-child {border-radius: 2px 0 0 2px;}
.tab ul li:last-child {border-radius: 0 2px 2px 0;}
.tab_content {display:none; text-align: center; font-size: 50px; color: #000;} 
.tab_content.on {display: block;} /* 3번 설명 */
.ie9 .tab ul:after {content: ""; display: block; clear: both;}
.ie9 .tab ul li {float: left; width: 33.33%; box-sizing: border-box;}
.ie9 .tab ul.no2 li {width: 50%;}
.ie9 .tab ul.no4 li {width: 25%;}
.ie9 .tab ul.no5 li {width: 20%;}

  </style>
  
</head>
<body>
<%@ include file="sidebar.jsp" %>


       <!-- Begin Page Content -->
       <div class="container-fluid">
           
           <!-- 카드 사용 내역 datatable -->
             <div class="card shadow mb-4">
                 <div class="card-header py-3">
                     <h4 class="m-0 font-weight-bold text-primary">카드 정보 수정</h4>
                 </div>
  <main>
    <h2></h2>
    <div class="tab_group">
      <nav class="tab">
        <ul>
          <li class="on"><button>카드 상품 추가</button></li>
          <li><button>카드 상품 수정</button></li>
          <li><button>카드 상품 삭제</button></li>
        </ul>
      </nav>
      <div class="tab_content ">
      <form method="POST" action="product">
		<input type="hidden" name="action" value="register">
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">카드명</span>
  			</div>
  			<input type="text" class="form-control" name="name" placeholder="카드명을 입력하세요."  aria-describedby="basic-addon1">
		</div>
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">카드 타입</span>
  			</div>
  			<input type="text" class="form-control" name="type" placeholder="카드 타입을 입력하세요."  aria-describedby="basic-addon1">
		</div>
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">한도</span>
  			</div>
  			<input type="text" class="form-control" name="limit" placeholder="카드 한도를 입력하세요."  aria-describedby="basic-addon1">
		</div>
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">카테고리ID</span>
  			</div>
  			<input type="number" class="form-control" name="category" placeholder="제휴 카테고리 항목 ID를 입력하세요."  aria-describedby="basic-addon1">
		</div>
		<!--  
		카드 이름 : <input type="text" name="name"/><br>
		카드 타입 : <input type="text" name="type"/><br>
		카드 한도 : <input type="text" name="limit"/><br>
		카테고리 아이디: <input type="number" name="category"/><br>-->
		<button type="submit" class="btn btn-primary" value="등록">등록</button>
	</form>
	</div>
      <div class="tab_content">
      <form method="POST" action="product">
		<input type="hidden" name="action" value="update">
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">카드ID</span>
  			</div>
  			<input type="number" class="form-control" name="productId" placeholder="카드 상품 ID을 입력하세요"  aria-describedby="basic-addon1">
		</div>
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">카드명</span>
  			</div>
  			<input type="text" class="form-control" name="name" placeholder="카드명을 입력하세요"  aria-describedby="basic-addon1">
		</div>
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">카드 타입</span>
  			</div>
  			<input type="text" class="form-control" name="type" placeholder="카드명을 입력하세요"  aria-describedby="basic-addon1">
		</div>
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">한도</span>
  			</div>
  			<input type="text" class="form-control" name="limit" placeholder="카드 한도를 입력하세요"  aria-describedby="basic-addon1">
		</div>
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">카테고리ID</span>
  			</div>
  			<input type="number" class="form-control" name="category" placeholder="제휴 카테고리 항목 ID를 입력하세요"  aria-describedby="basic-addon1">
		</div>
		<button type="submit" class="btn btn-primary" value="수정">수정</button>
	</form>
	</div>
      <div class="tab_content">
      <form method="POST" action="product">
		<input type="hidden" name="action" value="delete">
		<div class="input-group mb-3">
  			<div class="input-group-prepend">
    		<span class="input-group-text" id="basic-addon1">카드ID</span>
  			</div>
  			<input type="number" class="form-control" name="productId" placeholder="카드 상품 ID을 입력하세요"  aria-describedby="basic-addon1">
		</div>
		<button type="submit" class="btn btn-primary" value="삭제">삭제</button>
	</form>
      </div>
    </div>
  </main>
    <!-- Footer -->
   <footer class="sticky-footer bg-white">
       <div class="container my-auto">
           <div class="copyright text-center my-auto">
               <span>digital Hana路 &copy; card Tracker </span>
           </div>
       </div>
   </footer>
   </div>
   <!-- End of Footer -->
</div>
</body>
</html>