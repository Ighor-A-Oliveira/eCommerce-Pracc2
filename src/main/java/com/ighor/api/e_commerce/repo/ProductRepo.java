package com.ighor.api.e_commerce.repo;

import com.ighor.api.e_commerce.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo  extends JpaRepository<Product, Long> {
}
