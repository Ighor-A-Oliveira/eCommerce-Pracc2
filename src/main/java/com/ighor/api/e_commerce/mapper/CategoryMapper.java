package com.ighor.api.e_commerce.mapper;

import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.model.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDTO categoryParaDTO(Category cat){
        CategoryDTO dto = new CategoryDTO(
                cat.getId(),
                cat.getName(),
                cat.getSlug(),
                cat.getDescription(),
                cat.getActive(),
                cat.getProducts() != null ? cat.getProducts().stream().map(Product::getId).toList()  : new ArrayList<>()
        );

        return dto;
    }

    public List<CategoryDTO> categoriesParaDTO(List<Category> cats){
        if (cats == null) {
            return new ArrayList<>();
        }
        List<CategoryDTO> dtos = new ArrayList<>();

        //for each user de users
        for (Category cat : cats) {


            //Extraindo ID do Produto
            List<Long> prodIds = new ArrayList<>();
            //Se nao estiver nulo
            if (cat.getProducts() != null) {
                //for each product de category.getProducts()
                for (Product prod : cat.getProducts()) {
                    //Add o ID do produto a lista
                    prodIds.add(prod.getId());
                }
            }

            //Criando DTO do user
            CategoryDTO dto = new CategoryDTO(
                    cat.getId(),
                    cat.getName(),
                    cat.getSlug(),
                    cat.getDescription(),
                    cat.getActive(),
                    //lista dos ids inclusa no DTO
                    prodIds
            );

            //Add DTO a lista
            dtos.add(dto);
        }


        return dtos;
    }
}
