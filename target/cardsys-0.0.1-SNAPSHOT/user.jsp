<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css" />
<link rel="stylesheet" href="resources/css/user.css">
<title>user 페이지</title>
</head>
<body>


  <div class="section">
    <div class="container">
      <div style="display: flex; height: 100%; justify-content: center;">
        <div class="col-12 text-center align-self-center py-5">
        <h1>Card Tracker (user)</h1>
          <div class="section pb-5 pt-5 pt-sm-2 text-center">
            <h6 class="mb-0 pb-3"><span>결제하기</span><span>계좌충전</span></h6>
                  <input class="checkbox" type="checkbox" id="reg-log" name="reg-log"/>
                  <label for="reg-log"></label>
            <div class="card-3d-wrap mx-auto">
              <div class="card-3d-wrapper">
                <div class="card-front">
                  <div class="center-wrap">
                    <div class="section-text-center">
                      <div class="form-group">
                      	<i class="bi bi-credit-card-fill"> 카드ID </i>
                        <input type="text" name="cardid" class="form-style" placeholder="아이디를 입력해주세요." id="cardid" autocomplete="off">
                      </div>  
                      <br/>
                      <div class="form-group mt-2">
                      	<i class="bi bi-coin"> 가격 </i>
                        <input type="text" name="cost" class="form-style" placeholder="결제 가격을 입력해주세요." id="cost" autocomplete="off">
                      </div>
                      <br/>
                      <div class="form-group">
                      	<i class="bi bi-shop"> 가맹점 명 </i>
                        <input type="text" name="franchisee" class="form-style" id="franchisee" autocomplete="off">
                      </div>  
                      <br/>
                      <div class="form-group mt-2">
                      	<i class="bi bi-inbox"> 제휴 카테고리 </i>
                        <input type="text" name="fcategory" class="form-style"  id="fcategory" autocomplete="off">
                      </div>
                      <br/>
                      <a href="#" class="btn mt-4"><i class="bi bi-wallet2"></i>결제하기</a>
                        </div>
                      </div>
                    </div>
                <div class="card-back">
                  <div class="center-wrap">
                    <div class="section-text-center">
                      <div class="form-group">
                      <i class="bi bi-person-bounding-box"> 계좌 번호 </i>
                        <input type="text" name="account" class="form-style" placeholder="'-'입력없이 번호만 입력해주세요." id="account" autocomplete="off">
                      </div>  
                      <br/>
                      <div class="form-group mt-2">
                      <i class="bi bi-piggy-bank"> 충전 금액 </i>
                        <input type="password" name="plusprice" class="form-style"  id="plusprice" autocomplete="off">
                      </div>
                      <br/>
                      <a href="#" class="btn mt-4"><i class="bi bi-bank"></i>충전하기</a>
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
</body>
</html>