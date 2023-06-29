package service;

import java.sql.SQLException;
import java.util.List;

import dao.AccountDao;
import dao.CardDao;
import dao.UserDao;
import dto.CardResponseDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardService {
	
	private UserDao userDao = UserDao.getInstance();
	private AccountDao accountDao = AccountDao.getInstance();
	private CardDao cardDao = CardDao.getInstance();
	
	public List<CardResponseDto> showCardList() throws SQLException{
		List<CardResponseDto> list = cardDao.showCardList();
		return list;
	}
	
	public void block(int isStop, long id) throws SQLException {
		long accountId = cardDao.getAccountIdByCardId(id);
		String userId = accountDao.getUserIdByAccountId(accountId);
		if(userDao.checkUserBlock(userId) == 1) {
			if(isStop == 2) {
				isStop = 2;
			} else if(isStop == 1){
				isStop = 1;
			} else {
				isStop = 0;
			}
		} else {
			if(isStop == 2) {
				isStop = 2;
			} else if(isStop == 0){
				isStop += 1;
			}
		}

		cardDao.changeIsStopped(isStop, id);
	}
	
	public void cancel(int isStop, long id) throws SQLException {
		long accountId = cardDao.getAccountIdByCardId(id);
		String userId = accountDao.getUserIdByAccountId(accountId);
		if(userDao.checkUserBlock(userId) == 1) {
			if(isStop == 2) {
				isStop = 2;
			} else if(isStop == 1){
				isStop = 1;
			} else {
				isStop = 0;
			}
		} else {
			if(isStop == 0) {
				isStop = 0;
			} else if(isStop == 1){
				isStop -= 1;
			}
		}
		
		cardDao.changeIsStopped(isStop, id);
	}
}
