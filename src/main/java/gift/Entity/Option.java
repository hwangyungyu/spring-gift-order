package gift.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "options",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "name"}))
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^[\\w\\s\\(\\)\\[\\]\\+\\-\\&\\/]+$", message = "허용되지 않은 특수문자가 포함되어 있습니다.")
    private String name;

    @Min(1)
    @Max(99999999)
    private int quantity;

    // (옵션 여러 개 → 상품 하나)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Option() {}

    public Option(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public Product getProduct() { return product; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setProduct(Product product) { this.product = product; }
}

