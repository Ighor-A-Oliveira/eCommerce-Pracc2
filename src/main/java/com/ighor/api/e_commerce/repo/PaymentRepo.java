package com.ighor.api.e_commerce.repo;

import com.ighor.api.e_commerce.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
