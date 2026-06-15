package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.request.ProductRequestDTO;
import com.ighor.api.e_commerce.dto.response.ProductResponseDTO;
import com.ighor.api.e_commerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService prodServ;

    public ProductController(ProductService prodServ){
        this.prodServ = prodServ;

    }

    //create user
    @PostMapping
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody ProductRequestDTO request){
        prodServ.criarProduto(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //list user by id
    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponseDTO> buscarUsuarioPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(prodServ.buscarProdutoPorId(id));
    }

    //list all users
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> buscarTodosUsuarios(){
        return ResponseEntity.ok(prodServ.buscarTodosProdutos());
    }

    //login user

    //update user

    //delete user
}
