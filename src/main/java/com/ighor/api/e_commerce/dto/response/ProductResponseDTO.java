package com.ighor.api.e_commerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
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
