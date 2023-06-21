package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dao.CheckCardHistoryDao;
import domain.Account;
import domain.Cards;
import dto.AccountDto;
import dto.CheckCardDaoToServiceDto;
import dto.CheckCardRequestDto;
import dto.CheckCardResponseDto;

class CheckCardServiceTest {

	@Mock
	CheckCardHistoryDao dao;

	@InjectMocks
	CheckCardService service;

	Long id = 1L;
	Long cardId = 1L;
	Long payment = 1000L;
	String userId = "user123";
	String franchisee = "Franchise A";
	Long accountId = 1L;
	Long balance = 100000L;
	Integer isStopped = 0;
	String cardType = "체크";
	String validity = "25-06";
	String agency = "하나은행 여의도점";
	String issuer = "홍길동";
	String cardNum = "123412341234";
	Long discount = 15L;
	Long fCategory = 1L;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("체크카드 결제 성공")
	void 체크카드결제성공() throws Exception {

		// given
		Cards card = new Cards(cardId, id, null, "2023-06-21", cardType, validity, agency, issuer, isStopped, cardNum);
		Account account = new Account(id, null, "11111111", balance, "하나은행", isStopped);
		CheckCardDaoToServiceDto cardDtoMock = new CheckCardDaoToServiceDto(card, accountId, discount);
		AccountDto accountDtoMock = new AccountDto(account);
		CheckCardRequestDto request = new CheckCardRequestDto(cardId, userId, franchisee, payment, fCategory,
				LocalDateTime.now());

		Double paymentReal = payment * (-1) * ((100 - discount) * 0.01);
		Long totalBalance = balance + (new Double(paymentReal)).longValue();

		// when
		when(dao.selectCardByCardId(cardId)).thenReturn(cardDtoMock);
		when(dao.selectAccountByCardId(cardId)).thenReturn(accountDtoMock);

		// then
		CheckCardResponseDto responseDto = service.checkCardPayment(request);
		assertNotNull(responseDto);
		assertEquals(responseDto.getBalance(), totalBalance);
	}

}
