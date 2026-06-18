package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.dto.request.UserLoginRequestDTO;
import com.ighor.api.e_commerce.dto.request.UserRegisterRequestDTO;
import com.ighor.api.e_commerce.dto.response.UserLoginResponseDTO;
import com.ighor.api.e_commerce.mapper.UserMapper;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.enums.Role;
import com.ighor.api.e_commerce.repo.UserRepo;
import com.ighor.api.e_commerce.security.TokenConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenConfig tokenConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should create user successfully")
    void criarUsuarioCase1(){

        UserRegisterRequestDTO dto =
                new UserRegisterRequestDTO(
                        "Ighor",
                        "ighor@email.com",
                        "123",
                        "11999999999"
                );

        when(userRepo.findByEmail(dto.email()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(dto.password()))
                .thenReturn("senhaHash");

        userService.criarUsuario(dto);

        verify(userRepo).save(any(User.class));
    }


    @Test
    void shouldSaveCorrectUser(){

        UserRegisterRequestDTO dto =
                new UserRegisterRequestDTO(
                        "Ighor",
                        "ighor@email.com",
                        "123",
                        "999999"
                );

        when(userRepo.findByEmail(any()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("123"))
                .thenReturn("hash123");

        userService.criarUsuario(dto);

        ArgumentCaptor<User> captor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepo).save(captor.capture());

        User savedUser = captor.getValue();

        assertEquals("Ighor", savedUser.getName());
        assertEquals("ighor@email.com", savedUser.getEmail());
        assertEquals("999999", savedUser.getPhone());
        assertEquals("hash123", savedUser.getPassword());
        assertEquals(Role.CUSTOMER, savedUser.getRole());

    }



    @Test
    void buscarUsuarioPorIdCase1(){

        User user = new User();

        UserDTO dto = new UserDTO(
                1L,
                "Ighor",
                "ighor@email.com",
                "999",
                Role.CUSTOMER,
                LocalDateTime.now(),
                null,
                List.of(),
                List.of()
        );

        when(userRepo.findById(1L))
                .thenReturn(Optional.of(user));

        when(userMapper.userParaDTO(user))
                .thenReturn(dto);

        UserDTO result =
                userService.buscarUsuarioPorId(1L);

        assertNotNull(result);

        assertEquals("Ighor", result.name());

        verify(userMapper).userParaDTO(user);
    }

    @Test
    void shouldLoginSuccessfully(){

        User user = new User();

        user.setEmail("ighor@email.com");

        UserLoginRequestDTO request =
                new UserLoginRequestDTO(
                        "ighor@email.com",
                        "123"
                );

        Authentication authentication =
                mock(Authentication.class);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(user);

        when(tokenConfig.generateToken(user))
                .thenReturn("jwt-teste");

        UserLoginResponseDTO response =
                userService.fazerLoginUsuario(request);

        assertEquals(
                "jwt-teste",
                response.token()
        );
    }


    @Test
    void shouldThrowExceptionWhenCredentialsAreInvalid(){

        UserLoginRequestDTO request =
                new UserLoginRequestDTO(
                        "ighor@email.com",
                        "senhaErrada"
                );

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(
                BadCredentialsException.class,
                () -> userService.fazerLoginUsuario(request)
        );
    }
}