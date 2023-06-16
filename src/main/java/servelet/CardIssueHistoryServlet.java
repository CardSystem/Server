package servelet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CardIssueHistoryDAO;

@WebServlet("/CardIssueHistoryServlet")
public class CardIssueHistoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String order = request.getParameter("action");
		String url;
		switch (order) {
		case "list":
			url = doList(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			break;
		case "changeIsStop":
			url = doChangeIsStop(request, response);
			response.sendRedirect(url);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 한글깨짐 방지
		doGet(request, response);
	}

	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("list", CardIssueHistoryDAO.showCardList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "CardIssueHistory.jsp";
	}

	private String doChangeIsStop(HttpServletRequest request, HttpServletResponse response) {
	    long id = Long.parseLong(request.getParameter("id"));
	    int isStop = Integer.parseInt(request.getParameter("is_stopped"));
	    try {
	    	if(isStop == 1) {
	    		isStop = 0;
	    	} else {
	    		isStop = 1;
	    	}
	        CardIssueHistoryDAO.changeIsStopped(isStop, id);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    // 변경 후 다시 목록으로 돌아갈 수 있도록 Redirect URL을 설정
	    String redirectUrl = request.getContextPath() + "/CardIssueHistoryServlet?action=list";
	    return redirectUrl;
	}
}
