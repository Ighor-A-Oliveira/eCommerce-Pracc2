package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.dto.request.UserLoginRequestDTO;
import com.ighor.api.e_commerce.dto.request.UserRegisterRequestDTO;
import com.ighor.api.e_commerce.dto.request.UserUpdateRequestDTO;
import com.ighor.api.e_commerce.dto.response.UserLoginResponseDTO;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.security.TokenConfig;
import com.ighor.api.e_commerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cria o registro de um um usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de usuario criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro desse usuario já existe no sistema"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody UserRegisterRequestDTO request){
        userServ.criarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Busca um registro de usuario pelo userId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro de usuario encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de usuario não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> buscarUsuarioPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(userServ.buscarUsuarioPorId(id));
    }

    @Operation(summary = "Busca todos os registros de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros de usuario encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registros de usuario não foram encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')") //Comented just for testing
    public ResponseEntity<List<UserDTO>> buscarTodosUsuarios(){
        return ResponseEntity.ok(userServ.buscarTodosUsuarios());
    }

    @Operation(summary = "Realiza o login do usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login feito com sucesso com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "404", description = "Registro de usuario não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> fazerLoginUsuario(@Valid @RequestBody UserLoginRequestDTO loginRequest){
        return ResponseEntity.ok(userServ.fazerLoginUsuario(loginRequest));
    }

    @Operation(summary = "Atualiza o registro de usuario por userId")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de usuario alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de usuario não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarUsuario(@Valid @PathVariable Long userId, @Valid @RequestBody UserUpdateRequestDTO dto){
        userServ.atualizarUsuarioPorId(userId,dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta o registro de usuario por userId")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de usuario deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de usuario não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@Valid @PathVariable Long userId){
        userServ.deletarUsuarioPorId(userId);
        return ResponseEntity.noContent().build();
    }
}
