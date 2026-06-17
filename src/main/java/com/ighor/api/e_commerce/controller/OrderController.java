package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.OrderDTO;
import com.ighor.api.e_commerce.dto.request.OrderRequestDTO;
import com.ighor.api.e_commerce.dto.response.OrderResponseDTO;
import com.ighor.api.e_commerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<String> criarPedido(@RequestParam Long userId, @RequestParam Long addressId, @RequestParam Long paymentMethodId) {
        orderService.criarPedido(userId, addressId, paymentMethodId);

        return ResponseEntity.ok("Pedido criado com sucesso");
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("#orderId == authentication.principal.id")
    public ResponseEntity<OrderResponseDTO> buscarPedidoPorId(@PathVariable Long orderId) {
        return ResponseEntity.ok(
                orderService.buscarPedidoPorId(orderId)
        );
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<OrderResponseDTO>> listarTodosPedidosDoUsuario(@PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.listarPedidosUsuario(userId)
        );
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("#orderId == authentication.principal.id")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long orderId) {
        orderService.cancelarPedidoPorId(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarPedido(@RequestBody OrderDTO order, @PathVariable Long orderId) {
        orderService.atualizarPedido(order, orderId);
        return ResponseEntity.noContent().build();
    }
}