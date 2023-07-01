package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDao;
import dto.ProductRequestDto;
import dto.ProductResponseDto;
import service.ProductService;

//카드 상품 추가

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProductService productService;
	
	public ProductServlet() {
		ProductDao dao = new ProductDao();
		this.productService = new ProductService(dao);
	}

	public ProductServlet(ProductService productService) {
		this.productService = productService;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<ProductResponseDto> productList = productService.getProductList();

		request.setAttribute("productList", productList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("productList.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String order = request.getParameter("action");
		
		if(order.equals("register")) {
			doRegister(request,response);
			doGet(request,response);
		}else if (order.equals("update")) {
			doUpdate(request, response);
			doGet(request,response);
		} else if (order.equals("delete")) {
			doDeleteProduct(request, response);
			doGet(request,response);
		}
	}

	public void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {

		final String name = request.getParameter("name");
		final String type = request.getParameter("type");
		final Long limit = Long.parseLong(request.getParameter("limit"));
		final Long categoryId = Long.parseLong(request.getParameter("category"));

		ProductRequestDto dto = ProductRequestDto
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
		}
	}

	public void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {

		final Long id = Long.parseLong(request.getParameter("productId"));
		final String name = request.getParameter("name");
		final String type = request.getParameter("type");
		final Long limit = Long.parseLong(request.getParameter("limit"));
		final Long categoryId = Long.parseLong(request.getParameter("category"));

		ProductRequestDto dto = ProductRequestDto
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
	}

	public void doDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final Long id = Long.parseLong(request.getParameter("productId"));

		try {
			productService.deleteProduct(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
