package service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import dao.InsertCardDao;
import dto.CardInsertDto;
import dto.CardRequestDto;
import exception.BusinessException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsertCardService {
	public final InsertCardDao dao;

	private static final int RANDOM_STRING_LENGTH = 16;

	public static String generateRandomString() {
		char[] randomChars = new char[RANDOM_STRING_LENGTH];
		ThreadLocalRandom random = ThreadLocalRandom.current();

		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			randomChars[i] = (char) (random.nextInt(10) + '0');
		}

		return new String(randomChars);
	}

	public void insertCard(CardRequestDto dto) throws SQLException {
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
		// 5년을 더함
		calendar.add(Calendar.YEAR, 5);

		// 일을 01로 설정
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

//		insert into cards(card_id,issued_date,card_type,validity,agency,issuer,is_stopped,card_num,account_id)
//	    -> values(1,"2023-06-21","체크","26-06","하나은행 성수역점","홍길동",0,"45644564",1);
	}
}
