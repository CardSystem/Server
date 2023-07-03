package servlet;

import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MonthlyCreditDao;
import dto.MonthlyCreditCreateDto;

@WebServlet("/CreditServlet")
public class MonthlyCreditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private static MonthlyCreditDao dao = new MonthlyCreditDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String order = request.getParameter("action");
		String url = null;
		switch(order) {
		case "insert":
			url = doInsert(request, response);
			response.sendRedirect(url);
			break;
		case "monthlyCreditList":
			url = doList(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");//한글깨짐 방지
		doGet(request, response);
	}
	
	private String doInsert(HttpServletRequest request, HttpServletResponse response) {
		MonthlyCreditCreateDto monthlyCreditCreateDto = new MonthlyCreditCreateDto();
		
		monthlyCreditCreateDto.setUserId(request.getParameter("user_id"));
		monthlyCreditCreateDto.setCardId(Long.parseLong(request.getParameter("card_id")));
		monthlyCreditCreateDto.setDiscount(Long.parseLong(request.getParameter("discount")));
		monthlyCreditCreateDto.setTotal(Long.parseLong(request.getParameter("total")));
		monthlyCreditCreateDto.setPay(Long.parseLong(request.getParameter("pay")));
		monthlyCreditCreateDto.setIsPayed(Integer.parseInt(request.getParameter("is_payed")));
		monthlyCreditCreateDto.setDelayDays(Integer.parseInt(request.getParameter("delay_days")));
		monthlyCreditCreateDto.setDelayPrice(Long.parseLong(request.getParameter("delay_price")));
		monthlyCreditCreateDto.setStartDate(LocalDate.parse(request.getParameter("start_date")));
		monthlyCreditCreateDto.setEndDate(LocalDate.parse(request.getParameter("end_date")));
		monthlyCreditCreateDto.setTitle(request.getParameter("title"));
		
		
		System.out.println("doInsert 지입 후 LocalDate 확인!");
		System.out.println(monthlyCreditCreateDto.getStartDate());
		try {
			dao.insertMonthlyCredit(monthlyCreditCreateDto);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("db insert중 에러!");
		}
		return "CreditServlet?action=monthlyCreditList";
	}
	
	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("doList 입장!");
			request.setAttribute("monthlyCreditList", dao.selectAllMonthlyCreditByCardId(Long.parseLong(request.getParameter("card_id"))));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "MonthlyCreditList.jsp";
	}
	
}