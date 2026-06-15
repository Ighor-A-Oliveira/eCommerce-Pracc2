package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.entity.UserPaymentMethod;
import com.ighor.api.e_commerce.repo.UserPaymentMethodRepo;
import com.ighor.api.e_commerce.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserPaymentMethodService {
    private final UserPaymentMethodRepo paymentMethodRepo;
    private final UserRepo userRepo;

    public UserPaymentMethodService(UserPaymentMethodRepo paymentMethodRepository,
                                    UserRepo userRepository) {
        this.paymentMethodRepo = paymentMethodRepository;
        this.userRepo = userRepository;
    }

    public List<UserPaymentMethod> findByUserId(Long userId) {
        return paymentMethodRepo.findByUserId(userId);
    }


    public UserPaymentMethod save(Long userId, UserPaymentMethod paymentMethod) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        paymentMethod.setUser(user);

        // Se for o primeiro método ou marcado como default, remove default dos outros
        if (paymentMethod.getIsDefault() || paymentMethodRepo.findByUserId(userId).isEmpty()) {
            paymentMethodRepo.findByUserId(userId)
                    .forEach(pm -> pm.setIsDefault(false));
            paymentMethod.setIsDefault(true);
        }

        return paymentMethodRepo.save(paymentMethod);
    }

    public void delete(Long id, Long userId) {
        UserPaymentMethod method = paymentMethodRepo.findByIdAndUserId(id, userId).orElseThrow(() -> new RuntimeException());
        paymentMethodRepo.delete(method);
    }

    public UserPaymentMethod setAsDefault(Long id, Long userId) {
        // Remove default de todos
        paymentMethodRepo.findByUserId(userId)
                .forEach(pm -> pm.setIsDefault(false));

        UserPaymentMethod method = paymentMethodRepo.findByIdAndUserId(id, userId).orElseThrow(() -> new RuntimeException());
        method.setIsDefault(true);
        return paymentMethodRepo.save(method);
    }
}
