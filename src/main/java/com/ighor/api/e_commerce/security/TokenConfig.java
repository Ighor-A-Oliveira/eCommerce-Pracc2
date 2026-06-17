package com.ighor.api.e_commerce.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.enums.Role;
import org.springframework.stereotype.Component;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {

    private String secret = "secret";

    Algorithm algorithm = Algorithm.HMAC256(secret);

    public String  generateToken(User user) {
        //Cria objeto JWT
        return JWT.create()
                //Adiciona uma claim chamada "userId" dentro do JWT: "userID":123
                .withClaim("userId", user.getId())
                .withClaim("role", user.getRole().name())
                //Define quem é o usuário
                .withSubject(user.getEmail())
                //O token vai expirar em 24 horas (86400 segundos)
                .withExpiresAt(Instant.now().plusSeconds(86400))
                //Define quando o token foi emitido.
                .withIssuedAt(Instant.now())
                //Assina o token com o algoritmo HMAC256 usando uma senha
                .sign(algorithm);
    }

    //Valida o token JWT
    public Optional<JWTUserData> validateToken(String token) {

        try {
            //Recria o algoritmo usado na assinatura do token
            //Se o token tiver sido alterado, a validação vai falhar.
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //Valida e decodifica o token
            DecodedJWT decode = JWT.require(algorithm)
                    .build().verify(token);

            // Recupera a String da role de dentro das claims
            String roleStr = decode.getClaim("role").asString();

            // Converte a String de volta para o seu Enum Role (com segurança)
            Role userRole = (roleStr != null) ? Role.valueOf(roleStr) : Role.CUSTOMER;


            //Constrói o objeto com os dados do usuário extraídos do token
            return Optional.of(JWTUserData.builder()
                    .id(decode.getClaim("userId").asLong())
                    .email(decode.getSubject())
                    .role(userRole)
                    .build());

        } catch (JWTVerificationException ex) {
            //Se o token for inválido → Retorna vazio
            return Optional.empty();
        }
    }
}
