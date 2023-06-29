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
import dto.CardHistoryDTO;


import dao.CardDao;



@WebServlet("/CardControllerServlet")
public class CardControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String userId = null;
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String order = request.getParameter("action");
		String url;
		
		
		if(order != null) {
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
					
					userId = keyword;
					
					HttpSession session = request.getSession();
					request.getSession().removeAttribute("userId");
					session.setAttribute("userId", userId);
					
					userId = (String)session.getAttribute("userId");

					url = doIdSearchList(request, response);
					RequestDispatcher dispatcherSearchId = request.getRequestDispatcher(url);
					dispatcherSearchId.forward(request, response);
					break;
				case "searchUserCardId":
					userId = (String) request.getSession().getAttribute("userId");
					
					url = doSearchUserCardList(request, response,keyword);
					RequestDispatcher dispatcherSearchUserCardId = request.getRequestDispatcher(url);
					dispatcherSearchUserCardId.forward(request, response);
					break;
				case "searchCardId":
					long cardKeyword= Long.parseLong(keyword); 
					url = doCardIdSearchList(request, response,cardKeyword);
					RequestDispatcher dispatcherSearchCardId = request.getRequestDispatcher(url);
					dispatcherSearchCardId.forward(request, response);
					break;
				case "searchPeriod":
					url = doPeriodSearchList(request, response,keyword);
					RequestDispatcher dispatcherSearchPeriod = request.getRequestDispatcher(url);
					dispatcherSearchPeriod.forward(request, response);
					break;
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");//�븳湲�源⑥쭚 諛⑹�
		doGet(request, response);
	}

	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("list", CardDao.showPayCardList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "cardList.jsp";
	}
	
	private String doRecentList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("recentlist", CardDao.showRecentPayCardList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "cardRecentList.jsp";
	}
	
	private String doIdSearchList(HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<CardHistoryDTO> userList = CardDao.showSearchUidCardList(userId);
			if(userList != null && !userList.isEmpty()) {
				request.setAttribute("searchId",userList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cardUserSearchList.jsp";
	}
	
	private String doSearchUserCardList(HttpServletRequest request, HttpServletResponse response, String keyword) {
		try {
			ArrayList<CardHistoryDTO> userCardList = CardDao.showSearchUserCardList(userId,keyword);
			if(userCardList != null && !userCardList.isEmpty()) {
				request.setAttribute("searchUserCardId",userCardList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "cardUserCidSearchList.jsp";
	}
	
	private String doCardIdSearchList(HttpServletRequest request, HttpServletResponse response, long keyword) {
		try {
			request.setAttribute("searchCardId", CardDao.showSearchCardList(keyword));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "cardSearchList.jsp";
	}
	
	private String doPeriodSearchList(HttpServletRequest request, HttpServletResponse response, String option) {
		try {
			request.setAttribute("searchPeriod", CardDao.showMonthlyCardList(option));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "monthlyPaymentList.jsp";
	}
	
}
