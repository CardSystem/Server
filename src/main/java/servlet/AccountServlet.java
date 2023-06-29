package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDao;
import dto.AccountResponseDto;
import service.AccountService;

@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AccountService accountService;
    
    public AccountServlet() {
    	AccountDao dao = new AccountDao();
    	this.accountService = new AccountService(dao);
    }
    
    public AccountServlet(AccountService accountService) {
    	this.accountService = accountService;
    }
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
            	RequestDispatcher dispatcher1 = request.getRequestDispatcher(doList(request, response));
    			dispatcher1.forward(request, response);
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
            List<AccountResponseDto> accountList = accountService.selectAccount(userId);
            if (accountList != null && !accountList.isEmpty()) {
                request.setAttribute("list", accountList);
            }
            return "ChooseAccount.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "Login.jsp";
        }
    }
    
    private String doDepositAccount(HttpServletRequest request, HttpServletResponse response) {
        String accountNum = request.getParameter("accountNum");
        String balance = request.getParameter("balance");
        
        try {
        	accountService.checkMoney(request, response, accountNum, balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}