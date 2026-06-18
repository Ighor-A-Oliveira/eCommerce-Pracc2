package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
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
@RequestMapping("category")
public class CategoryController {

    private final CategoryService catServ;

    public CategoryController(CategoryService catServ){
        this.catServ = catServ;

    }

    @Operation(summary = "Cria registro de categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de categoria criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Categoria já está cadastrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')") //Comented just for testing
    public ResponseEntity<Void> criarCategoria(@Valid @RequestBody CategoryDTO request){
        catServ.criarCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Busca um registro de categoria por catId")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de categoria encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de categoria não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDTO> buscarCategoriaPorId(@Valid @PathVariable Long catId){
        return ResponseEntity.ok(catServ.buscarCategoriaPorId(catId));
    }

    @Operation(summary = "Busca todos os registros de categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registros de categoria encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> buscarTodasCategoria(){
        return ResponseEntity.ok(catServ.buscarTodasCategorias());
    }


    @Operation(summary = "Atualiza o registro de categoria por catId")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de categoria alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de categoria não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{catId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarCategoriaPorId(@Valid @PathVariable Long catId, @Valid @RequestBody CategoryDTO dto){
        catServ.atualizarCategoriaPorId(catId, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta um registro de categoria por catId")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de categoria deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de categoria não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{catId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarCategoriaPorId(@Valid @PathVariable Long catId){
        catServ.deletarCategoriaPorId(catId);
        return ResponseEntity.noContent().build();
    }
}
