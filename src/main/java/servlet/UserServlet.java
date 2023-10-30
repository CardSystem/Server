package servlet;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.BusinessException;
import dao.UserDao;
import dto.UserResponseDto;
import service.UserService;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final UserService userService;
	
	public UserServlet() {
		UserDao dao = new UserDao();
		this.userService = new UserService(dao);
	}
	
	public UserServlet(UserService userService) {
		this.userService = userService;
	}
	public static String id;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
		
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
		case "logout":
		    doLogout(request, response);
		    break;
		case "userList":
			url = doList(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			break;
		case "block":
			url = block(request, response);
			response.sendRedirect(url);
			break;
		case "cancel":
			url = cancel(request, response);
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
			String url = userService.checkLogin(request, response, id, birth);
	        if (url != null) {
	            // url이 "userList"인 경우 UserServlet의 doList 메서드 호출
	            if (url.equals("userList")) {
	                return doList(request, response);
	            }
	        }
		} catch (SQLException | BusinessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    try {
	        userService.logout(request, response);
	    } catch (BusinessException e) {
	        e.printStackTrace();
	    }
	    if (!response.isCommitted()) {
	        String contextPath = request.getContextPath();
	        response.sendRedirect(contextPath + "/Login.jsp");
	    }
	}
	
	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<UserResponseDto> userList = userService.showUserList();
			request.setAttribute("userList", userList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "UserList.jsp";
	}

	private String block(HttpServletRequest request, HttpServletResponse response) {
	    String id = request.getParameter("id");
	    Integer isStop = Integer.parseInt(request.getParameter("is_blocked"));

	    // 유효성 검사
	    if (isStop >= 1) {
	        // 이미 정지 상태인 경우
	        return request.getContextPath() + "/UserServlet?action=userList";
	    }

	    isStop++; // isStop 값을 1 증가

	    try {
	        userService.block(isStop, id);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // 변경 후 다시 목록으로 돌아갈 수 있도록 Redirect URL을 설정
	    String redirectUrl = request.getContextPath() + "/UserServlet?action=userList";
	    return redirectUrl;
	}

	private String cancel(HttpServletRequest request, HttpServletResponse response) {
	    String id = request.getParameter("id");
	    Integer isStop = Integer.parseInt(request.getParameter("is_blocked"));

	    // 유효성 검사
	    if (isStop <= 0) {
	        // 이미 정지 상태가 아닌 경우
	        return request.getContextPath() + "/UserServlet?action=userList";
	    }

	    isStop--; // isStop 값을 1 감소

	    try {
	        userService.cancel(isStop, id);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // 변경 후 다시 목록으로 돌아갈 수 있도록 Redirect URL을 설정
	    String redirectUrl = request.getContextPath() + "/UserServlet?action=userList";
	    return redirectUrl;
	}
}