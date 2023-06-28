/**
 * 
 */
package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dao.ProductDao;
import domain.Product;
import dto.ProductDto;
import dto.ProductResponseDto;

/**
 * @author 동현
 *
 */

class ProductServiceTest {
	//의존하는 실체 객체 대신 가짜 객체로 초기화
	@Mock
	private ProductDao dao;
	
	//목 객체에 의존하는 객체에 대해 초기화
	@InjectMocks
	private ProductService productService;
	
	private Product product;
	private ProductDto.Request request;
	private ProductResponseDto response;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		request = ProductDto.Request //요청 dto
				.builder()
				.id(1L)
				.cardName("신한DS")
				.cardType("신용")
				.cardLimit(40000000L)
				.categoryId(2L)
				.categoryName("카테고리명-음식점")
				.discount(10L)
				.build();
		product = request.toEntity(); //dto를 entity화
		response = ProductResponseDto.of(product); //응답 dto로 가공
	}

	@Test
	@DisplayName("상품 조회 테스트 - 성공")
	void getProductTest() throws Exception {
		
		/*
		 * Mock은 가짜 데이터 생성
		 * 테스트 시나리오에서는 실제 데이터베이스에 의존하지 않고, 
		 * 가짜 데이터를 사용하여 테스트를 수행합니다.
		 */
		
		// given
		List<ProductResponseDto> mockProductList = new ArrayList<>();
		mockProductList.add(response);
		
		// when
		when(dao.getProdcutList()).thenReturn(mockProductList);
		
		//Optional로 null 검증
	    Optional<List<ProductResponseDto>> dto = productService.getProductList();
	    
	    assertNotNull(mockProductList);
	    assertTrue(dto.isPresent(), "상품 리스트가 비어있지 않아야 테스트 통과");
	    assertNotNull(dto);
	    dto.ifPresent(list -> {
	    	for(ProductResponseDto element : list) {
	    		System.out.println(element);
	    	}
	    });
	}
		
	
	@Test
	@DisplayName("상품 추가 테스트 - 성공")
	void registerProductTest() throws Exception {
		//given
		ProductDto.Request requestMock = ProductDto.Request
				.builder()
				.id(2L)
				.cardName("국민")
				.cardType("신용")
				.cardLimit(40000000L)
				.categoryId(2L)
				.categoryName("카테고리명-음식점")
				.discount(10L)
				.build();
		requestMock.toEntity();
		
		//when
		doAnswer(i ->{
			productService.registerProduct(requestMock);
			return null;
		}).when(dao).registerProduct(any(ProductDto.Request.class));
		
		// then
		assertNotNull(dao.selectOneProduct(2L));
		assertTrue(requestMock.getDiscount()==10L, "해당 카드의 할인율이 일치하지 않음");
		assertEquals(requestMock.getCardName(), "국민");
		System.out.println(requestMock);
	}
	
	@Test
	@DisplayName("상품 수정 테스트 - 성공")
	void updateProductTest() throws Exception {
		// given
		//엔티티 내 데이터 설정
		Product productMock = Product.builder()
				.id(5L)
				.cardName("카카오뱅크-라이언카드")
				.cardType("체크")
				.cardLimit(40000000L)
				.categoryId(2L)
				.categoryName("카테고리명-음식점")
				.discount(10L)
				.build();
		
		//아이디 값 찾아서 상품 데이터 수정
		Long updateIdx = 5L;
		ProductDto.Request requestMock = ProductDto.Request
				.builder()
				.cardName("우리은행-우리카드")
				.cardType("신용")
				.cardLimit(20000L)
				.categoryId(3L)
				.categoryName("카테고리명-카페")
				.discount(5L)
				.build();
		productMock = requestMock.toEntity();
		
		// when
		doAnswer( i -> {
			productService.updateProduct(updateIdx, requestMock);
			return null;
		}).when(dao).updateProduct(any(Long.class),any(ProductDto.Request.class));
		
		assertNotNull(requestMock);
		assertNotNull(productMock);
		assertEquals(productMock.getCardName(),"우리은행-우리카드");
		
	}
	
	@Test
	@DisplayName("상품 삭제 테스트 - 성공")
	void deleteProductTest() throws Exception {
		Long deleteIdx = request.getId();
		doAnswer( i ->{
			productService.deleteProduct(deleteIdx);
			return null;
		}).when(dao).deleteProduct(any(Long.class));
		
	}

}