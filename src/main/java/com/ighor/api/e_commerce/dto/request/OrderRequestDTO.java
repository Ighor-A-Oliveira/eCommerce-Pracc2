package com.ighor.api.e_commerce.dto.request;

public record OrderRequestDTO(
        Long userId,
        Long addressId,
        Long paymentId
) {}
