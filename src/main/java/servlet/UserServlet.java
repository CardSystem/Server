package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exception.BusinessException;
import dao.UserDao;
import service.UserService;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static String id;

	private UserService userService;

	public void init() {
		userService = new UserService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String order = request.getParameter("action");
		String url;
		switch (order) {
		case "login":
			url = doLogin(request, response);
			if (url != null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
			}
			break;
		case "userList":
			url = doList(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			break;
		case "changeIsBlock":
			url = doChangeIsBlock(request, response);
			response.sendRedirect(url);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 한글깨짐 방지
		doGet(request, response);
	}

	private String doLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		id = request.getParameter("id");
		String birth = request.getParameter("userBirth");
		try {
			return userService.checkLogin(request, response, id, birth);
		} catch (SQLException | BusinessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("userList", UserDao.showUserList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "UserList.jsp";
	}

	private String doChangeIsBlock(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Integer isStop = Integer.parseInt(request.getParameter("is_blocked"));
		try {
			UserService.controlIsBlock(isStop, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 변경 후 다시 목록으로 돌아갈 수 있도록 Redirect URL을 설정
		String redirectUrl = request.getContextPath() + "/UserServlet?action=userList";
		return redirectUrl;
	}
}