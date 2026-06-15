package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.mapper.CategoryMapper;
import com.ighor.api.e_commerce.model.entity.Category;
import com.ighor.api.e_commerce.repo.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepo catRepo;
    private final CategoryMapper catMapper;


    public CategoryService(CategoryRepo catRepo, CategoryMapper catMapper){
        this.catRepo = catRepo;
        this.catMapper= catMapper;
    }

    //create user
    public void criarCategoria(CategoryDTO request){
        //Checando se tem categoria com o nome informado
        if (catRepo.findByName(request.name().toUpperCase()).isPresent()){
            //Se possui o flow de registro para
            throw new RuntimeException("Ja existe uma Categoria cadastrada com o nome "+ request.name());
        }

        //Se nao tiver categoria ja criado entao fazemos o registro
        Category cat = new Category();
        cat.setName(request.name().toUpperCase());
        cat.setSlug(request.slug());
        cat.setDescription(request.description());
        cat.setActive(true);
        cat.setProducts(null);

        catRepo.save(cat);

    }


    //list user by id
    public CategoryDTO buscarCategoriaPorId(Long id){
        //Procurando usuario
        Category cat = catRepo.findById(id).orElseThrow(() -> new RuntimeException("Não foi possivel encontrar uma categoria com o id "+id));
        //Usando a classe UserMapper para converter a entidade em DTO
        CategoryDTO dto = catMapper.categoryParaDTO(cat);
        return dto;
    }

    //list all users
    public List<CategoryDTO> buscarTodasCategorias(){
        //List<Category> cats = catRepo.findAll();
        List<CategoryDTO> dtos = catMapper.categoriesParaDTO(catRepo.findAll());
        return dtos;
    }


    //update category

    //delete category
}

