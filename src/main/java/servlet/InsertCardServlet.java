package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InsertCardDao;
import dto.CardRequestDto;
import service.InsertCardService;

/**
 * Servlet implementation class InsertCardServlet
 */
@WebServlet("/card/insert")
public class InsertCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final InsertCardService insertCardService;
	public static InsertCardDao dao = new InsertCardDao();

	public InsertCardServlet() {
		// 기본 생성자
		this.insertCardService = new InsertCardService(dao);
	}

	public InsertCardServlet(InsertCardService insertCardService) {
		this.insertCardService = insertCardService;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("카드서블릿 접근");
		Long cardId = Long.parseLong(request.getParameter("card_id"));
		String agency = request.getParameter("agency");
		String issuer = request.getParameter("issuer");
		Long accountId = Long.parseLong(request.getParameter("account_id"));

		CardRequestDto dto = new CardRequestDto(cardId, accountId, agency, issuer);

		try {
			insertCardService.insertCard(dto);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
