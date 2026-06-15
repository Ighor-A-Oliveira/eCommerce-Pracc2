package com.ighor.api.e_commerce.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record UserRegisterRequestDTO(
        @NotEmpty(message = "Nome é obrigatorio!")
        String name,
        @NotEmpty(message = "Email é obrigatorio!")
        String email,
        @NotEmpty(message = "Senha é obrigatoria!")
        String password,
        @NotEmpty(message = "Telefone é obrigatorio!")
        String phone
) {
}
