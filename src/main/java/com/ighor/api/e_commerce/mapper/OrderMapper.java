package com.ighor.api.e_commerce.mapper;

import com.ighor.api.e_commerce.dto.response.OrderItemResponseDTO;
import com.ighor.api.e_commerce.dto.response.OrderResponseDTO;
import com.ighor.api.e_commerce.model.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDTO toDTO(Order order) {

        List<OrderItemResponseDTO> items = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getTrackingCode(),
                order.getCreatedAt(),
                order.getDeliveryAddress().getId(),
                items
        );
    }

    public List<OrderResponseDTO> listToDTO(List<Order> orders) {

        return orders.stream()
                .map(this::toDTO)
                .toList();
    }
}
