package gift.Entity;

import java.io.Serializable;
import java.util.Objects;

public class WishId implements Serializable {
    private String member;
    private Long product;
    private Long option;

    public WishId() {}

    public WishId(String member, Long product, Long option) {
        this.member = member;
        this.product = product;
        this.option = option;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WishId)) return false;
        WishId wishId = (WishId) o;
        return Objects.equals(member, wishId.member) &&
                Objects.equals(product, wishId.product)&&
                Objects.equals(option, wishId.option);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, product, option);
    }
}

