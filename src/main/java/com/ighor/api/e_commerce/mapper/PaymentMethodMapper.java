package com.ighor.api.e_commerce.mapper;

import com.ighor.api.e_commerce.dto.request.UserPaymentMethodRequestDTO;
import com.ighor.api.e_commerce.model.entity.UserPaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper {

    public UserPaymentMethod dtoParaEntidade(UserPaymentMethodRequestDTO dto) {

        UserPaymentMethod paymentMethod = new UserPaymentMethod();

        paymentMethod.setMethod(dto.method());
        paymentMethod.setLastFourDigits(dto.lastFourDigits());
        paymentMethod.setCardHolderName(dto.cardHolderName());
        paymentMethod.setExpiryMonth(dto.expiryMonth());
        paymentMethod.setExpiryYear(dto.expiryYear());
        paymentMethod.setToken(dto.token());

        return paymentMethod;
    }
}
