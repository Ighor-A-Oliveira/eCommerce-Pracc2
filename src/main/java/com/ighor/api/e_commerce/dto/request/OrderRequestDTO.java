package com.ighor.api.e_commerce.dto.request;

import jakarta.validation.constraints.NotNull;

public record OrderRequestDTO(
        @NotNull(message = "Id do usuario eh obrigatorio")
        Long userId,
        @NotNull(message = "Id do endereco eh obrigatorio")
        Long addressId,
        @NotNull(message = "Id do metodo de pagamento eh obrigatorio")
        Long paymentId
) {}
