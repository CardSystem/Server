package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDao;
import domain.Product;

//카드 상품 추가

@WebServlet("/product")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	public static ProductDao dao=new ProductDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String order = request.getParameter("action");
//		String url;
//		switch(order) {
//		case "insert":
//			url = 
			doInsert(request, response);
			System.out.println("get처리 성공");
//			response.sendRedirect(url);
//
//		case "list":
//			url = doList(request, response);
//			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
//			dispatcher.forward(request, response);
//			break;
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");//한글깨짐 방지
		doGet(request, response);
	}
	
	private void doInsert(HttpServletRequest request, HttpServletResponse response) {
		Product data = new Product();
		data.setCardName(request.getParameter("card_name").toString());
		data.setCardType(request.getParameter("card_type").toString());
		data.setCardLimit(Long.parseLong(request.getParameter("card_limit")));
		data.setCategoryId(Long.parseLong(request.getParameter("category_id")));

		
		try {
			dao.insertData(data);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("db insert중 에러!");
		}
		System.out.print( "success");
	}
	
	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("list", dao.selectAllData());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "list.jsp";
	}

}
