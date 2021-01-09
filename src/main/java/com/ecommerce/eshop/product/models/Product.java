package com.ecommerce.eshop.product.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private BigDecimal price;
    private Integer quantity;
    private boolean isPromo;
    @ManyToOne(cascade = CascadeType.DETACH)
    private ProductCategory category;
    private BigDecimal promoPrice;
    @OneToMany (mappedBy = "productId")
    private List<ProductImage> productImages;
    @CreationTimestamp
    private LocalDateTime creationTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
    private boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return isPromo == product.isPromo &&
                name.equals(product.name) &&
                description.equals(product.description) &&
                price.equals(product.price) &&
                Objects.equals(quantity, product.quantity) &&
                Objects.equals(category, product.category) &&
                Objects.equals(promoPrice, product.promoPrice) &&
                Objects.equals(productImages, product.productImages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, quantity, isPromo, category, promoPrice, productImages);
    }
}
