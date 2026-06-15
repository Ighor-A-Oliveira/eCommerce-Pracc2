package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.dto.request.UserRegisterRequestDTO;
import com.ighor.api.e_commerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userServ;

    public UserController(UserService userServ){
        this.userServ = userServ;

    }

    //create user
    @PostMapping
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody UserRegisterRequestDTO request){
        userServ.criarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //list user by id
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> buscarUsuarioPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(userServ.buscarUsuarioPorId(id));
    }

    //list all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> buscarTodosUsuarios(){
        return ResponseEntity.ok(userServ.buscarTodosUsuarios());
    }

    //login user

    //update user

    //delete user
}
