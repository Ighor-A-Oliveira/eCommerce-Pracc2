package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.request.ProductRequestDTO;
import com.ighor.api.e_commerce.dto.response.ProductResponseDTO;
import com.ighor.api.e_commerce.model.entity.Product;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService prodServ;

    public ProductController(ProductService prodServ){
        this.prodServ = prodServ;

    }

    @Operation(summary = "Cria um registro de produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Já existe registro para esse produto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')") //Comented just for testing
    public ResponseEntity<Void> criarProduto(@Valid @RequestBody ProductRequestDTO request){
        prodServ.criarProduto(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Busca um registro de produto pelo prodId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de produto encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> buscarProdutoPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(prodServ.buscarProdutoPorId(id));
    }

    @Operation(summary = "Busca todos os registros de produto pelo prodId")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registros de produtos foram encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> buscarTodosProdutos(){
        return ResponseEntity.ok(prodServ.buscarTodosProdutos());
    }

    @Operation(summary = "Atualiza o registro de um produto pelo prodId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de produto não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{prodId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarProduto(@Valid @PathVariable Long prodId, @Valid @RequestBody Product prod){
        prodServ.atualizarProdutoPorId(prodId,prod);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta o registro de um produto pelo prodId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de produto deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de produto não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{prodId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProduto(@Valid @PathVariable Long prodId){
        prodServ.deletarProdutoPorId(prodId);
        return ResponseEntity.noContent().build();
    }
}
