package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import dto.AccountDTO;

@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String userId = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        userId = (String) session.getAttribute("userId");
        String order = request.getParameter("action");
        String url;
        switch (order) {
            case "list":
                url = doList(request, response);
                RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                dispatcher.forward(request, response);
                break;
                
            case "depositAccount":
            	url = doDepositAccount(request, response);
    			response.sendRedirect(url);
    			break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8"); // 한글깨짐 방지
        doGet(request, response);
    }

    private String doList(HttpServletRequest request, HttpServletResponse response) {
        try {
            ArrayList<AccountDTO> accountList = AccountDAO.selectAccount(userId);
            if (accountList != null && !accountList.isEmpty()) {
                request.setAttribute("list", accountList);
            }
            return "ChooseAccount.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "데이터베이스 오류가 발생했습니다."); // 오류 메시지 설정
            return "Login.jsp"; // 오류 페이지로 이동
        }
    }
    
    private String doDepositAccount(HttpServletRequest request, HttpServletResponse response) {
        String accountNum = request.getParameter("accountNum");
        long balance = Long.parseLong(request.getParameter("balance"));
        
        try {
            AccountDAO.depositAccount(accountNum, balance);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String redirectUrl = request.getContextPath() + "/AccountServlet?action=list";
        return redirectUrl;
    }
}