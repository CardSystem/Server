package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dao.ProductDAO;
import domain.ProductDTO;
import domain.ProductResponseDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductService {

	private ProductDAO dao = ProductDAO.getInstance();

	public Optional<Long> selectOneProduct(final Long idx) throws SQLException, IllegalAccessException {
		return Optional.ofNullable(dao.selectOneProduct(idx));
	}

	public Optional<List<ProductResponseDTO>> getProduct(){
		List<ProductResponseDTO> list = dao.getProdcut();
		return Optional.ofNullable(list);
	}
	/**
	 * 
	 * @param dto
	 * @throws SQLException SQL 예외 처리 <br>
	 * <b>DTO 객체는 주로 Presentation Layer에서 사용되는 데이터 전달객체입니다.</b>
	 * <b>따라서 Service Layer에서는 DTO를 엔티티로 변환하는 작업을 수행해야 합니다.</b>
	 * 
	 */
	public void registerProduct(final ProductDTO.RequestProduct dto) throws SQLException {
		// 서비스 레이어에서 추상적으로 로직 수행
		
		dao.register(dto);
	}
	// dto는 비즈니스 로직을 포함할 수 없다. 순수 데이터 전송 전달용 객체로만 사용한다.
	// service단에서 비즈니스 로직과 상태를 관리하는 entity로 변환하여 의존성을 분리한다.
	// 따라서 dto를 엔티티로 변환하는 작업은 Service 계층에서 처리하는 것이 적절하다.
	// 이를 통해 데이터의 일관성을 유지하고 비즈니스 규칙을 적용할 수 있다.

	public void updateProduct(final Long productId, final ProductDTO.RequestProduct dto)
			throws SQLException, IllegalAccessException {
		Long findId = selectOneProduct(productId).orElseGet(() -> null);
		System.out.println(findId);
		if (findId == null) {
			throw new IllegalAccessException("유효한 카드 상품이 아닙니다!");
		}
		dao.update(productId, dto);

	}

	public void deleteProduct(final Long productId) throws SQLException, IllegalAccessException {
		Long findId = selectOneProduct(productId).orElseGet(() -> null);
		System.out.println(findId);
		if (findId == null) {
			throw new IllegalAccessException("없는 상품입니다.");
		}

		dao.delete(productId);
	}
}
