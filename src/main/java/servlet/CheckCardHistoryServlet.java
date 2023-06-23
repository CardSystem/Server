package servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CheckCardHistoryDao;
import dto.CheckCardRequestDto;
import dto.CheckCardResponseDto;
import redis.RedissonExam;
import service.CheckCardService;

/**
 * Servlet implementation class NewServelet
 */

@WebServlet("/card/check")
public class CheckCardHistoryServlet extends HttpServlet {

//	private final CheckCardService checkCardService;
	private final RedissonExam redisson;
	public static CheckCardHistoryDao dao = new CheckCardHistoryDao();

	public CheckCardHistoryServlet() {
		// 기본 생성자
//		this.checkCardService = new CheckCardService(dao);
		this.redisson=new RedissonExam();
	}
//
	public CheckCardHistoryServlet(RedissonExam redisson) {
//		this.checkCardService = checkCardService;
		this.redisson=redisson;
	}

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
		System.out.println("/card/check/post 서블릿 진입");
		LocalDateTime date = LocalDateTime.now();
		CheckCardRequestDto dto = new CheckCardRequestDto(cardId, userId, franchisee, payment, fCategory, date);
		try {
			CheckCardResponseDto responseDto=RedissonExam.cardLock(dto);
			
//			CheckCardResponseDto responseDto = checkCardService.checkCardPayment(dto);
//			request.setAttribute("data", responseDto);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/CheckCardResponse.jsp");
//			dispatcher.forward(request, response);
			System.out.println(responseDto.getCardType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}