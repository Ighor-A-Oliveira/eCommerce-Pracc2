package com.ighor.api.e_commerce.dto.response;

import com.ighor.api.e_commerce.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        OrderStatus status,
        BigDecimal totalAmount,
        String trackingCode,
        LocalDateTime createdAt,
        Long addressId,
        List<OrderItemResponseDTO> items
) {}
