package servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CheckCardHistoryDao;
import dto.CheckCardRequestDto;
import dto.CheckCardResponseDto;
import service.CheckCardService;

/**
 * Servlet implementation class NewServelet
 */

@WebServlet("/card/check")
public class CheckCardHistoryServlet extends HttpServlet {
	private static final long serialVersionUID1 = 1L;

	private final CheckCardService checkCardService;
	public static CheckCardHistoryDao dao = new CheckCardHistoryDao();

	public CheckCardHistoryServlet() {
		// 기본 생성자
		this.checkCardService = new CheckCardService(dao);
	}

	public CheckCardHistoryServlet(CheckCardService checkCardService) {
		this.checkCardService = checkCardService;
	}

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("서블릿 접근");
		Long cardId = Long.parseLong(request.getParameter("card_id"));
		String userId = request.getParameter("user_id");
		Long payment = Long.parseLong(request.getParameter("payment"));
		String franchisee = request.getParameter("franchisee");
		Long fCategory = Long.parseLong(request.getParameter("f_category"));
		System.out.println("post 서블릿 진입");
		LocalDateTime date = LocalDateTime.now();
		CheckCardRequestDto dto = new CheckCardRequestDto(cardId, userId, franchisee, payment, fCategory, date);
		try {
			CheckCardResponseDto responseDto = checkCardService.checkCardPayment(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

	}

}
