package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.CartDTO;
import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.dto.request.CartItemRequestDTO;
import com.ighor.api.e_commerce.dto.request.CartRequestDTO;
import com.ighor.api.e_commerce.dto.response.CartResponseDTO;
import com.ighor.api.e_commerce.service.CartService;
import com.ighor.api.e_commerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    private final CartService cartServ;

    public CartController(CartService cartServ){
        this.cartServ = cartServ;

    }

    //create cart
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> buscarCarrinho(@PathVariable Long userId){
        return ResponseEntity.ok(cartServ.buscarCarrinho(new CartRequestDTO(userId)));
    }


    //add item to cart
    @PostMapping("/{userId}/items")
    public ResponseEntity<CartResponseDTO> adicionarItem(@PathVariable Long userId, @RequestBody CartItemRequestDTO request){
        return ResponseEntity.ok(cartServ.adicionarItem(userId, request));
    }


    //update cart
    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartResponseDTO> atualizarQuantidade(@PathVariable Long userId, @PathVariable Long productId, @RequestParam Long quantity){
        cartServ.atualizarQuantidade(userId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    //update cart
    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartResponseDTO> removerItem(@PathVariable Long userId, @PathVariable Long productId){
        cartServ.removerItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> limparCarrinho(@PathVariable Long userId){
        cartServ.limparCarrinho(userId);
        return ResponseEntity.noContent().build();
    }

}
