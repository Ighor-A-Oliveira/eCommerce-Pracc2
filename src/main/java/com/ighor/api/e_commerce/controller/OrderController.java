package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.OrderDTO;
import com.ighor.api.e_commerce.dto.request.OrderRequestDTO;
import com.ighor.api.e_commerce.dto.response.OrderResponseDTO;
import com.ighor.api.e_commerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Cria um registro de order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de order criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<String> criarPedido(@Valid @RequestBody OrderRequestDTO dto) {
        orderService.criarPedido(dto);

        return ResponseEntity.ok("Pedido criado com sucesso");
    }

    @Operation(summary = "Busca um registro de order pelo orderId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de order encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de order não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{orderId}")
    @PreAuthorize("#orderId == authentication.principal.id")
    public ResponseEntity<OrderResponseDTO> buscarPedidoPorId(@Valid @PathVariable Long orderId) {
        return ResponseEntity.ok(
                orderService.buscarPedidoPorId(orderId)
        );
    }

    @Operation(summary = "Busca todos os registros de order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros de order encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registros de order não foram encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/user/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<OrderResponseDTO>> listarTodosPedidosDoUsuario(@Valid @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.listarPedidosUsuario(userId)
        );
    }

    @Operation(summary = "Busca um registro de order pelo orderId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de order deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de order não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{orderId}")
    @PreAuthorize("#orderId == authentication.principal.id")
    public ResponseEntity<Void> cancelarPedido(@Valid @PathVariable Long orderId) {
        orderService.cancelarPedidoPorId(orderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza um registro de order pelo orderId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de order atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de order não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarPedido(@Valid @RequestBody OrderDTO order, @Valid @PathVariable Long orderId) {
        orderService.atualizarPedido(order, orderId);
        return ResponseEntity.noContent().build();
    }
}