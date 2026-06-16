package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    private final CategoryService catServ;

    public CategoryController(CategoryService catServ){
        this.catServ = catServ;

    }

    //create category
    @PostMapping
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody CategoryDTO request){
        catServ.criarCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //search category by id
    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryDTO> buscarUsuarioPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(catServ.buscarCategoriaPorId(id));
    }

    //list all categories
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> buscarTodosUsuarios(){
        return ResponseEntity.ok(catServ.buscarTodasCategorias());
    }


    //update category
    @PutMapping("/{catId}")
    public ResponseEntity<Void> atualizarCategoriaPorId(@Valid @PathVariable Long catId, @Valid @RequestBody CategoryDTO dto){
        catServ.atualizarCategoriaPorId(catId, dto);
        return ResponseEntity.noContent().build();
    }

    //delete category
    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> deletarCategoriaPorId(@Valid @PathVariable Long catId){
        catServ.deletarCategoriaPorId(catId);
        return ResponseEntity.noContent().build();
    }
}
