package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.dto.request.UserLoginRequestDTO;
import com.ighor.api.e_commerce.dto.request.UserRegisterRequestDTO;
import com.ighor.api.e_commerce.dto.response.UserLoginResponseDTO;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.security.TokenConfig;
import com.ighor.api.e_commerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final UserService userServ;

    public UserController(AuthenticationManager authenticationManager, TokenConfig tokenConfig, UserService userServ){
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
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
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> buscarUsuarioPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(userServ.buscarUsuarioPorId(id));
    }

    //list all users
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')") //Comented just for testing
    public ResponseEntity<List<UserDTO>> buscarTodosUsuarios(){
        return ResponseEntity.ok(userServ.buscarTodosUsuarios());
    }

    @PostMapping("/login")
    //Recebe e valida o JSON do login
    public ResponseEntity<UserLoginResponseDTO> fazerLoginUsuario(@Valid @RequestBody UserLoginRequestDTO loginRequest){
        return ResponseEntity.ok(userServ.fazerLoginUsuario(loginRequest));
    }

    //update user
    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarUsuario(@Valid @PathVariable Long userId, @RequestBody User user){
        userServ.atualizarUsuarioPorId(userId,user);
        return ResponseEntity.noContent().build();
    }

    //delete user
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@Valid @PathVariable Long userId){
        userServ.deletarUsuarioPorId(userId);
        return ResponseEntity.noContent().build();
    }
}
