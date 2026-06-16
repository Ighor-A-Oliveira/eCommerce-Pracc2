package com.ighor.api.e_commerce.repo;

import com.ighor.api.e_commerce.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);


}
