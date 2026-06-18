package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.request.UserPaymentMethodRequestDTO;
import com.ighor.api.e_commerce.dto.response.UserPaymentMethodResponseDTO;
import com.ighor.api.e_commerce.model.entity.UserPaymentMethod;
import com.ighor.api.e_commerce.service.UserPaymentMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-method")
public class UserPaymentMethodController {
    private final UserPaymentMethodService paymentMethodService;

    public UserPaymentMethodController(UserPaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @Operation(summary = "Cria registro da forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro da forma de pagamento encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro dessa forma de pagamento já existe no sistema"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public UserPaymentMethodResponseDTO salvarMetodoPagamento(@Valid @PathVariable Long userId, @Valid @RequestBody UserPaymentMethodRequestDTO paymentMethod) {
        return paymentMethodService.salvarMetodoPagamento(userId, paymentMethod);
    }

    @Operation(summary = "Busca uma forma de pagamento pelo userId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de forma de pagamento encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de forma de pagamento não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public List<UserPaymentMethod> listarMetodoPagamentoPorUserId(@Valid @PathVariable Long userId) {
        return paymentMethodService.listarMetodoPagamentoPorUserId(userId);
    }

    @Operation(summary = "Define uma forma de pagamento como padrão")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alteração de forma de pagamento feita com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro de forma de pagamento não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{userId}/{paymentMethodId}/default")
    @PreAuthorize("#userId == authentication.principal.id")
    public UserPaymentMethod definirComoPadrao(@Valid @PathVariable Long userId, @Valid @PathVariable Long paymentMethodId) {
        return paymentMethodService.definirComoPadrao(paymentMethodId, userId);
    }

    @Operation(summary = "Deleta o registro de forma de pagamento peo userId e paymentMethodId")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de forma de pagamento deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de forma de pagamento não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{userId}/{paymentMethodId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public void deletarMetodoPagamentoPorId(@Valid @PathVariable Long userId, @Valid @PathVariable Long paymentMethodId) {
        paymentMethodService.deletarMetodoPagamentoPorId(paymentMethodId, userId);
    }
}
