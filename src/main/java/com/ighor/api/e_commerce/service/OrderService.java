package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.response.OrderResponseDTO;
import com.ighor.api.e_commerce.mapper.OrderMapper;
import com.ighor.api.e_commerce.model.entity.*;
import com.ighor.api.e_commerce.model.enums.OrderStatus;
import com.ighor.api.e_commerce.model.enums.PaymentStatus;
import com.ighor.api.e_commerce.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final AddressRepo addrRepo;
    private final CartService cartServ;
    private final PaymentRepo payRepo;
    private final UserPaymentMethodRepo paymentMethodRepo;
    private final OrderMapper orderMapper;

    public OrderService(
            OrderRepo orderRepo,
            PaymentRepo payRepo,
            UserRepo userRepo,
            AddressRepo addrRepo,
            CartService cartServ,
            UserPaymentMethodRepo paymentMethodRepo,
            OrderMapper orderMapper) {

        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.addrRepo = addrRepo;
        this.cartServ = cartServ;
        this.payRepo = payRepo;
        this.paymentMethodRepo = paymentMethodRepo;
        this.orderMapper = orderMapper;
    }

    //Criar pedido a partir do carrinho
    @Transactional
    public void criarPedido(Long userId, Long addressId, Long paymentMethodId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Address addr = addrRepo.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        UserPaymentMethod paymentMethod = paymentMethodRepo.findById(paymentMethodId)
                .orElseThrow(() -> new RuntimeException("Método de pagamento não encontrado"));

        Cart cart = user.getCart();

        if (cart == null) {
            throw new RuntimeException("Usuário não possui carrinho");
        }

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Carrinho vazio");
        }

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setUser(user);
        order.setDeliveryAddress(addr);
        order.setStatus(OrderStatus.PENDING);
        order.setUserPaymentMethod(paymentMethod);

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());

            orderItem.setSubtotal(
                    cartItem.getUnitPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );

            order.getOrderItems().add(orderItem);
        }

        order.calculateTotal();

        orderRepo.save(order);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(paymentMethod.getMethod());
        payment.setStatus(PaymentStatus.PENDING);

        payRepo.save(payment);

        cart.getItems().clear();
    }


    public OrderResponseDTO buscarPorId(Long id) {

        return orderMapper.toDTO(orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado")));
    }

    public List<OrderResponseDTO> listarPedidosUsuario(Long userId) {

        return orderMapper.toDTO(orderRepo.findByUserId(userId));
    }

    //Listar pedidos de um usuário
    //Buscar pedido por ID
    //Cancelar pedido
    //Atualizar status do pedido (admin)
}
