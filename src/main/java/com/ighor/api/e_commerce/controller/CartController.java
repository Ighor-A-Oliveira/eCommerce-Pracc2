package com.ighor.api.e_commerce.controller;

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

    //create user
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> buscarCarrinho(@Valid @PathVariable Long userId){
        return ResponseEntity.ok(cartServ.buscarCarrinho(new CartRequestDTO(userId)));
    }


    //list user by id
    @PostMapping("/{userId}/items")
    public ResponseEntity<CartResponseDTO> adicionarItem(@PathVariable Long userId, @RequestBody CartItemRequestDTO request){
        return ResponseEntity.ok(cartServ.adicionarItem(userId, request));
    }


    //login user

    //update user

    //delete user
}
