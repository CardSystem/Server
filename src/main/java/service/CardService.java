package service;

import java.sql.SQLException;
import dao.CardDao;

public class CardService {
	public static void block(int isStop, long id) throws SQLException {
		if(isStop == 2) {
			isStop = 2;
		} else if(isStop == 0){
			isStop += 1;
		}
		
        CardDao.changeIsStopped(isStop, id);
	}
	
	public static void cancel(int isStop, long id) throws SQLException {
		if(isStop == 0) {
			isStop = 0;
		} else if(isStop == 1){
			isStop -= 1;
		}
        CardDao.changeIsStopped(isStop, id);
	}
}
