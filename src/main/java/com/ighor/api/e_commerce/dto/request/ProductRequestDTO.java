package com.ighor.api.e_commerce.dto.request;

import com.ighor.api.e_commerce.model.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductRequestDTO(
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String imageUrl,
        String sku,
        Boolean active,
        LocalDateTime createdAt,
        Long categoryId
) {

}
