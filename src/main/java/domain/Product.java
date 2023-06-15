package domain;

public class Product {
	private Long productId;
	private String cardName;
	private String cardType;
	private Long cardLimit;
	private Long categoryId;

	public Product() {

	}

	public Product(Long productId, String cardName, String cardType, Long cardLimit, Long categoryId) {
		super(); /// 부모 클래스 생성자 호출
		this.productId = productId;
		this.cardName = cardName;
		this.cardType = cardType;
		this.cardLimit = cardLimit;
		this.categoryId = categoryId;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Long getCardLimit() {
		return cardLimit;
	}

	public void setCardLimit(Long cardLimit) {
		this.cardLimit = cardLimit;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
