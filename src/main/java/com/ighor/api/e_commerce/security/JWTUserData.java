package com.ighor.api.e_commerce.security;

import com.ighor.api.e_commerce.model.enums.Role;
import lombok.Builder;

@Builder
public record JWTUserData(Long id, String email, Role role) {
}
