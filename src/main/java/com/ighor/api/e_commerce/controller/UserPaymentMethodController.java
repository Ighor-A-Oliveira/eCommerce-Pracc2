package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.model.entity.UserPaymentMethod;
import com.ighor.api.e_commerce.service.UserPaymentMethodService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-methods")
public class UserPaymentMethodController {
    private final UserPaymentMethodService paymentMethodService;

    public UserPaymentMethodController(UserPaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public UserPaymentMethod salvarMetodoPagamento(@PathVariable Long userId, @RequestBody UserPaymentMethod paymentMethod) {
        return paymentMethodService.salvarMetodoPagamento(userId, paymentMethod);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public List<UserPaymentMethod> listarMetodoPagamentoPorUserId(@PathVariable Long userId) {
        return paymentMethodService.listarMetodoPagamentoPorUserId(userId);
    }

    @PutMapping("/{userId}/{paymentMethodId}/default")
    @PreAuthorize("#userId == authentication.principal.id")
    public UserPaymentMethod definirComoPadrao(@PathVariable Long userId, @PathVariable Long paymentMethodId) {
        return paymentMethodService.definirComoPadrao(paymentMethodId, userId);
    }

    @DeleteMapping("/{userId}/{paymentMethodId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public void deletarMetodoPagamentoPorId(@PathVariable Long userId, @PathVariable Long paymentMethodId) {
        paymentMethodService.deletarMetodoPagamentoPorId(paymentMethodId, userId);
    }
}
