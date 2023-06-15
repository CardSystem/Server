package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import domain.Product;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductDAO productDao = new ProductDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String order = request.getParameter("action");
		String url;
		switch (order) {
		case "insert":
			url = doInsert(request, response);
			response.sendRedirect(url);
			break;
		}

		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

	private String doInsert(HttpServletRequest request, HttpServletResponse response) {
		Product product = new Product();
		product.setCardName(request.getParameter("name"));
		product.setCardType(request.getParameter("type"));
		product.setCardLimit(Long.parseLong(request.getParameter("limit")));
		product.setCategoryId(Long.parseLong(request.getParameter("category")));
		try {
			productDao.insertProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("insert 에러!");
		}
		System.out.println("성공 성공 성공");
		return "success.jsp";
	}
}
