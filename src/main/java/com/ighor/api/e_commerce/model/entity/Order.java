package com.ighor.api.e_commerce.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ighor.api.e_commerce.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", unique = true, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    private String trackingCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //FK de User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    //FK de Address
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address deliveryAddress;

    //FK de UserPaymentMethod
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_payment_method_id")
    private UserPaymentMethod userPaymentMethod;

    //FK de OrdemItem
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //FK de Payment
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    // Métodos de domínio
    public void calculateTotal() {
        this.totalAmount = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    public void confirm() {
        this.status = OrderStatus.CONFIRMED;
    }
}