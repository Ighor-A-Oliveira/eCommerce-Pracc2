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

    //create user
    @PostMapping
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody CategoryDTO request){
        catServ.criarCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //list user by id
    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryDTO> buscarUsuarioPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(catServ.buscarCategoriaPorId(id));
    }

    //list all users
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> buscarTodosUsuarios(){
        return ResponseEntity.ok(catServ.buscarTodasCategorias());
    }

    //login user

    //update user

    //delete user
}
