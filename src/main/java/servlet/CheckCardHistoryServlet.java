package servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.redisson.api.RedissonClient;

import dao.CardHistoryDao;
import dto.CheckCardRequestDto;
import dto.CheckCardResponseDto;
import redis.RedissonExam;
import service.CheckCardService;

/**
 * Servlet implementation class NewServelet
 */

@WebServlet("/card/check")
public class CheckCardHistoryServlet extends HttpServlet {

//	private CheckCardService checkCardService = null;
	private final RedissonExam redisson;

	public static CardHistoryDao dao = new CardHistoryDao();

	public CheckCardHistoryServlet() {
		// 湲곕낯 �깮�꽦�옄
		this.redisson=new RedissonExam();
	}
//
	public CheckCardHistoryServlet(RedissonExam redisson) {
		this.redisson=redisson;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("서블릿 접근");
		System.out.println(request.getParameter("card_id"));
		Long cardId = Long.parseLong(request.getParameter("card_id"));
		String userId = request.getParameter("user_id");
		Long payment = Long.parseLong(request.getParameter("payment"));
		String franchisee = request.getParameter("franchisee");
		Long fCategory = Long.parseLong(request.getParameter("f_category"));
		System.out.println("/card/check/post 서블릿 진입");
		LocalDateTime date = LocalDateTime.now();
		CheckCardRequestDto dto = new CheckCardRequestDto(cardId, userId, franchisee, payment, fCategory, date);
		try {
			  CheckCardResponseDto responseDto = RedissonExam.cardLock(dto);
	            String message = responseDto.getStatusMsg();

	            System.out.println(message);

				request.setAttribute("data", responseDto);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/CheckCardResponse.jsp");
				dispatcher.forward(request, response);
				System.out.println(responseDto.getCardType());

		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}