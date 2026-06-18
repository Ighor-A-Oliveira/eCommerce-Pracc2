package com.ighor.api.e_commerce.controller;

//import com.ighor.api.e_commerce.dto.entity.CartDTO;
import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.dto.request.CartItemRequestDTO;
import com.ighor.api.e_commerce.dto.request.CartRequestDTO;
import com.ighor.api.e_commerce.dto.response.CartResponseDTO;
import com.ighor.api.e_commerce.service.CartService;
import com.ighor.api.e_commerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    private final CartService cartServ;

    public CartController(CartService cartServ){
        this.cartServ = cartServ;

    }

    @Operation(summary = "Cria registro do carrinho")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro do carrinho criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Carrinho já está cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<CartResponseDTO> buscarCarrinho(@Valid @PathVariable Long userId){
        return ResponseEntity.ok(cartServ.buscarCarrinho(userId));
    }


    @Operation(summary = "Adiciona item ao carrinho")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item adicionado ao carrinho com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou não ah estoque o suficiente"),
            @ApiResponse(responseCode = "409", description = "Item já está no carrinho"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/{userId}/items")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<CartResponseDTO> adicionarItem(@Valid @PathVariable Long userId, @Valid @RequestBody CartItemRequestDTO request){
        return ResponseEntity.ok(cartServ.adicionarItem(userId, request));
    }


    @Operation(summary = "Atualiza a quantidade de um item que já esta no carrinho")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Quantidade atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou não ah estoque o suficiente"),
            @ApiResponse(responseCode = "401", description = "Não esta autorizado a fazer a alteração"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{userId}/items/{productId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<CartResponseDTO> atualizarQuantidade(@Valid @PathVariable Long userId, @Valid @PathVariable Long productId, @Valid  @RequestParam Long quantity){
        cartServ.atualizarQuantidade(userId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove um item do carrinho")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não esta autorizado a fazer a alteração"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{userId}/items/{productId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<CartResponseDTO> removerItem(@Valid @PathVariable Long userId, @Valid @PathVariable Long productId){
        cartServ.removerItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove todos os itens do carrinho")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Itens removidos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não esta autorizado a fazer a alteração"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<CartResponseDTO> limparCarrinho(@Valid @PathVariable Long userId){
        cartServ.limparCarrinho(userId);
        return ResponseEntity.noContent().build();
    }

}
