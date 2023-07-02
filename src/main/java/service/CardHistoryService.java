package service;

import java.util.ArrayList;

import dao.CardHistoryDao;
import dto.CardHistoryResponseDto;
import exception.BusinessException;
import exception.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardHistoryService {
	private CardHistoryDao dao;

	public ArrayList<CardHistoryResponseDto> showPayCardList() throws Exception {
		ArrayList<CardHistoryResponseDto> list = dao.showPayCardList();
		if (list == null) {
			throw new BusinessException(ErrorCode.DATABASE_ERROR, "DB 에러");
		}
		return list;
	}

	public ArrayList<CardHistoryResponseDto> showSearchUidCardList(String keyword) throws Exception {
		ArrayList<CardHistoryResponseDto> list = dao.showSearchUidCardList(keyword);
		if (list == null) {
			throw new BusinessException(ErrorCode.DATABASE_ERROR, "DB 에러");
		}
		return list;
	}

	public ArrayList<CardHistoryResponseDto> showSearchUserCardList(String userId, long keyword) throws Exception {
		ArrayList<CardHistoryResponseDto> list = dao.showSearchUserCardList(userId, keyword);
		if (list == null) {
			throw new BusinessException(ErrorCode.DATABASE_ERROR, "DB 에러");
		}
		return list;
	}

	public ArrayList<CardHistoryResponseDto> showSearchCardList(long keyword) throws Exception {
		ArrayList<CardHistoryResponseDto> list = dao.showSearchCardList(keyword);
		if (list == null) {
			throw new BusinessException(ErrorCode.DATABASE_ERROR, "DB 에러");
		}
		return list;
	}

	public ArrayList<CardHistoryResponseDto> showMonthlyCardList(String option) throws Exception {
		ArrayList<CardHistoryResponseDto> list = dao.showMonthlyCardList(option);
		if (list == null) {
			throw new BusinessException(ErrorCode.DATABASE_ERROR, "DB 에러");
		}
		return list;
	}

}
