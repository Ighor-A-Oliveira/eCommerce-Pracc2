package com.ighor.api.e_commerce.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddressDTO(
        Long id,
        @NotBlank
        String street,
        @NotNull
        @Positive
        String number,
        String complement,
        @NotBlank
        String neighborhood,
        @NotBlank
        String city,
        @NotBlank
        String state,
        @NotBlank
        String zipCode,
        Boolean isDefault,
        @NotNull
        Long userId
) {
}
