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
import dto.ProductCreateDto;

//카드 상품 추가

@WebServlet("/product")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	public static ProductDao dao=new ProductDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String order = request.getParameter("action");
		String url;
		switch(order) {
		case "insert":
			url = doInsert(request, response);
			System.out.println("get처리 성공");
			response.sendRedirect(url);
			break;

		case "productList":
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
		ProductCreateDto productCreateDto = ProductCreateDto.builder()
				.cardName(request.getParameter("card_name").toString())
				.cardType(request.getParameter("card_type").toString())
				.cardLimit(Long.parseLong(request.getParameter("card_limit")))
				.categoryId(Long.parseLong(request.getParameter("category_id")))
				.build();
			
		try {
			dao.insertData(productCreateDto);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("db insert중 에러!");
		}
		System.out.print( "success");
		return "product?action=productList";
	}
	
	private String doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("doList 진입!");
			request.setAttribute("productList", dao.selectAllData());
			System.out.println("doList 탈출!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "ProductList.jsp";
	}

}
