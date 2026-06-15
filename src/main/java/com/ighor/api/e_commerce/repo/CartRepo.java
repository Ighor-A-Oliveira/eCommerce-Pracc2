package com.ighor.api.e_commerce.repo;

import com.ighor.api.e_commerce.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
}
