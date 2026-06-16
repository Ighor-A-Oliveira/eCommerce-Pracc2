package com.ighor.api.e_commerce.service;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.entity.UserPaymentMethod;
import com.ighor.api.e_commerce.repo.UserPaymentMethodRepo;
import com.ighor.api.e_commerce.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//Ainda tenho que criar o dto e fazer o mapper

@Service
@Transactional
public class UserPaymentMethodService {
    private final UserPaymentMethodRepo paymentMethodRepo;
    private final UserRepo userRepo;

    public UserPaymentMethodService(UserPaymentMethodRepo paymentMethodRepo, UserRepo userRepo) {
        this.paymentMethodRepo = paymentMethodRepo;
        this.userRepo = userRepo;
    }

    public List<UserPaymentMethod> acharMetodoDePagamentoPeloUserId(Long userId) {
        //Retornar uma lista com todos os metodos de pagamento do usuario
        return paymentMethodRepo.findByUserId(userId);
    }


    public UserPaymentMethod salvarMetodoDePagamento(Long userId, UserPaymentMethod paymentMethod) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        //essa forma de pagamento eh vinculada a um usuario
        paymentMethod.setUser(user);

        // Se for o primeiro method ou marcado como default, remove default dos outros
        List<UserPaymentMethod> existing = paymentMethodRepo.findByUserId(userId);
        if (paymentMethod.getIsDefault() || existing.isEmpty()) {
            existing.forEach(pm -> pm.setIsDefault(false));
            paymentMethod.setIsDefault(true);
        }

        //sava a forma de pagamento
        return paymentMethodRepo.save(paymentMethod);
    }

    public void deletarPorUserId(Long id, Long userId) {
        UserPaymentMethod method = paymentMethodRepo.findByIdAndUserId(id, userId).orElseThrow(() -> new RuntimeException());
        paymentMethodRepo.delete(method);
    }

    public UserPaymentMethod salvarComoPadrao(Long id, Long userId) {
        // Remove default de todos
        paymentMethodRepo.findByUserId(userId).forEach(pm -> pm.setIsDefault(false));

        UserPaymentMethod method = paymentMethodRepo.findByIdAndUserId(id, userId).orElseThrow(() -> new RuntimeException());
        method.setIsDefault(true);
        return paymentMethodRepo.save(method);
    }
}
