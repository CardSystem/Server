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
import dto.CardHistoryResponseDto;
import dto.CardResponseDto;
import service.CardHistoryService;
import service.CardService;
import dao.CardHistoryDao;
import dao.UserDao;
import dao.CardDao;
import dao.AccountDao;


@WebServlet("/CardServlet")
public class CardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String userId = null;
	private final CardService cardService;
	private final CardHistoryService cardHistoryService;
	
	public CardServlet() {
		UserDao userDao = new UserDao();
		AccountDao accountDao = new AccountDao();
		CardDao cardDao = new CardDao();
		CardHistoryDao cardHistoryDao = new CardHistoryDao();
		this.cardService = new CardService(userDao, accountDao, cardDao);
		this.cardHistoryService = new CardHistoryService(cardHistoryDao);
	}

	public CardServlet(CardService cardService) {
		this.cardService = cardService;
		this.cardHistoryService = null;
	}
	
	public CardServlet(CardHistoryService cardHistoryService) {
		this.cardService = null;
		this.cardHistoryService = cardHistoryService;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String order = request.getParameter("action");
		String url;

		if (order != null) {
			switch (order) {
			case "list":
				url = doList(request, response);
				RequestDispatcher dispatcherList = request.getRequestDispatcher(url);
				dispatcherList.forward(request, response);
				break;
			case "cardIssueHistoryList":
				url = cardIssueHistoryList(request, response);
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
				break;
			case "searchId":

				userId = keyword;

				HttpSession session = request.getSession();
				request.getSession().removeAttribute("userId");
				session.setAttribute("userId", userId);

				userId = (String) session.getAttribute("userId");

				url = doIdSearchList(request, response);
				RequestDispatcher dispatcherSearchId = request.getRequestDispatcher(url);
				dispatcherSearchId.forward(request, response);
				break;
			case "searchUserCardId":
				userId = (String) request.getSession().getAttribute("userId");
				long userCardKeyword = Long.parseLong(keyword);
				url = doSearchUserCardList(request, response, userCardKeyword);
				RequestDispatcher dispatcherSearchUserCardId = request.getRequestDispatcher(url);
				dispatcherSearchUserCardId.forward(request, response);
				break;
			case "searchCardId":
				long cardKeyword = Long.parseLong(keyword);
				url = doCardIdSearchList(request, response, cardKeyword);
				RequestDispatcher dispatcherSearchCardId = request.getRequestDispatcher(url);
				dispatcherSearchCardId.forward(request, response);
				break;
			case "searchPeriod":
				url = doPeriodSearchList(request, response, keyword);
				RequestDispatcher dispatcherSearchPeriod = request.getRequestDispatcher(url);
				dispatcherSearchPeriod.forward(request, response);
				break;
			case "block":
				url = blockCard(request, response);
				response.sendRedirect(url);
				break;
			case "cancel":
				url = cancelCard(request, response);
				response.sendRedirect(url);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// ��� 깨짐 방�
		doGet(request, response);
	}

	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("list", cardHistoryService.showPayCardList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cardList.jsp";
	}

	private String cardIssueHistoryList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<CardResponseDto> cardList = cardService.showCardList();
			request.setAttribute("cardIssueHistoryList", cardList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "CardIssueHistory.jsp";
	}

	private String doIdSearchList(HttpServletRequest request, HttpServletResponse response) {
		try {
			ArrayList<CardHistoryResponseDto> userList = cardHistoryService.showSearchUidCardList(userId);
			if (userList != null && !userList.isEmpty()) {
				request.setAttribute("searchId", userList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cardUserSearchList.jsp";
	}

	private String doSearchUserCardList(HttpServletRequest request, HttpServletResponse response, long keyword) {
		try {
			ArrayList<CardHistoryResponseDto> userCardList = cardHistoryService.showSearchUserCardList(userId, keyword);
			if (userCardList != null && !userCardList.isEmpty()) {
				request.setAttribute("searchUserCardId", userCardList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "cardUserCidSearchList.jsp";
	}

	private String doCardIdSearchList(HttpServletRequest request, HttpServletResponse response, long keyword) {
		try {
			request.setAttribute("searchCardId", cardHistoryService.showSearchCardList(keyword));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cardSearchList.jsp";
	}

	private String doPeriodSearchList(HttpServletRequest request, HttpServletResponse response, String option) {
		try {
			request.setAttribute("searchPeriod", cardHistoryService.showMonthlyCardList(option));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "monthlyPaymentList.jsp";
	}

	private String blockCard(HttpServletRequest request, HttpServletResponse response) {
		long id = Long.parseLong(request.getParameter("id"));
		Integer isStop = Integer.parseInt(request.getParameter("is_stopped"));
		try {
			cardService.block(isStop, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// ���� �� �ٽ� ������� ���ư� �� �ֵ��� Redirect URL�� ����
		String redirectUrl = request.getContextPath() + "/CardServlet?action=cardIssueHistoryList";
		return redirectUrl;
	}

	private String cancelCard(HttpServletRequest request, HttpServletResponse response) {
		long id = Long.parseLong(request.getParameter("id"));
		Integer isStop = Integer.parseInt(request.getParameter("is_stopped"));
		try {
			cardService.cancel(isStop, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// ���� �� �ٽ� ������� ���ư� �� �ֵ��� Redirect URL�� ����
		String redirectUrl = request.getContextPath() + "/CardServlet?action=cardIssueHistoryList";
		return redirectUrl;
	}
}