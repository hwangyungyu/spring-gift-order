package gift.response;

import gift.Entity.Wish;

public class WishResponse {

    private Long productId;
    private String productName;
    private int price;
    private String imageUrl;

    private Long optionId;
    private String optionName;
    private int optionQuantity;

    public WishResponse(Wish wish) {
        this.productId = wish.getProduct().getId();
        this.productName = wish.getProduct().getName();
        this.price = wish.getProduct().getPrice();
        this.imageUrl = wish.getProduct().getImageUrl();

        this.optionId = wish.getOption().getId();
        this.optionName = wish.getOption().getName();
        this.optionQuantity = wish.getOption().getQuantity();
    }

}
