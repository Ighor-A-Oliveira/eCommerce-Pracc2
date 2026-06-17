package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.service.CategoryService;
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

    //create category
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')") //Comented just for testing
    public ResponseEntity<Void> criarCategoria(@Valid @RequestBody CategoryDTO request){
        catServ.criarCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //search category by id
    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryDTO> buscarCategoriaPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(catServ.buscarCategoriaPorId(id));
    }

    //list all categories
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> buscarTodasCategoria(){
        return ResponseEntity.ok(catServ.buscarTodasCategorias());
    }


    //update category
    @PutMapping("/{catId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarCategoriaPorId(@Valid @PathVariable Long catId, @Valid @RequestBody CategoryDTO dto){
        catServ.atualizarCategoriaPorId(catId, dto);
        return ResponseEntity.noContent().build();
    }

    //delete category
    @DeleteMapping("/{catId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarCategoriaPorId(@Valid @PathVariable Long catId){
        catServ.deletarCategoriaPorId(catId);
        return ResponseEntity.noContent().build();
    }
}
