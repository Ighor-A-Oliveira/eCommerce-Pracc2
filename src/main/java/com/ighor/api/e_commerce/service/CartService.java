package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.CartDTO;
import com.ighor.api.e_commerce.dto.request.CartItemRequestDTO;
import com.ighor.api.e_commerce.dto.request.CartRequestDTO;
import com.ighor.api.e_commerce.dto.response.CartItemResponseDTO;
import com.ighor.api.e_commerce.dto.response.CartResponseDTO;
import com.ighor.api.e_commerce.mapper.CartItemMapper;
import com.ighor.api.e_commerce.model.entity.Cart;
import com.ighor.api.e_commerce.model.entity.CartItem;
import com.ighor.api.e_commerce.model.entity.Product;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.repo.CartRepo;
import com.ighor.api.e_commerce.repo.ProductRepo;
import com.ighor.api.e_commerce.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductRepo prodRepo;
    private final CartItemMapper cartItemMapper;


    public CartService(CartRepo cartRepo, UserRepo userRepo, ProductRepo prodRepo, CartItemMapper cartItemMapper){
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.prodRepo = prodRepo;
        this.cartItemMapper= cartItemMapper;
    }

    @Transactional
    public void criarCarrinho(CartDTO request){
        User user = userRepo.findById(request.userId()).orElseThrow(() -> new RuntimeException("Usuario nao encontrado com esse id"));
        Cart cart = new Cart();
        cart.setCreatedAt(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setUser(user);
        cartRepo.save(cart);
    }

    @Transactional
    public Cart criarCarrinho(User user){
        Cart cart = new Cart();
        cart.setCreatedAt(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setUser(user);
        cartRepo.save(cart);
        return cart;
    }


    //Buscar carrinho do usuário.
    @Transactional
    public CartResponseDTO buscarCarrinho(CartRequestDTO request){
        //Procurar se ja tem carrinho criado
        User user = userRepo.findById(request.userId()).orElseThrow(() -> new RuntimeException("Usuario nao encontrado com esse id"));

        //Se n tiver tem que criar e retornar o carrinho vazio
        if (user.getCart() == null) {
            Cart novoCart = criarCarrinho(user);
            return new CartResponseDTO(novoCart.getId(), 0, BigDecimal.ZERO, new ArrayList<>());
        }

        //Se tiver entao carrinho criado entao tem que procurar os dados do carrinho
        //A entity user ja tem acesso ao cart, entao pego essa referencia para ter acesso aos itens
        Cart cart = user.getCart();

        //Dps eu converto os CartItems para DTO
        List<CartItemResponseDTO> cartItemDTOs = new ArrayList<>();

        //Calcular quantidade total
        cart.setTotalItems(0);
        //Calcular preco total
        BigDecimal priceTotal = new BigDecimal("0");

        for (CartItem item : cart.getItems()){
            //numero total de itens, inclui tudo
            cart.setTotalItems(cart.getTotalItems() + item.getQuantity());
            //preço total
            priceTotal = priceTotal.add(item.getSubtotal());
            //Add na lista
            cartItemDTOs.add(cartItemMapper.cartItemParaDTO(item));
        }

        //depois junto tudo no CartResponseDTO
        CartResponseDTO responseDTO = new CartResponseDTO(
                cart.getId(),
                cart.getTotalItems(),
                priceTotal,
                cartItemDTOs
        );

        return responseDTO;
    }


    //Adicionar item ao carrinho.
    @Transactional
    public CartResponseDTO adicionarItem(Long userId, CartItemRequestDTO request){
        //Checamos se o usuario existe
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        //Checamos se o cart existe
        Cart cart = user.getCart();
        if (cart == null) {
            cart = criarCarrinho(user);
            user.setCart(cart);
        }

        //Tem que checar se o produto existe
        Product prod = prodRepo.findById(request.productId()).orElseThrow(() -> new RuntimeException("Nao existe produto com o id "+request.productId()));

        //Se o produto existe, checar se tem estoque
        if(prod.getStockQuantity() < request.quantity()){
            throw new RuntimeException("Nao ah estoque suficiente de "+prod.getName());
        }

        //Checando se o item ja existe no carrinho
        Optional<CartItem> itemExistente = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(request.productId()))
                .findFirst();


        //Se as duas condicoes sao true entao
        //O item ja esta no carrinho e a gente so altera a quantidade
        if(itemExistente.isPresent()){
            //Pegamos o CartItem
            CartItem item = itemExistente.get();
            //Adicionamos o numero de itens
            item.setQuantity(item.getQuantity() + request.quantity());

        }

        //Se n ta no carrinho temos que add
        else {
            CartItem novoItem = new CartItem();
            //Quantidade de itens dentro do carrinho
            novoItem.setQuantity(request.quantity());
            //Coloca a referencia do carrinho no item
            novoItem.setCart(cart);
            //Preco unitario do item
            novoItem.setUnitPrice(prod.getPrice());
            novoItem.setProduct(prod);
            cart.getItems().add(novoItem);
        }

        //Salvamos no carrinho mesmo pois temos o cascate ativado
        cartRepo.save(cart);
        CartRequestDTO dto = new CartRequestDTO(user.getId());
        return buscarCarrinho(dto);
    }

    //Atualizar quantidade de um item.
    public void atualizarQuantidade(Long userId, Long productId, Long quantity) {

        if (quantity == 0) {
            return;
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException());

        Cart cart = user.getCart();

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho"));

        cartItem.setQuantity(cartItem.getQuantity() + quantity.intValue());

        if (cartItem.getQuantity() <= 0) {
            removerItem(userId, productId);
            return;
        }

        cartRepo.save(cart);
    }


    //Remover item do carrinho
    public void removerItem(Long userId, Long productId){
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException());
        Cart cart = user.getCart();

        //Pegando o item especifico
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho"));
        //Removendo o item especifico da lista
            //cart.getItems = lista de items
        cart.getItems().remove(cartItem);

        cartRepo.save(cart);
    }

    //Limpar carrinho
    public void limparCarrinho(Long userId){
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException());
        Cart cart = user.getCart();

        cart.getItems().clear();
        cartRepo.save(cart);
    }

    //Calcular total
    public BigDecimal calcularTotal(List<CartItem> items){
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items){
            total = total.add(item.getSubtotal());
        }

        return total;
    }
}
