package gift.response;

import gift.Entity.Wish;

public record WishResponse(
        Long productId,
        String productName,
        int price,
        String imageUrl,
        Long optionId,
        String optionName,
        int optionQuantity
) {
    public WishResponse(Wish wish) {
        this(
                wish.getProduct().getId(),
                wish.getProduct().getName(),
                wish.getProduct().getPrice(),
                wish.getProduct().getImageUrl(),
                wish.getOption().getId(),
                wish.getOption().getName(),
                wish.getOption().getQuantity()
        );
    }
}

