package com.ighor.api.e_commerce.dto.request;

import com.ighor.api.e_commerce.model.enums.Role;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record UserUpdateRequestDTO(
        @NotBlank(message = "Id do usuario eh obrigatorio")
        Long id,
        String name,
        String email,
        String phone
) {
}
