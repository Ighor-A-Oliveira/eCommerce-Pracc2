package com.ighor.api.e_commerce.dto.request;

public record CartItemRequestDTO(
        Long productId,
        Integer quantity
) {}
