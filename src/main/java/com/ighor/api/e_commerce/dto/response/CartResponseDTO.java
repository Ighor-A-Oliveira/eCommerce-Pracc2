package com.ighor.api.e_commerce.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record  CartResponseDTO(
        Long id,
        Integer totalItems,
        BigDecimal totalPrice,
        List<CartItemResponseDTO> items
) {
}
