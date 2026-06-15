package com.ighor.api.e_commerce.mapper;


import com.ighor.api.e_commerce.dto.entity.AddressDTO;
import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.model.entity.Address;
import com.ighor.api.e_commerce.model.entity.Category;
import com.ighor.api.e_commerce.model.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressMapper {

    public AddressDTO addressParaDTO(Address addr){

        AddressDTO dto = new AddressDTO(
                addr.getId(),
                addr.getStreet(),
                addr.getNumber(),
                addr.getComplement(),
                addr.getNeighborhood(),
                addr.getCity(),
                addr.getState(),
                addr.getZipCode(),
                addr.getIsDefault(),
                addr.getUser() != null ? addr.getUser().getId()  : null
        );

        return dto;
    }

    public List<AddressDTO> addressesParaDTO(List<Address> addrs){
        //checando se n ta vazio
        if (addrs == null) {
            return new ArrayList<>();
        }
        List<AddressDTO> dtos = new ArrayList<>();

        //for each address de addresses
        for(Address addr : addrs) {
            //chama o metodo que converte cada addr para dto e add na lista
            dtos.add(addressParaDTO(addr));
        }


        return dtos;
    }
}
