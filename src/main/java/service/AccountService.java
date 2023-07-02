package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.BusinessException;
import exception.ErrorCode;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.*;
import dao.AccountDao;
import dto.AccountResponseDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountService {

	private AccountDao dao = AccountDao.getInstance();

	public List<AccountResponseDto> selectAccount(String userId) throws SQLException {
		List<AccountResponseDto> list = dao.selectAccount(userId);
		return list;
	}

	public void checkMoney(HttpServletRequest request, HttpServletResponse response, String accountNum, String balance)
            throws Exception {
        String pattern = "^[1-9]\\d*$";

        if (balance.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_AMOUNT, "금액을 입력해주세요");
        } else if (Pattern.matches(pattern, String.valueOf(balance))) {
        	long balanceToLong = Long.parseLong(balance);
            dao.depositAccount(accountNum, balanceToLong);
        } else {
            throw new BusinessException(ErrorCode.WRONG_AMOUNT, "잘못된 숫자 형식입니다.");
        }
    }
}