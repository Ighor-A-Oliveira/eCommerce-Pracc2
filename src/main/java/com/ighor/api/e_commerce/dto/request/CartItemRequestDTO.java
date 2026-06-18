package com.ighor.api.e_commerce.dto.request;

import jakarta.validation.constraints.NotNull;

public record CartItemRequestDTO(
        @NotNull(message = "Id do produto é obrigatorio!")
        Long productId,
        @NotNull(message = "Quantidade do produto eh obrigatorio!")
        Integer quantity
) {}
