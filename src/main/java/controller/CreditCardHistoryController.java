package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CreditCardHistoryDao;
import dao.productDao;
import dto.CreditCardPaymentDto;
import dto.CreditCardRequestDto;
import service.CreditCardService;


//신용카드 결제
//단순 전달 역할


/*
 *
 카드정보 입력받기
 카드 존재 여부 확인
 카드 정지여부 확인
 할부적용
 할부개월 중 하나라도 한도초과나면 false
 결제 성공시 true
 카드사용내역 테이블에 최종 insert
 자동 결제일에 계좌 정지여부 확인하기
 계좌에서 해당 월 총사용액 차감
 총 사용액 차감 성공시 true
 총 사용액 차감 실패시 false
 연체 처리
 블랙리스트 처리
 * 
 */

@WebServlet("/card/credit")
public class CreditCardHistoryController extends HttpServlet {
	
	public final CreditCardService creditCardService = new CreditCardService();
	private static final long serialVersionUID = 1L;
	public static CreditCardHistoryDao dao=new CreditCardHistoryDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	//신용카드 결제 POST 요청이오면 request에서 컬럼을 추출해서 
	//service로 전달
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long cardId=Long.parseLong(request.getParameter("card_id"));
		String userId=request.getParameter("user_id");
		Long payment=Long.parseLong(request.getParameter("payment"));
		String franchisee=request.getParameter("franchisee");
		Long fCategory=Long.parseLong(request.getParameter("f_category"));
		int insMonth=Integer.parseInt(request.getParameter("ins_month"));
		
		System.out.println("서블릿 post 메소드 진입");
		
		CreditCardPaymentDto creditCardPaymentDto = new CreditCardPaymentDto(cardId,userId,franchisee,payment,fCategory,insMonth);
		
		try {
			creditCardService.payCreditCard(creditCardPaymentDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("list", dao.selectAllData());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "list.jsp";
	}
//	
//	private String doList(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			request.setAttribute("list", dao.());

}










