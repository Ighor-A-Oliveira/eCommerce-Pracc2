package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.response.OrderResponseDTO;
import com.ighor.api.e_commerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> criarPedido(
            @RequestParam Long userId,
            @RequestParam Long addressId,
            @RequestParam Long paymentMethodId) {

        orderService.criarPedido(userId, addressId, paymentMethodId);

        return ResponseEntity.ok("Pedido criado com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> buscarPedido(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.buscarPorId(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> listarPedidosUsuario(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.listarPedidosUsuario(userId)
        );
    }
}