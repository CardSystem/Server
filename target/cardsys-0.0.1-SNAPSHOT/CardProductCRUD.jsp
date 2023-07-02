
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
      <div class="tab_content on">
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