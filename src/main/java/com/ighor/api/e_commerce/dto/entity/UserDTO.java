package com.ighor.api.e_commerce.dto.entity;

import com.ighor.api.e_commerce.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        String phone,
        Role role,
        LocalDateTime createdAt,
        Long cartId,                // ID do Carrinho
        List<Long> addressIds,      // Lista de IDs dos Endereços
        List<Long> orderIds
) {
}
