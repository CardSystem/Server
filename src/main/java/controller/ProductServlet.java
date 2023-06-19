package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.ProductDTO;
import domain.ProductResponseDTO;
import service.ProductService;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ProductService productService = new ProductService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<ProductResponseDTO> list = productService.getProduct().orElseGet(()-> null);
		
		
		request.setAttribute("productList", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("productList.jsp");
	    dispatcher.forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		if (action.equals("register")) {
			doRegister(request, response);
		} else if (action.equals("update")) {
			doUpdate(request, response);
		} else if (action.equals("delete")) {
			doDelete(request, response);
		}

	}

	public void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {

		final String name = request.getParameter("name");
		final String type = request.getParameter("type");
		final Long limit = Long.parseLong(request.getParameter("limit"));
		final Long categoryId = Long.parseLong(request.getParameter("category"));

		ProductDTO.RequestProduct dto = ProductDTO.RequestProduct
				.builder()
				.cardName(name)
				.cardType(type)
				.cardLimit(limit)
				.categoryId(categoryId)
				.build();

		try {
			productService.registerProduct(dto);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("카드 등록 에러!"); // 중복 카드 예외 처리 로직 처리 필요!
		}

		System.out.println("성공 성공 성공");
		response.sendRedirect("productList.jsp");
	}

	public void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {

		final Long id = Long.parseLong(request.getParameter("productId"));
		final String name = request.getParameter("name");
		final String type = request.getParameter("type");
		final Long limit = Long.parseLong(request.getParameter("limit"));
		final Long categoryId = Long.parseLong(request.getParameter("category"));

		ProductDTO.RequestProduct dto = ProductDTO.RequestProduct
				.builder()
				.cardName(name)
				.cardType(type)
				.cardLimit(limit)
				.categoryId(categoryId)
				.build();

		try {
			productService.updateProduct(id, dto);
		} catch (Exception e) {
			e.printStackTrace();

		}

		System.out.println("성공 성공 성공");
		response.sendRedirect("productList.jsp");

	}

	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final Long id = Long.parseLong(request.getParameter("productId"));

		try {
			productService.deleteProduct(id);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("성공 성공 성공");
		response.sendRedirect("productList.jsp");
	}
}
