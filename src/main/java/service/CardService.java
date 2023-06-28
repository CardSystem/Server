package service;

import java.sql.SQLException;

import dao.AccountDao;
import dao.CardDao;
import dao.UserDao;

public class CardService {
	public static void block(int isStop, long id) throws SQLException {
		long accountId = CardDao.getAccountIdByCardId(id);
		String userId = AccountDao.getUserIdByAccountId(accountId);
		if(UserDao.checkUserBlock(userId) == 1) {
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

        CardDao.changeIsStopped(isStop, id);
	}
	
	public static void cancel(int isStop, long id) throws SQLException {
		long accountId = CardDao.getAccountIdByCardId(id);
		String userId = AccountDao.getUserIdByAccountId(accountId);
		if(UserDao.checkUserBlock(userId) == 1) {
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
		
        CardDao.changeIsStopped(isStop, id);
	}
}
