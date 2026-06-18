package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.AddressDTO;
import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.service.AddressService;
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
@RequestMapping("address")
public class AddressController {
    private final AddressService addrServ;

    public AddressController(AddressService addrServ){
        this.addrServ = addrServ;

    }

    @Operation(summary = "Criar registro de endereço")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro de endereço criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    @PreAuthorize("#request.userId == authentication.principal.id")
    public ResponseEntity<Void> criarEndereco(@Valid @RequestBody AddressDTO request){
        addrServ.criarEndereco(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Busca o endereço usando como parametro o addressId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<AddressDTO> buscarEnderecoPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(addrServ.buscarEnderecoPorId(id));
    }

    @Operation(summary = "Busca todos os endereços e retorna uma lista")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereços encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereços não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')") //Comented just for testing
    public ResponseEntity<List<AddressDTO>> buscarTodosEnderecos(){
        return ResponseEntity.ok(addrServ.buscarTodosEnderecos());
    }

    @Operation(summary = "Atualiza o registro do endereço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alteração feita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não esta autorizado a fazer a alteração"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Void> atualizarEnderecoPorId(@Valid @PathVariable Long userId, @Valid @RequestBody AddressDTO dto){
        addrServ.atualizarEnderecoPorId(userId, dto);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Deleta o registro do endereço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete feito com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não esta autorizado a fazer o delete"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    //delete address
    @DeleteMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Void> deletarEnderecoPorId(@Valid @PathVariable Long userId){
        addrServ.deletarEnderecoPorId(userId);
        return ResponseEntity.noContent().build();
    }
}
