package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.AddressDTO;
import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.service.AddressService;
import com.ighor.api.e_commerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {
    private final AddressService addrServ;

    public AddressController(AddressService addrServ){
        this.addrServ = addrServ;

    }

    //create user
    @PostMapping
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody AddressDTO request){
        addrServ.criarEndereco(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //list user by id
    @GetMapping("/id/{id}")
    public ResponseEntity<AddressDTO> buscarUsuarioPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(addrServ.buscarEnderecoPorId(id));
    }

    //list all users
    @GetMapping
    public ResponseEntity<List<AddressDTO>> buscarTodosUsuarios(){
        return ResponseEntity.ok(addrServ.buscarTodosEnderecos());
    }

    //login user

    //update user

    //delete user
}
