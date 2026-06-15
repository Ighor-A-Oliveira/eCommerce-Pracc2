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
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    //FK de Order
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    //FK de Product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
