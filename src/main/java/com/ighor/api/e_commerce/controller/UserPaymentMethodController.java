package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.model.entity.UserPaymentMethod;
import com.ighor.api.e_commerce.service.UserPaymentMethodService;
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
    public UserPaymentMethod salvar(@PathVariable Long userId, @RequestBody UserPaymentMethod paymentMethod) {
        return paymentMethodService.salvarMetodoDePagamento(userId, paymentMethod);
    }

    @GetMapping("/{userId}")
    public List<UserPaymentMethod> listByUser(@PathVariable Long userId) {
        return paymentMethodService.acharMetodoDePagamentoPeloUserId(userId);
    }

    @PutMapping("/{userId}/{paymentMethodId}/default")
    public UserPaymentMethod setDefault(@PathVariable Long userId, @PathVariable Long paymentMethodId) {
        return paymentMethodService.salvarComoPadrao(paymentMethodId, userId);
    }

    @DeleteMapping("/{userId}/{paymentMethodId}")
    public void delete(@PathVariable Long paymentMethodId, @PathVariable Long userId) {
        paymentMethodService.deletarPorUserId(paymentMethodId, userId);
    }
}
