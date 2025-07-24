package gift.request;

public class WishRequest {
    private final Long productId;
    private final Long optionId;

    public WishRequest(Long productId, Long optionId) {
        this.productId = productId;
        this.optionId = optionId;
    }

    public Long getProductId() {
        return productId;
    }
    public Long getOptionId() {return optionId;}

}

