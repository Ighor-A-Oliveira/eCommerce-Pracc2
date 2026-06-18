package com.ighor.api.e_commerce.dto.request;

import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.enums.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserPaymentMethodRequestDTO(
        @NotNull(message = "Metodo de pagamento eh obrigatorio")
        PaymentMethod method,

        @Size(min = 4, max = 4, message = "Digite apenas os 4 ultimos digitos")
        String lastFourDigits,

        @Size(max = 100, message = "Nome do titular do cartao nao pode passar de 100 caracteres")
        String cardHolderName,

        //@NotBlank(message = "Mes de expiracao do cartao eh obrigatio")
        String expiryMonth,

        //@NotBlank(message = "Ano de expiracao do cartao eh obrigatio")
        String expiryYear,

        //@NotBlank(message = "Token de pagamento eh obrigatorio")
        String token

) {
}
