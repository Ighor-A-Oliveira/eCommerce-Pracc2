package com.ighor.api.e_commerce.dto.request;


import jakarta.validation.constraints.NotNull;

public record CartRequestDTO(
        @NotNull(message = "Id do usuario é obrigatorio!")
        Long userId
) {
}
