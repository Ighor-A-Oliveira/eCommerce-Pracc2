package com.ighor.api.e_commerce.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cart_items")
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;

    //FK de Cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    //FK de Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
