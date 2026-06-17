package com.ighor.api.e_commerce.controller;

import com.ighor.api.e_commerce.dto.entity.AddressDTO;
import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.service.AddressService;
import com.ighor.api.e_commerce.service.CategoryService;
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

    //create address
    @PostMapping
    @PreAuthorize("#request.userId == authentication.principal.id")
    public ResponseEntity<Void> criarEndereco(@Valid @RequestBody AddressDTO request){
        addrServ.criarEndereco(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //search address by id
    @GetMapping("/id/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<AddressDTO> buscarEnderecoPorId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(addrServ.buscarEnderecoPorId(id));
    }

    //list all address
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')") //Comented just for testing
    public ResponseEntity<List<AddressDTO>> buscarTodosEnderecos(){
        return ResponseEntity.ok(addrServ.buscarTodosEnderecos());
    }

    //update address
    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Void> atualizarEnderecoPorId(@Valid @PathVariable Long userId, @Valid @RequestBody AddressDTO dto){
        addrServ.atualizarEnderecoPorId(userId, dto);
        return ResponseEntity.noContent().build();
    }

    //delete address
    @DeleteMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Void> deletarEnderecoPorId(@Valid @PathVariable Long userId){
        addrServ.deletarEnderecoPorId(userId);
        return ResponseEntity.noContent().build();
    }
}
