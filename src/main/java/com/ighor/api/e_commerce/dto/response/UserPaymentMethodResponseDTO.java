package com.ighor.api.e_commerce.dto.response;

import com.ighor.api.e_commerce.model.enums.PaymentMethod;

import java.time.LocalDateTime;

public record UserPaymentMethodResponseDTO(
        Long id,

        PaymentMethod method,

        String lastFourDigits,

        String cardHolderName,

        String expiryMonth,

        String expiryYear,

        Boolean isDefault,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
