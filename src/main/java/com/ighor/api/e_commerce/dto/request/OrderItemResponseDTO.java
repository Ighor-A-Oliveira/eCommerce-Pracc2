package com.ighor.api.e_commerce.dto.request;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {}