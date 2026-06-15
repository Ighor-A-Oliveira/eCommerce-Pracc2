package com.ighor.api.e_commerce.mapper;

import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.dto.response.ProductResponseDTO;
import com.ighor.api.e_commerce.model.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    public ProductResponseDTO productParaDTO(Product prod){
        ProductResponseDTO dto = new ProductResponseDTO(
                prod.getId(),
                prod.getName(),
                prod.getDescription(),
                prod.getPrice(),
                prod.getStockQuantity(),
                prod.getImageUrl(),
                prod.getSku(),
                prod.getActive(),
                prod.getCreatedAt(),
                prod.getCategory() != null ? prod.getCategory().getId() : null

        );

        return dto;
    }

    public List<ProductResponseDTO> productsParaDTO(List<Product> prods){
        if (prods == null) {
            return new ArrayList<>();
        }
        List<ProductResponseDTO> dtos = new ArrayList<>();

        //for each prod de prods
        for (Product prod : prods) {
            //Add DTO a lista
            dtos.add(productParaDTO(prod));
        }


        return dtos;
    }
}
