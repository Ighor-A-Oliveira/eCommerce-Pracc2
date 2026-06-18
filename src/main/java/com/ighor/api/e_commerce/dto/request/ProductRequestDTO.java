package com.ighor.api.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductRequestDTO(
        @NotBlank(message = "Nome do produto é obrigatorio!")
        String name,
        @NotBlank(message = "Descricao do produto é obrigatorio!")
        String description,
        @NotNull(message = "Preco do produto é obrigatorio!")
        @Positive
        BigDecimal price,
        @NotNull(message = "Quantidade em estoque do produto é obrigatorio!")
        @PositiveOrZero
        Integer stockQuantity,
        @NotBlank(message = "URL da imagem do produto é obrigatorio!")
        String imageUrl,
        @NotBlank(message = "SKU do produto é obrigatorio!")
        String sku,
        @NotNull(message = "Eh obrigatorio! sinalizar se o produto esta ativo")
        Boolean active,
        LocalDateTime createdAt,
        @NotNull(message = "Id da categoria do produto é obrigatorio!")
        Long categoryId
) {

}
