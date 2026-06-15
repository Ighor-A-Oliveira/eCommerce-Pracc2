package com.ighor.api.e_commerce.model.entity;

import com.ighor.api.e_commerce.model.enums.PaymentMethod;
import com.ighor.api.e_commerce.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity                        // ← essa linha
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private BigDecimal amount;
    private String transactionId;
    private LocalDateTime paidAt;

    //FK de order
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
