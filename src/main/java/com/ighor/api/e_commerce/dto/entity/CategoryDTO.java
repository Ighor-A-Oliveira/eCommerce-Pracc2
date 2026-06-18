package com.ighor.api.e_commerce.dto.entity;

import com.ighor.api.e_commerce.model.entity.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CategoryDTO(
        Long id,
        @NotBlank
        @NotNull
        String name,
        @NotBlank
        @NotNull
        String slug,
        @NotBlank
        @NotNull
        String description,
        Boolean active,
        List<Long> productIds
) {
}
