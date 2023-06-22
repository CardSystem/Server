package service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

public class UserService {
    public String checkLogin(HttpServletRequest request, HttpServletResponse response, String id, String birth)
            throws ServletException, IOException, SQLException {
        int loginResult = UserDAO.login(id, birth);
        if (loginResult == 1) {
            if (id.contains("Admin")) {
                request.getSession().setAttribute("adminId", id);
                request.getSession().setAttribute("adminBirth", birth);
                response.sendRedirect("SearchButton.jsp");
                return null; // 리다이렉트 후에는 메서드 실행 중지
            } else {
                // 로그인 성공 시 사용자 정보를 세션에 저장
                request.getSession().setAttribute("userId", id);
                request.getSession().setAttribute("userBirth", birth);
                response.sendRedirect("ShowMyAccount.jsp");
                return null; // 리다이렉트 후에는 메서드 실행 중지
            }
        } else {
            // 로그인 실패한 경우 세션에서 사용자 정보를 제거
            request.getSession().removeAttribute("userId");
            request.getSession().removeAttribute("userBirth");
            request.getSession().removeAttribute("adminId");
            request.getSession().removeAttribute("adminBirth");
            response.sendRedirect("Login.jsp");
            return null; // 리다이렉트 후에는 메서드 실행 중지
        }
    }
}