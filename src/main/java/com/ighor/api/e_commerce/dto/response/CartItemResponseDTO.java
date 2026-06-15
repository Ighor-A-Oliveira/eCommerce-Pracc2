package com.ighor.api.e_commerce.dto.response;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {}
