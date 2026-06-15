package com.ighor.api.e_commerce.dto.entity;

import com.ighor.api.e_commerce.model.entity.User;
import java.time.LocalDateTime;
import java.util.List;

public record CartDTO(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long userId,
        List<Long> itemIds
) {
}
