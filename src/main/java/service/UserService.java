package service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import exception.BusinessException;
import exception.ErrorCode;
import dto.UserResponseDto;
import dao.UserDao;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserService {
	
	private UserDao dao = UserDao.getInstance();
	
	public List<UserResponseDto> showUserList() throws SQLException{
		List<UserResponseDto> list = dao.showUserList();
		return list;
	}
	
	public String checkLogin(HttpServletRequest request, HttpServletResponse response, String id, String birth)
			throws ServletException, IOException, SQLException, BusinessException {
		int loginResult = dao.login(id, birth);
		if (loginResult == 1) {
			if (id.contains("Admin")) {
				request.getSession().setAttribute("adminId", id);
				request.getSession().setAttribute("adminBirth", birth);
				response.sendRedirect("SearchButton.jsp");
				return null; // �����̷�Ʈ �Ŀ��� �޼��� ���� ����
			} else {
				// �α��� ���� �� ����� ������ ���ǿ� ����
				request.getSession().setAttribute("userId", id);
				request.getSession().setAttribute("userBirth", birth);
				response.sendRedirect("ShowMyAccount.jsp");
				return null; // �����̷�Ʈ �Ŀ��� �޼��� ���� ����
			}
		} else if (loginResult == 0) { // ��й�ȣ ����ġ
			// �α��� ������ ��� ���ǿ��� ����� ������ ����
			request.getSession().removeAttribute("userId");
			request.getSession().removeAttribute("userBirth");
			request.getSession().removeAttribute("adminId");
			request.getSession().removeAttribute("adminBirth");
			response.sendRedirect("Login.jsp");
			throw new BusinessException(ErrorCode.WORNG_PASSWORD, "�߸��� ��й�ȣ�Դϴ�.");
//            return null; // �����̷�Ʈ �Ŀ��� �޼��� ���� ����
		} else if (loginResult == -1) {// ����ڰ� ����
			request.getSession().removeAttribute("userId");
			request.getSession().removeAttribute("userBirth");
			request.getSession().removeAttribute("adminId");
			request.getSession().removeAttribute("adminBirth");
			response.sendRedirect("Login.jsp");
			throw new BusinessException(ErrorCode.UNABLE_USER, "�������� ���� �����Դϴ�.");
//          return null; // �����̷�Ʈ �Ŀ��� �޼��� ���� ����
		} else { // ����
			request.getSession().removeAttribute("userId");
			request.getSession().removeAttribute("userBirth");
			request.getSession().removeAttribute("adminId");
			request.getSession().removeAttribute("adminBirth");
			response.sendRedirect("Login.jsp");
			throw new BusinessException(ErrorCode.LOGIN_ERROR, "�α��� ����");
		}
	}

	public void block(int isStop, String id) throws SQLException {
		if(isStop == 0) {
			isStop = 1;
		}
		
        dao.changeIsBlock(isStop, id);
	}
	
	public void cancel(int isStop, String id) throws SQLException {
		if(isStop == 1) {
			isStop = 0;
		}
		
        dao.changeIsCancel(isStop, id);
	}
}
