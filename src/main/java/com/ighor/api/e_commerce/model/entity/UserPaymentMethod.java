package com.ighor.api.e_commerce.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ighor.api.e_commerce.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_payment_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    // Para cartões
    private String lastFourDigits;
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;

    // Token do gateway de pagamento (Stripe, PagSeguro, Mercado Pago, etc.)
    private String token;

    private Boolean isDefault = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
