package service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import dao.CardDao;
import dto.CardInsertDto;
import dto.CardRequestDto;
import exception.BusinessException;
import exception.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsertCardService {
	public final CardDao dao;

	private static final int RANDOM_STRING_LENGTH = 16;

	public static String generateRandomString() {
		char[] randomChars = new char[RANDOM_STRING_LENGTH];
		ThreadLocalRandom random = ThreadLocalRandom.current();

		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			randomChars[i] = (char) (random.nextInt(10) + '0');
		}

		return new String(randomChars);
	}
	
	
	

	public void insertCard(CardRequestDto dto) throws Exception {
		Long count=dao.countCardNum(dto.getAccountId());
		if(count>=3)
		{
			throw new BusinessException(ErrorCode.TOO_MANY_CARDS, "한 계좌에 카드는 3개까지 등록할 수 있습니다.");
		}
			
		Long cardId = dto.getCardId();
		Long accountId = dto.getAccountId();
		String agency = dto.getAgency();
		String issuer = dto.getIssuer();
		String cardType = dao.selectCardType(cardId);
		String cardNum = generateRandomString();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Date today = calendar.getTime();
		String issuedDate = format.format(today);
		calendar.add(Calendar.YEAR, 5);

		calendar.set(Calendar.DAY_OF_MONTH, 1);

		Date modifiedDate = calendar.getTime();
		String validity = format.format(modifiedDate);

		CardInsertDto insertDto = new CardInsertDto(cardId, issuedDate, cardType, validity, agency, issuer, 0, cardNum,
				accountId);

		System.out.println(insertDto.getIssuedDate());
		System.out.println(insertDto.getValidity());
		try {
			dao.insertData(insertDto);
		} catch (BusinessException e) {
			System.out.println("에러발생: " + e.getMessage());

		}

	}
}
