package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.request.ProductRequestDTO;
import com.ighor.api.e_commerce.dto.response.ProductResponseDTO;
import com.ighor.api.e_commerce.model.entity.Product;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService prodServ;

    public ProductController(ProductService prodServ){
        this.prodServ = prodServ;

    }

    //create product
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')") //Comented just for testing
    public ResponseEntity<Void> criarProduto(@Valid @RequestBody ProductRequestDTO request){
        prodServ.criarProduto(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //list product by id
    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponseDTO> buscarProdutoPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(prodServ.buscarProdutoPorId(id));
    }

    //list all products
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> buscarTodosProdutos(){
        return ResponseEntity.ok(prodServ.buscarTodosProdutos());
    }

    //login user

    //update product
    @PutMapping("/{prodId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarProduto(@Valid @PathVariable Long prodId, @RequestBody Product prod){
        prodServ.atualizarProdutoPorId(prodId,prod);
        return ResponseEntity.noContent().build();
    }

    //delete product
    @DeleteMapping("/{prodId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProduto(@Valid @PathVariable Long prodId){
        prodServ.deletarProdutoPorId(prodId);
        return ResponseEntity.noContent().build();
    }
}
