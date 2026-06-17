package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.OrderDTO;
import com.ighor.api.e_commerce.dto.response.OrderResponseDTO;
import com.ighor.api.e_commerce.mapper.OrderMapper;
import com.ighor.api.e_commerce.model.entity.*;
import com.ighor.api.e_commerce.model.enums.OrderStatus;
import com.ighor.api.e_commerce.model.enums.PaymentStatus;
import com.ighor.api.e_commerce.model.enums.Role;
import com.ighor.api.e_commerce.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//terminar o atualizar pedido

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

        //Buscando user que fez o pedido
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        //Buscando endereco do user
        Address addr = addrRepo.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        //Buscando metodo de pagamento do user
        UserPaymentMethod paymentMethod = paymentMethodRepo.findById(paymentMethodId)
                .orElseThrow(() -> new RuntimeException("Método de pagamento não encontrado"));

        //Pegando acesso ao cart
        Cart cart = user.getCart();

        //Checagens
        if (cart == null) {
            throw new RuntimeException("Usuário não possui carrinho");
        }

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Carrinho vazio");
        }

        //Criando order com os dados coletados
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setUser(user);
        order.setDeliveryAddress(addr);
        order.setStatus(OrderStatus.PENDING);
        order.setUserPaymentMethod(paymentMethod);

        //Criando OrdemItems
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

        //Calculando valor total do pedido (metodo interno da entidade)
        order.calculateTotal();

        //salvando ordem no banco
        orderRepo.save(order);

        //Criando registro de pagmaento
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(paymentMethod.getMethod());
        payment.setStatus(PaymentStatus.PENDING);

        //Salvando registro no banco
        payRepo.save(payment);

        //Limpando carrinho
        cart.getItems().clear();
    }


    //Buscar pedido por ID
    public OrderResponseDTO buscarPedidoPorId(Long orderId) {

        return orderMapper.toDTO(orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado")));
    }

    //Listar pedidos de um usuário
    public List<OrderResponseDTO> listarPedidosUsuario(Long userId) {

        //Buscando todos os pedido feitos pelo userId
        List<Order> orders = orderRepo.findByUserId(userId);

        //Retornando a lista j[a convertida para DTO
        return orderMapper.listToDTO(orders);
    }



    //Cancelar pedido
    @Transactional
    public void cancelarPedidoPorId(Long orderId){
        //Buscando ordem especifica
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException());
        //Alterando o status
        order.setStatus(OrderStatus.CANCELLED);

        //Buscando registro de pagamento da ordem
        Payment payment = order.getPayment();
        //Alterado o status
        payment.setStatus(PaymentStatus.REFUNDED);
        //Alterando data de pagamento/reembolso
        payment.setPaidAt(LocalDateTime.now());

        //Salvando no banco
        payRepo.save(payment);
        orderRepo.save(order);
    }

    //Atualizar status do pedido (admin)
    @Transactional
    public void atualizarPedido(OrderDTO order, Long userId){

    }
}
