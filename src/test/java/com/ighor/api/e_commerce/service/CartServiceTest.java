package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.request.CartItemRequestDTO;
import com.ighor.api.e_commerce.dto.request.CartRequestDTO;
import com.ighor.api.e_commerce.dto.response.CartItemResponseDTO;
import com.ighor.api.e_commerce.dto.response.CartResponseDTO;
import com.ighor.api.e_commerce.exception.ResourceNotFoundException;
import com.ighor.api.e_commerce.mapper.CartItemMapper;
import com.ighor.api.e_commerce.model.entity.Cart;
import com.ighor.api.e_commerce.model.entity.CartItem;
import com.ighor.api.e_commerce.model.entity.Product;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.enums.Role;
import com.ighor.api.e_commerce.repo.CartRepo;
import com.ighor.api.e_commerce.repo.ProductRepo;
import com.ighor.api.e_commerce.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    User user;
    Cart cart;
    Cart cart2;
    List<CartItemResponseDTO> cartItemDTOs;
    CartResponseDTO responseDTO;
    CartRequestDTO requestDTO;
    Product prod;
    CartItem cartItem;
    CartItemResponseDTO cartItemResponseDTO;
    CartItemRequestDTO cartItemRequestDTO;

    @Mock
    private CartRepo cartRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private ProductRepo prodRepo;
    @Mock
    private CartItemMapper cartItemMapper;

    @InjectMocks
    CartService cartServ;

    @BeforeEach
    void setUp() {

        // USER
        user = new User();
        user.setId(1L);

        // PRODUCT
        prod = new Product();
        prod.setId(1L);
        prod.setName("Mouse");
        prod.setPrice(new BigDecimal("50"));
        prod.setStockQuantity(50);

        // CART ITEM
        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setProduct(prod);
        cartItem.setQuantity(2);
        cartItem.setUnitPrice(prod.getPrice());

        // CART
        cart = new Cart();
        cart.setId(10L);
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
        cart.getItems().add(cartItem);

        // ligações importantes (evita null no service)
        cartItem.setCart(cart);
        user.setCart(null);

        // DTO RESPONSE
        cartItemResponseDTO = new CartItemResponseDTO(
                1L,
                1L,
                "Mouse",
                2,
                new BigDecimal("50"),
                new BigDecimal("100")
        );

        cartItemRequestDTO = new CartItemRequestDTO(1L, 2);

        cartItemDTOs = List.of(cartItemResponseDTO);

        // REQUEST
        requestDTO = new CartRequestDTO(1L);

        cart2 = new Cart();
        cart2.setId(10L);
        cart2.setUser(user);
        cart2.setItems(new ArrayList<>());
        cart2.getItems().add(cartItem);
    }


    @Test
    void criarCarrinho1() {
        when(userRepo.findById(1L)).thenReturn(Optional.ofNullable(user));
        cartServ.criarCarrinho(requestDTO);
        ArgumentCaptor<Cart> captor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepo).save(captor.capture());
        Cart saved = captor.getValue();

        assertNotNull(saved.getUser());
        assertEquals(saved.getUser(), user);

    }

    @Test
    void criarCarrinho2() {
        cartServ.criarCarrinho(user);
        ArgumentCaptor<Cart> captor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepo).save(captor.capture());
        Cart saved = captor.getValue();

        assertNotNull(saved.getUser());
        assertEquals(saved.getUser(), user);

    }


    @Test
    void buscarCarrinho_criaNovoCarrinho() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        CartResponseDTO result = cartServ.buscarCarrinho(1L);

        verify(cartRepo).save(any(Cart.class));

        assertEquals(0, result.totalItems());
        assertEquals(BigDecimal.ZERO, result.totalPrice());
    }

    @Test
    void buscarCarrinho_comCarrinhoExistente() {
        //setup
        CartItem item = new CartItem();
        item.setId(1L);
        item.setProduct(prod);
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("50"));

        cart2.setItems(List.of(item));

        User user2 = new User();
        user2.setId(1L);
        user2.setCart(cart2);

        //definindo comportamento
        when(userRepo.findById(1L)).thenReturn(Optional.of(user2));
        when(cartItemMapper.cartItemParaDTO(any())).thenReturn(cartItemResponseDTO);

        //executando metodo
        CartResponseDTO result = cartServ.buscarCarrinho(1L);

        //checando se o mapper foi chamado
        verify(cartItemMapper).cartItemParaDTO(any());
        //checando resultado
        assertEquals(10L, result.id());//cartId
    }

    @Test
    void adicionarItem() {


        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(prodRepo.findById(1L)).thenReturn(Optional.of(prod));

        when(cartRepo.save(any(Cart.class))).thenAnswer(invocation -> {
            Cart c = invocation.getArgument(0);
            c.setId(10L);
            return c;
        });

        CartResponseDTO result = cartServ.adicionarItem(1L, cartItemRequestDTO);

        verify(cartRepo, atLeastOnce()).save(any(Cart.class));

        assertNotNull(user.getCart());
        assertEquals(1, user.getCart().getItems().size());
        assertEquals(10L, result.id());
        assertEquals(1, result.items().size());
    }

    @Test
    @DisplayName("Aumenta quantidade")
    void atualizarQuantidade1() {
        //definindo comportamento
        when(userRepo.findById(1L)).thenReturn(Optional.ofNullable(user));
        //Settando o cart
        user.setCart(cart2);
        //Definindo a quantidade inicial do cartItem
        cartItem.setQuantity(2);

        //chamando o metodo de atualizar
        cartServ.atualizarQuantidade(1L, 1L, 3L);

        //Checando se rodou legal
        assertEquals(5, cartItem.getQuantity());
        verify(cartRepo).save(cart2);

    }

    @Test
    @DisplayName("Quantidade = 0")
    void atualizarQuantidade2() {
        //Settando o cart
        user.setCart(cart2);
        //Definindo a quantidade inicial do cartItem
        cartItem.setQuantity(2);

        //chamando o metodo de atualizar
        cartServ.atualizarQuantidade(1L, 1L, 0L);

        verify(cartRepo, never()).save(any());
    }

    @Test
    @DisplayName("Quantidade fica <= 0")
    void atualizarQuantidade3() {
        when(userRepo.findById(1L)).thenReturn(Optional.ofNullable(user));
        //Settando o cart
        user.setCart(cart2);
        //Definindo a quantidade inicial do cartItem
        cartItem.setQuantity(2);

        cartServ.atualizarQuantidade(1L, 1L, -2L);
    }

    @Test
    @DisplayName("Produto não existe no carrinho")
    void atualizarQuantidade4() {
        when(userRepo.findById(1L)).thenReturn(Optional.ofNullable(user));
        //Settando o cart
        user.setCart(cart2);
        //Resetando os itens do carrinho
        cart2.setItems(new ArrayList<>());

        assertThrows(
                ResourceNotFoundException.class,
                () -> cartServ.atualizarQuantidade(1L, 1L, 1L)
        );
    }

    @Test
    @DisplayName("Remove item com sucesso")
    void removerItem1() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        user.setCart(cart2);
        cartServ.removerItem(1L, 1L);

        assertEquals(0, cart2.getItems().size());
        verify(cartRepo).save(cart2);
    }

    @Test
    @DisplayName("Usuário não encontrado")
    void removerItem2() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> cartServ.removerItem(1L, 1L));
    }

    @Test
    @DisplayName("Produto não existe no carrinho")
    void removerItem3() {
        user.setCart(cart2);
        cart2.getItems().clear();
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(
                ResourceNotFoundException.class,
                () -> cartServ.removerItem(1L, 1L)
        );
    }

    @Test
    @DisplayName("Remove items com sucesso")
    void limparCarrinho1() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        user.setCart(cart2);

        cartServ.limparCarrinho(1L);
        verify(cartRepo).save(cart2);
    }

    @Test
    @DisplayName("Usuario nao encontrado")
    void limparCarrinho2() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());
        user.setCart(cart2);

        assertThrows(
                ResourceNotFoundException.class,
                () -> cartServ.limparCarrinho(1L)
        );
    }

}