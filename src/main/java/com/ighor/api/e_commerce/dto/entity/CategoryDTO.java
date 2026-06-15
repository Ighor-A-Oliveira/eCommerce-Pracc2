package com.ighor.api.e_commerce.dto.entity;

import com.ighor.api.e_commerce.model.entity.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

public record CategoryDTO(
        Long id,
        String name,
        String slug,
        String description,
        Boolean active,
        List<Long> productIds
) {
}
