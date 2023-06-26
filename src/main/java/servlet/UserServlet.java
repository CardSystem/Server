package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exception.BusinessException;
import dao.UserDAO;
import service.UserService;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static String id;

    private UserService userService;

    public void init() {
        userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String order = request.getParameter("action");
        String url;
        switch (order) {
            case "login":
                url = doLogin(request, response);
                if (url != null) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                    dispatcher.forward(request, response);
                }
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");// 한글깨짐 방지
        doGet(request, response);
    }

    private String doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        id = request.getParameter("id");
        String birth = request.getParameter("userBirth");
        try {
            return userService.checkLogin(request, response, id, birth);
        } catch (SQLException | BusinessException e) {
            e.printStackTrace();
        }
        return null;
    }
}