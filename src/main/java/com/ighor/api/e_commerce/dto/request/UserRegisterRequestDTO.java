package com.ighor.api.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequestDTO(
        @NotBlank(message = "Nome é obrigatorio!")
        String name,
        @NotBlank(message = "Email é obrigatorio!")
        String email,
        @NotBlank(message = "Senha é obrigatoria!")
        String password,
        @NotBlank(message = "Telefone é obrigatorio!")
        String phone
) {
}
