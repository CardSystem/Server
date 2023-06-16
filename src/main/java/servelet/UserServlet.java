package servelet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static String id;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String order = request.getParameter("action");
		String url;
		switch (order) {
		case "login":
			url = doLogin(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 한글깨짐 방지
		doGet(request, response);
	}
	
	private String doLogin(HttpServletRequest request, HttpServletResponse response) {
		id = request.getParameter("id");
	    String birth = request.getParameter("userBirth");
	    try {
	        int loginResult = UserDAO.login(id, birth);
	        if (loginResult == 1) {
	        	if(id.contains("Admin")) {
	        		request.getSession().setAttribute("adminId", id);
		            request.getSession().setAttribute("adminBirth", birth);
		            return "SearchButton.jsp";
	        	}
	            // 로그인 성공 시 사용자 정보를 세션에 저장
	            request.getSession().setAttribute("userId", id);
	            request.getSession().setAttribute("userBirth", birth);
	        } else {
	            // 로그인 실패한 경우 세션에서 사용자 정보를 제거
	            request.getSession().removeAttribute("userId");
	            request.getSession().removeAttribute("userBirth");
	            request.getSession().removeAttribute("adminId");
	            request.getSession().removeAttribute("adminBirth");
	            return "User.jsp";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "ShowMyAccount.jsp";
	}
}