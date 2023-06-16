<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dto.AccountDTO"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
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
    <table class="table">
        <thead>
            <tr>
                <th width="40%">계좌번호</th>
                <th width="40%">잔액</th>
                <th width="10%">은행</th>
                <th width="10%">정지여부</th>
            </tr>
        </thead>
        <tbody>
            <%
            // 이전 코드를 유지하면서 null 값을 방지하기 위해 null 체크 또는 변수 초기화를 수행합니다.
            ArrayList<AccountDTO> list = (ArrayList<AccountDTO>) request.getAttribute("list");
            if (list != null) {
                for (AccountDTO data : list) {
            %>
            <tr>
                <td><%=data.getAccountNum()%></td>
                <td><%=data.getBalance()%>
                	<form method="post" action="AccountServlet">
						<input type="hidden" name="action" value="depositAccount" />
						<input type="hidden" name="accountNum" value="<%=data.getAccountNum()%>" />
						<input type="number" name="balance"/>
						<input type="submit" value="충전" />
					</form>
                </td>
                <td><%=data.getBankName()%></td>
                <td><%=data.getIsStopped()%></td>
            </tr>
            <%
                }
            }
            %>
        </tbody>
    </table>
</body>
</html>