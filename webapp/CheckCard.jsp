<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script>
        // 서블릿에서 넘어온 메시지와 입력값을 확인하고 alert 창 띄우기 및 입력값 초기화
        function showAlert() {
            var message = '<%= request.getAttribute("message") %>';
            alert(message);

            document.getElementById("card_id").value = "";
            document.getElementById("user_id").value = "";
            document.getElementById("payment").value = "";
            document.getElementById("franchisee").value = "";
            document.getElementById("f_category").value = "";
        }
    </script>
</head>
<body>
<body onload="showAlert()">

서버 on
<form method="POST" action="card/check">
		<input type="hidden" name="action" value="insert">
		카드id :<input type="number" name="card_id"/><br>
		사용자id :<input type="text" name="user_id"/><br>
		가격 :<input type="number" name="payment"/><br>
		가맹점:<input type="text" name="franchisee"/><br>
		제휴카테고리 :<input type="number" name="f_category"/><br>
		<input type="submit" value="등록"/>
	</form>
</body>
</html>