package com.ighor.api.e_commerce.service;
import com.ighor.api.e_commerce.dto.request.UserPaymentMethodRequestDTO;
import com.ighor.api.e_commerce.dto.response.UserPaymentMethodResponseDTO;
import com.ighor.api.e_commerce.exception.ResourceNotFoundException;
import com.ighor.api.e_commerce.mapper.PaymentMethodMapper;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.entity.UserPaymentMethod;
import com.ighor.api.e_commerce.model.enums.PaymentMethod;
import com.ighor.api.e_commerce.repo.UserPaymentMethodRepo;
import com.ighor.api.e_commerce.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//Ainda tenho que criar o dto e fazer o mapper

@Service
@Transactional
public class UserPaymentMethodService {
    private final UserPaymentMethodRepo paymentMethodRepo;
    private final UserRepo userRepo;
    private final PaymentMethodMapper payMapper;

    public UserPaymentMethodService(UserPaymentMethodRepo paymentMethodRepo, UserRepo userRepo, PaymentMethodMapper payMapper) {
        this.paymentMethodRepo = paymentMethodRepo;
        this.userRepo = userRepo;
        this.payMapper = payMapper;
    }

    public List<UserPaymentMethod> listarMetodoPagamentoPorUserId(Long userId) {
        //Retornar uma lista com todos os metodos de pagamento do usuario
        return paymentMethodRepo.findByUserId(userId);
    }

    @Transactional
    public UserPaymentMethodResponseDTO salvarMetodoPagamento(Long userId, UserPaymentMethodRequestDTO dto) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Nao foi encontrado usuario com id "+userId));
        UserPaymentMethod paymentMethod = payMapper.dtoParaEntidade(dto);


        //essa forma de pagamento eh vinculada a um usuario
        paymentMethod.setUser(user);

        // Se for o primeiro method ou marcado como default, remove default dos outros
        List<UserPaymentMethod> existing = paymentMethodRepo.findByUserId(userId);
        if (paymentMethod.getIsDefault() || existing.isEmpty()) {
            existing.forEach(pm -> pm.setIsDefault(false));
            paymentMethod.setIsDefault(true);
        }

        //salva a forma de pagamento
        paymentMethodRepo.save(paymentMethod);

        return new UserPaymentMethodResponseDTO(
                paymentMethod.getId(),
                paymentMethod.getMethod(),
                paymentMethod.getLastFourDigits(),
                paymentMethod.getCardHolderName(),
                paymentMethod.getExpiryMonth(),
                paymentMethod.getExpiryYear(),
                paymentMethod.getIsDefault(),
                paymentMethod.getCreatedAt(),
                paymentMethod.getUpdatedAt()
        );
    }

    @Transactional
    public void deletarMetodoPagamentoPorId(Long id, Long userId) {
        UserPaymentMethod method = paymentMethodRepo.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Nao foi possivel encontrar o metodo de pagamento do usuario"));
        paymentMethodRepo.delete(method);
    }

    @Transactional
    public UserPaymentMethod definirComoPadrao(Long id, Long userId) {
        // Remove default de todos
        paymentMethodRepo.findByUserId(userId).forEach(pm -> pm.setIsDefault(false));

        UserPaymentMethod method = paymentMethodRepo.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Nao foi possivel encontrar o metodo de pagamento do usuario"));
        method.setIsDefault(true);
        return paymentMethodRepo.save(method);
    }
}
