package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CardDao;
import dao.UserDao;
import dao.AccountDao;
import service.CardService;
import dto.CardResponseDto;

@WebServlet("/CardServlet")
public class CardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final CardService cardService;
	
	public CardServlet() {
		UserDao userDao = new UserDao();
		AccountDao accountDao = new AccountDao();
		CardDao cardDao = new CardDao();
		this.cardService = new CardService(userDao, accountDao, cardDao);
	}
	
	public CardServlet(CardService cardService) {
		this.cardService = cardService;
	}

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 한글깨짐 방지
		doGet(request, response);
	}

	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<CardResponseDto> cardList = cardService.showCardList();
			request.setAttribute("list", cardList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "CardIssueHistory.jsp";
	}

	private String blockCard(HttpServletRequest request, HttpServletResponse response) {
	    long id = Long.parseLong(request.getParameter("id"));
	    Integer isStop = Integer.parseInt(request.getParameter("is_stopped"));
	    try {
	    	cardService.block(isStop, id);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    // 변경 후 다시 목록으로 돌아갈 수 있도록 Redirect URL을 설정
	    String redirectUrl = request.getContextPath() + "/CardServlet?action=list";
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
	    // 변경 후 다시 목록으로 돌아갈 수 있도록 Redirect URL을 설정
	    String redirectUrl = request.getContextPath() + "/CardServlet?action=list";
	    return redirectUrl;
	}
}
