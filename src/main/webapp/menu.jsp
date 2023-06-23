<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css" />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script src="js/bootstrap.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/default.css">
<link rel="stylesheet" href="css/nav.css">
<script src="js/jquery.js" charset="utf-8"></script>
<script src="js/nav.js" charset="utf-8"></script>

<title>왼쪽 메뉴 바</title>
</head>
<body>
<div class = "leftNav" style="float: left;">
  <div class="snb">
    <h2><i class="bi bi-credit-card"></i> Card Tracker</h2>
    <nav>
      <ul>
        <li class="on">
          <a class="nav_title" href=""><i class="bi bi-table" style="font-size: 15px; text-align:center;"></i>  조회</a>
          <ul>
            <li><a href="">고객 정보 조회</a></li>
            <ul class = "sub_sub_menu">
	            <li><a href="" style="color: #808080">신상 정보 조회</a></li>
	            <li><a href="" >보유한 카드 내역 조회</a></li>
	            <li><a href="" >계좌 정보 조회</a></li>
            </ul>
            <li><a href="">카드 상품 조회</a></li>
            <li><a href="">카드 사용 내역 조회</a></li>
            <ul class = "sub_sub_menu">
	            <li><a href="">카드 사용 내역</a></li>
	            <li><a href="">카드 월별 명세서</a></li>
	            <li><a href="">할부 내역</a></li>
            </ul>
            
          </ul>
        </li>
        <li><a class="nav_title" href=""><i class="bi bi-pencil-square" style="font-size: 15px; text-align:center;"></i> 수정</a>
        <ul>
            <li><a href="">고객 정보 수정</a></li>
            <li><a href="">카드 정지</a></li>
          </ul></li>
        <li>
          <a class="nav_title" href=""><i class="bi bi-bar-chart-line" style="font-size: 15px; text-align:center;"></i> 통계</a>
          <ul>
            <li><a href="">소비 카테고리 비교</a></li>
            <li><a href="">카드 추천</a></li>
          </ul>
        </li>
        <li><a class="nav_title" href=""><i class="bi bi-box-arrow-left" style="font-size: 15px; text-align:center;"></i> 로그아웃</a></li>
      </ul>
    </nav>
  </div></div>

  </div>
</body>
</html>
</body>
</html>