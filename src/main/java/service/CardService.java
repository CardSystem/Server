package service;

import java.sql.SQLException;
import dao.CardDao;

public class CardService {
	public static void controlIsStop(int isStop, long id) throws SQLException {
		if(isStop == 1) {
    		isStop = 0;
    	} else {
    		isStop = 1;
    	}
        CardDao.changeIsStopped(isStop, id);
	}
}
