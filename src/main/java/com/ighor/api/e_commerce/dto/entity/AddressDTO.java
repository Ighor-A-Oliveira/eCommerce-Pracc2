package com.ighor.api.e_commerce.dto.entity;

import com.ighor.api.e_commerce.model.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record AddressDTO(
        Long id,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        Boolean isDefault,
        Long userId
) {
}
