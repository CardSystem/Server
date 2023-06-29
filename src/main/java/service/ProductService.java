package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dao.ProductDao;
import domain.Product;
import dto.ProductDto;
import dto.ProductResponseDto;
import exception.BusinessException;
import exception.ErrorCode;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ProductService {
	
	private ProductDao dao = ProductDao.getInstance();

	public Optional<Long> selectOneProduct(final Long idx) throws SQLException {
		return Optional.ofNullable(dao.selectOneProduct(idx));
	}

	public List<ProductResponseDto> getProductList() {
		List<ProductResponseDto> list = dao.getProductList().orElseThrow(
				()-> new BusinessException(ErrorCode.NOT_FOUND_PRODUCTLIST, "상품 리스트를 찾을 수 없습니다."));;		
		
		return list;
	}

	public void registerProduct(final ProductDto.Request dto) throws SQLException {
		String cardName = dao.selectProductCardName(dto.getCardName()).orElseGet(()-> null);		
		if (cardName != null) {
			 throw new BusinessException(ErrorCode.DUPLICATE_PRODUCT, "상품명이 중복된 상품입니다.");
		}
		
		dao.registerProduct(dto);
	}
	
	public void updateProduct(final Long idx, final ProductDto.Request dto)
			throws SQLException {
		Long findId = selectOneProduct(idx).orElseGet(() -> null);
		
		if (findId == null) {
			throw new BusinessException(ErrorCode.UNABLE_PRODUCTNUM, "존재하지 않는 상품입니다.");
		}
		dao.updateProduct(idx, dto);

	}

	public void deleteProduct(final Long idx) throws SQLException {
		Long findId = selectOneProduct(idx).orElseGet(() -> null);
		
		if (findId == null) {
			throw new BusinessException(ErrorCode.UNABLE_PRODUCTNUM, "존재하지 않는 상품입니다.");
		}
		dao.deleteProduct(idx);
	}
}
