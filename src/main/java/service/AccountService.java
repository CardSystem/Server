package service;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.*;
import dao.AccountDAO;

public class AccountService {
	public static String checkMoney(HttpServletRequest request, String accountNum, long balance) throws Exception {
		String pattern = "^[1-9]\\d*$";
		
		if (Pattern.matches(pattern, String.valueOf(balance))) {
			AccountDAO.depositAccount(accountNum, balance);
        } else {
        	throw new Exception("잘못된 형식입니다");
        }

		String redirectUrl = request.getContextPath() + "/AccountServlet?action=list";
        return redirectUrl;
	}

}