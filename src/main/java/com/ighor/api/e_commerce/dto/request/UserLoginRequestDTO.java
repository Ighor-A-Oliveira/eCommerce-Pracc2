package com.ighor.api.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDTO(
        @NotBlank(message = "Email do usuario eh obrigatorio")
        String email,
        @NotBlank(message = "Senha do usuario eh obrigatorio")
        String password
) {
}
