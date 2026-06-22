package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.AddressDTO;
import com.ighor.api.e_commerce.dto.request.OrderRequestDTO;
import com.ighor.api.e_commerce.dto.request.UserPaymentMethodRequestDTO;
import com.ighor.api.e_commerce.dto.request.UserRegisterRequestDTO;
import com.ighor.api.e_commerce.mapper.OrderMapper;
import com.ighor.api.e_commerce.model.entity.*;
import com.ighor.api.e_commerce.model.enums.OrderStatus;
import com.ighor.api.e_commerce.model.enums.PaymentMethod;
import com.ighor.api.e_commerce.model.enums.PaymentStatus;
import com.ighor.api.e_commerce.model.enums.Role;
import com.ighor.api.e_commerce.repo.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private AddressRepo addrRepo;
    @Mock
    private PaymentRepo payRepo;
    @Mock
    private UserPaymentMethodRepo paymentMethodRepo;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private User userServ;
    @Mock
    private AddressService addrServ;
    @Mock
    private UserPaymentMethodService payMethodServ;

    PasswordEncoder passwordEncoder;

    @InjectMocks
    private OrderService orderServ;

    User user;
    Address addr;
    UserPaymentMethod payMethod;
    Cart cart;
    OrderRequestDTO dto;
    Order order;
    Payment payLog;

    @BeforeEach
    public void setUp(){
        CartItem cartItem = new CartItem();
        cartItem.setProduct(new Product());
        cartItem.setQuantity(2);
        cartItem.setUnitPrice(new BigDecimal("50.00"));
        cart = new Cart();
        cart.getItems().add(cartItem);

        //Tem que ter um User, um Address e um UserPaymentMethod ja criado
        user = new User();
        user.setId(1L);
        user.setCreatedAt(LocalDateTime.now());
        user.setName("user");
        user.setEmail("user@email.com");
        user.setPassword("123");
        user.setPhone("123");
        user.setRole(Role.CUSTOMER);
        user.setCart(cart);


        addr = new Address(
                1L,
                "Rua Usuario",
                "24",
                "",
                "Vila do Usuario",
                "Sao Paulo",
                "Sao Paulo",
                "000100-000",
                true,
                user
        );


        payMethod = new UserPaymentMethod();
        payMethod.setId(1L);
        payMethod.setUser(user);
        payMethod.setMethod(PaymentMethod.PIX);
        payMethod.setLastFourDigits(null);
        payMethod.setCardHolderName("User");
        payMethod.setExpiryMonth(null);
        payMethod.setExpiryYear(null);
        payMethod.setToken("token");

        dto = new OrderRequestDTO(1L, 2L, 3L);

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setDeliveryAddress(addr);
        order.setUserPaymentMethod(payMethod);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderItems(List.of(new OrderItem()));

        payLog = new Payment();
        payLog.setId(1L);
        payLog.setOrder(order);
        payLog.setMethod(payMethod.getMethod());
        payLog.setStatus(PaymentStatus.PENDING);


        order.setPayment(payLog);
    }

    @Test
    @DisplayName("Deve criar um pedido")
    void criarPedido() {
        when(userRepo.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(addrRepo.findById(2L)).thenReturn(Optional.ofNullable(addr));
        when(paymentMethodRepo.findById(3L)).thenReturn(Optional.ofNullable(payMethod));

        orderServ.criarPedido(dto);

        //checando se salvou order
        verify(orderRepo).save(any(Order.class));
        //checando se salvou payment
        verify(payRepo).save(any(Payment.class));
        // assertTrue(cart.getItems().isEmpty());
    }

    @Test
    @DisplayName("Deve buscar um pedido por id")
    void buscarPedidoPorId() {
        when(orderRepo.findById(1L)).thenReturn(Optional.ofNullable(order));
        orderServ.buscarPedidoPorId(1L);
        verify(orderRepo).findById(1L);
    }

    @Test
    @DisplayName("Deve criar uma lista de pedidos feitos pelo usuario")
    void listarPedidosUsuario() {
        //configurando para retornar a order
        when(orderRepo.findByUserId(1L)).thenReturn(List.of(order));
        //acionando o metodo
        orderServ.listarPedidosUsuario(1L);
        //checando se retornou certo
        verify(orderRepo).findByUserId(1L);
    }

    @Test
    @DisplayName("Deve cancelar um pedido")
    void cancelarPedidoPorId() {
        when(orderRepo.findById(1L)).thenReturn(Optional.ofNullable(order));
        orderServ.cancelarPedidoPorId(1L);
        verify(orderRepo).findById(1L);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals(PaymentStatus.REFUNDED, order.getPayment().getStatus());
        verify(payRepo).save(any(Payment.class));
        verify(orderRepo).save(any(Order.class));
    }

    @Test
    @DisplayName("Deve atualizar um pedido")
    void atualizarPedido() {
    }



}