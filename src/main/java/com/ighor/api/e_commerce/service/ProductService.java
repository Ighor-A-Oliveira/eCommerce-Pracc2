package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.request.ProductRequestDTO;
import com.ighor.api.e_commerce.dto.response.ProductResponseDTO;
import com.ighor.api.e_commerce.mapper.ProductMapper;
import com.ighor.api.e_commerce.model.entity.Category;
import com.ighor.api.e_commerce.model.entity.Product;
import com.ighor.api.e_commerce.repo.CategoryRepo;
import com.ighor.api.e_commerce.repo.ProductRepo;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepo prodRepo;
    private final ProductMapper prodMapper;
    private final CategoryRepo catRepo;


    public ProductService(ProductRepo prodRepo, ProductMapper prodMapper, CategoryRepo catRepo){
        this.prodRepo = prodRepo;
        this.prodMapper= prodMapper;
        this.catRepo= catRepo;
    }

    //create user
    @Transactional
    public void criarProduto(ProductRequestDTO request){
        Category category = catRepo.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + request.categoryId()));

        Product prod = new Product();
        prod.setName(request.name());
        prod.setDescription(request.description());
        prod.setPrice(request.price());
        prod.setStockQuantity(request.stockQuantity());
        prod.setImageUrl(request.imageUrl());
        prod.setSku(request.sku());
        prod.setActive(request.active());
        prod.setCreatedAt(LocalDateTime.now());
        prod.setCategory(category);

        prodRepo.save(prod);

    }


    //list user by id
    public ProductResponseDTO buscarProdutoPorId(Long id){
        //Procurando usuario
        Product prod = prodRepo.findById(id).orElseThrow(() -> new RuntimeException("Não foi possivel encontrar uma categoria com o id "+id));
        //Usando a classe UserMapper para converter a entidade em DTO
        ProductResponseDTO dto = prodMapper.productParaDTO(prod);
        return dto;
    }

    //list all users
    public List<ProductResponseDTO> buscarTodosProdutos(){
        //List<Category> cats = catRepo.findAll();
        List<ProductResponseDTO> dtos = prodMapper.productsParaDTO(prodRepo.findAll());
        return dtos;
    }


    //update category

    //delete category
}
