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
    public UserPaymentMethod create(
            @PathVariable Long userId,
            @RequestBody UserPaymentMethod paymentMethod) {

        return paymentMethodService.save(userId, paymentMethod);
    }

    @GetMapping("/{userId}")
    public List<UserPaymentMethod> listByUser(
            @PathVariable Long userId) {

        return paymentMethodService.findByUserId(userId);
    }

    @PutMapping("/{userId}/{paymentMethodId}/default")
    public UserPaymentMethod setDefault(
            @PathVariable Long userId,
            @PathVariable Long paymentMethodId) {

        return paymentMethodService.setAsDefault(paymentMethodId, userId);
    }

    @DeleteMapping("/{userId}/{paymentMethodId}")
    public void delete(
            @PathVariable Long userId,
            @PathVariable Long paymentMethodId) {

        paymentMethodService.delete(paymentMethodId, userId);
    }
}
