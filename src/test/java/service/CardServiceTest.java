package service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dao.CardDao;
import domain.Card;
import dto.CardRequestDto;
import dto.CardResponseDto;

class CardServiceTest {

	@Mock
	private CardDao cardDao;

	@InjectMocks
	private CardService cardService;

	private Card card;
	private CardRequestDto request;
	private CardResponseDto response;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		request = CardRequestDto
				.builder()
				.id(1L)
				.productId(1L)
				.accountId(1L)
				.issuedDate("2023-06-10")
				.cardType("신용")
				.validity("2028-07-01")
				.agency("성수역")
				.issuer("홍길동")
				.isStopped(1)
				.cardNum("123123")
				.totalPayment(0L)
				.build();
		card = request.toEntity();
		response = CardResponseDto.of(card);
	}
	
	@Test
	@DisplayName("카드 발급이력 조회 테스트 - 성공")
	void test() throws SQLException{
		 List<CardResponseDto> mockCardList = new ArrayList<>();
		 mockCardList.add(response);
		 
		 when(cardDao.showCardList()).thenReturn(mockCardList);
		 List<CardResponseDto> dto = cardService.showCardList();
		 
		 assertNotNull(mockCardList);
		 assertTrue(!dto.isEmpty(), "카드 발급이력 리스트가 비어있지 않아야 테스트 통과");
		 assertNotNull(dto);
		 
		 for(CardResponseDto element : dto) {
			 System.out.println(element);
		 }
	}
}
