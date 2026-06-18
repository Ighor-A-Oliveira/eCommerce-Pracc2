package com.ighor.api.e_commerce.repo;

import com.ighor.api.e_commerce.dto.request.UserRegisterRequestDTO;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.enums.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepoTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Test
    @DisplayName("Should return user successfully")
    void findByEmailCase1() {
        UserRegisterRequestDTO dto = new UserRegisterRequestDTO(
                "User Silva",
                "user@email.com",
                "123",
                "123"
        );
        this.createUser(dto);
        Optional<User> result = this.userRepo.findByEmail(dto.email());

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should return null")
    void findByEmailCase2() {
        UserRegisterRequestDTO dto = new UserRegisterRequestDTO(
                "User Silva",
                "user@email.com",
                "123",
                "123"
        );
        this.createUser(dto);
        Optional<User> result = this.userRepo.findByEmail(dto.email()+"1");

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserRegisterRequestDTO dto){
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setPhone(dto.phone());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.CUSTOMER);

        this.entityManager.persist(user);
        this.entityManager.flush();
        return user;
    }

    @TestConfiguration
    static class TestConfig{

        @Bean
        PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
    }
}