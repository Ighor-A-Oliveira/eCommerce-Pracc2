package com.ighor.api.e_commerce.repo;

import com.ighor.api.e_commerce.model.entity.UserPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPaymentMethodRepo extends JpaRepository<UserPaymentMethod, Long> {
    List<UserPaymentMethod> findByUserId(Long userId);

    Optional<UserPaymentMethod> findByIdAndUserId(Long id, Long userId);

    Optional<UserPaymentMethod> findByUserIdAndIsDefaultTrue(Long userId);
}
