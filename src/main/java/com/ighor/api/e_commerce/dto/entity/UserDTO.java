package com.ighor.api.e_commerce.dto.entity;

import com.ighor.api.e_commerce.model.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        String phone,
        Role role,
        LocalDateTime createdAt,
        Long cartId,
        List<Long> addressIds,
        List<Long> orderIds
) {
}
