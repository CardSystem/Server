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
	// dao, Entity 클래스 필드로 선언
	private ProductDao dao = ProductDao.getInstance();

	public Optional<Long> selectOneProduct(final Long idx) throws SQLException {
		return Optional.ofNullable(dao.selectOneProduct(idx));
	}

	public Optional<List<ProductResponseDto>> getProductList() {
		List<ProductResponseDto> list = dao.getProdcutList();
		return Optional.ofNullable(list);
	}

	public void registerProduct(final ProductDto.Request dto) throws SQLException {
		Product product = new Product();
		String cardName = product.getCardName();
		System.out.print(cardName);
		if (cardName == dto.getCardName()) {
			 throw new BusinessException(ErrorCode.UNABLE_PRODUCTNUM, "일치하는 정보가 없습니다.");
		}
		
		dao.registerProduct(dto);
	}
	
	public void updateProduct(final Long idx, final ProductDto.Request dto)
			throws SQLException {
		Long findId = selectOneProduct(idx).orElseGet(() -> null);
		System.out.println(findId);
		if (findId == null) {
			throw new BusinessException(ErrorCode.UNABLE_PRODUCTNUM, "일치하는 정보가 없습니다.");
		}
		dao.updateProduct(idx, dto);

	}

	public void deleteProduct(final Long idx) throws SQLException {
		Long findId = selectOneProduct(idx).orElseGet(() -> null);
		
		if (findId == null) {
			throw new BusinessException(ErrorCode.UNABLE_PRODUCTNUM, "일치하는 정보가 없습니다.");
		}

		dao.deleteProduct(idx);
	}
}
