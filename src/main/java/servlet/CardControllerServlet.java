package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.CardDAO;



@WebServlet("/CardControllerServlet")
public class CardControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		
		String order = request.getParameter("action");
		String url;
		switch(order) {
		case "list":
			url = doList(request, response);
			RequestDispatcher dispatcherList = request.getRequestDispatcher(url);
			dispatcherList.forward(request, response);
			break;
		case "recentlist":
			url = doRecentList(request, response);
			RequestDispatcher dispatcherRecent = request.getRequestDispatcher(url);
			dispatcherRecent.forward(request, response);
			break;
		case "searchId":
			url = doIdSearchList(request, response,keyword);
			RequestDispatcher dispatcherSearchId = request.getRequestDispatcher(url);
			dispatcherSearchId.forward(request, response);
			break;
		case "searchCardId":
			long cardKeyword= Long.parseLong(keyword); 
			url = doCardIdSearchList(request, response,cardKeyword);
			RequestDispatcher dispatcherSearchCardId = request.getRequestDispatcher(url);
			dispatcherSearchCardId.forward(request, response);
			break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");//한글깨짐 방지
		doGet(request, response);
	}

	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("list", CardDAO.showPayCardList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "cardList.jsp";
	}
	
	private String doRecentList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("recentlist", CardDAO.showRecentPayCardList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "cardRecentList.jsp";
	}
	
	private String doIdSearchList(HttpServletRequest request, HttpServletResponse response, String keyword) {
		try {
			request.setAttribute("searchId", CardDAO.showSearchUidCardList(keyword));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "cardUserSearchList.jsp";
	}
	
	private String doCardIdSearchList(HttpServletRequest request, HttpServletResponse response, long keyword) {
		try {
			request.setAttribute("searchCardId", CardDAO.showSearchCardList(keyword));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "cardSearchList.jsp";
	}
	
}
