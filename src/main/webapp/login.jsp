<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css" />
<link rel="stylesheet" href="css/login.css">
<title>로그인 페이지</title>
</head>
<body>


  <div class="section">
    <div class="container">
      <div style="display: flex; height: 100%; justify-content: center;">
        <div class="col-12 text-center align-self-center py-5">
        <h1>Card Tracker</h1>
          <div class="section pb-5 pt-5 pt-sm-2 text-center">
            <h6 class="mb-0 pb-3"><span>USER</span><span>ADMIN</span></h6>
                  <input class="checkbox" type="checkbox" id="reg-log" name="reg-log"/>
                  <label for="reg-log"></label>
            <div class="card-3d-wrap mx-auto">
              <div class="card-3d-wrapper">
                <div class="card-front">
                  <div class="center-wrap">
                    <div class="section text-center">
                      <h4 class="mb-4 pb-3">회원 로그인</h4>
                      <div class="form-group">
                      	<i class="bi bi-person-circle"> ID </i>
                        <input type="text" name="logid" class="form-style" placeholder="아이디를 입력해주세요." id="logid" autocomplete="off">
                      </div>  
                      <br/>
                      <div class="form-group mt-2">
                      	<i class="bi bi-calendar-check"> 생년월일 </i>
                        <input type="text" name="logbirth" class="form-style" placeholder="8자로 입력해주세요.(예)20230101" id="logbirth" autocomplete="off">
                      </div>
                      <br/>
                      <a href="#" class="btn mt-4"><i class="bi bi-box-arrow-in-right"></i>로그인하기</a>
                        </div>
                      </div>
                    </div>
                <div class="card-back">
                  <div class="center-wrap">
                    <div class="section text-center">
                      <h4 class="mb-4 pb-3">관리자 로그인</h4>
                      <div class="form-group">
                      <i class="bi bi-person-circle"> ID </i>
                        <input type="text" name="logid" class="form-style" placeholder="관리자 ID를 입력해주세요." id="logid" autocomplete="off">
                      </div>  
                      <br/>
                      <div class="form-group mt-2">
                      <i class="bi bi-gear"></i> PW </i>
                        <input type="password" name="logpass" class="form-style" placeholder="비밀번호를 입력해주세요." id="logpass" autocomplete="off">
                      </div>
                      <br/>
                      <a href="#" class="btn mt-4"><i class="bi bi-box-arrow-in-right"></i>로그인하기</a>
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