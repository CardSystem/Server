package service;

import java.sql.SQLException;

import dao.CardIssueHistoryDAO;

public class CardIssueHistoryService {
	public static void controlIsStop(int isStop, long id) throws SQLException {
		if(isStop == 1) {
    		isStop = 0;
    	} else {
    		isStop = 1;
    	}
        CardIssueHistoryDAO.changeIsStopped(isStop, id);
	}
}
