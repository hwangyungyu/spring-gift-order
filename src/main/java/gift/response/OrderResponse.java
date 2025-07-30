package gift.response;

import gift.Entity.Order;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long optionId,
        int quantity,
        LocalDateTime orderDateTime,
        String message
) {
    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getOption().getId(),
                order.getQuantity(),
                order.getOrderDateTime(),
                order.getMessage()
        );
    }
}

