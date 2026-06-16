package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.AddressDTO;
import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.mapper.AddressMapper;
import com.ighor.api.e_commerce.mapper.CategoryMapper;
import com.ighor.api.e_commerce.model.entity.Address;
import com.ighor.api.e_commerce.model.entity.Category;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.repo.AddressRepo;
import com.ighor.api.e_commerce.repo.CategoryRepo;
import com.ighor.api.e_commerce.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepo addrRepo;
    private final UserRepo userRepo;
    private final AddressMapper addrMapper;


    public AddressService(AddressRepo addrRepo, AddressMapper addrMapper, UserRepo userRepo){
        this.addrRepo = addrRepo;
        this.addrMapper= addrMapper;
        this.userRepo= userRepo;
    }

    //create address
    @Transactional
    public void criarEndereco(AddressDTO request){
        User user = userRepo.findById(request.userId()).orElseThrow(() -> new RuntimeException("Nenhum usuario encontrado com esse id"));
        boolean defauldtAddr = false;

        //Se o user nao tiver address registrado o primeiro que ele inserir sera o padrao
        if(addrRepo.findByUserId(request.userId()).isEmpty()){
            defauldtAddr = true;
        }

        Address addr = new Address();
        addr.setStreet(request.street());
        addr.setNumber(request.number());
        addr.setComplement(request.complement());
        addr.setNeighborhood(request.neighborhood());
        addr.setCity(request.city());
        addr.setState(request.state());
        addr.setZipCode(request.zipCode());
        addr.setIsDefault(defauldtAddr);
        addr.setUser(user);


        addrRepo.save(addr);

    }


    //list address by id
    public AddressDTO buscarEnderecoPorId(Long id){
        //Procurando address
        Address addr = addrRepo.findById(id).orElseThrow(() -> new RuntimeException("Não foi possivel encontrar um endereco com o id "+id));
        //Usando a classe UserMapper para converter a entidade em DTO
        AddressDTO dto = addrMapper.addressParaDTO(addr);
        return dto;
    }

    //list all addresses
    public List<AddressDTO> buscarTodosEnderecos(){
        //List<Category> cats = catRepo.findAll();
        List<AddressDTO> dtos = addrMapper.addressesParaDTO(addrRepo.findAll());
        return dtos;
    }


    //update address
    @Transactional
    public void atualizarEnderecoPorId(Long id, AddressDTO dto){
        Address addr = addrRepo.findById(id).orElseThrow(() -> new RuntimeException());

        if (dto.street() != null) {
            addr.setStreet(dto.street());
        }

        if (dto.number() != null) {
            addr.setNumber(dto.number());
        }

        if (dto.complement() != null) {
            addr.setComplement(dto.complement());
        }

        if (dto.neighborhood() != null) {
            addr.setNeighborhood(dto.neighborhood());
        }

        if (dto.city() != null) {
            addr.setCity(dto.city());
        }

        if (dto.state() != null) {
            addr.setState(dto.state());
        }

        if (dto.zipCode() != null) {
            addr.setZipCode(dto.zipCode());
        }

        if (dto.isDefault() != null) {
            addr.setIsDefault(dto.isDefault());
        }

        addrRepo.save(addr);
    }

    //delete address
    @Transactional
    public void deletarEnderecoPorId(Long id){
        addrRepo.deleteById(id);
    }
}
