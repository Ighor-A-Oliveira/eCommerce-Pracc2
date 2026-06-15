package com.ighor.api.e_commerce.dto.request;

import com.ighor.api.e_commerce.model.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record LoginRequestDTO(
        @NotEmpty(message = "Email é obrigatorio!")
        String email,
        @NotEmpty(message = "Senha é obrigatoria!")
        String password
) {

}
