package com.ighor.api.e_commerce.mapper;

import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.dto.response.CartItemResponseDTO;
import com.ighor.api.e_commerce.model.entity.CartItem;
import com.ighor.api.e_commerce.model.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartItemMapper {

    public CartItemResponseDTO cartItemParaDTO(CartItem item){
        CartItemResponseDTO dto = new CartItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
        );

        return dto;
    }

    public List<CartItemResponseDTO> cartItemsParaDTO(List<CartItem> items){
        List<CartItemResponseDTO> dtos = new ArrayList<>();
        for (CartItem item : items){
            dtos.add(cartItemParaDTO(item));
        }

        return dtos;
    }
}
