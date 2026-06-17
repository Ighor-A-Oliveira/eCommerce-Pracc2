package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.request.ProductRequestDTO;
import com.ighor.api.e_commerce.dto.response.ProductResponseDTO;
import com.ighor.api.e_commerce.mapper.ProductMapper;
import com.ighor.api.e_commerce.model.entity.Category;
import com.ighor.api.e_commerce.model.entity.Product;
import com.ighor.api.e_commerce.repo.CategoryRepo;
import com.ighor.api.e_commerce.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    //create product
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


    //list produto by id
    public ProductResponseDTO buscarProdutoPorId(Long id){
        //Procurando usuario
        Product prod = prodRepo.findById(id).orElseThrow(() -> new RuntimeException("Não foi possivel encontrar uma categoria com o id "+id));
        //Usando a classe UserMapper para converter a entidade em DTO
        return prodMapper.productParaDTO(prod);
    }

    //list all products
    public List<ProductResponseDTO> buscarTodosProdutos(){
        //List<Category> cats = catRepo.findAll();
        return prodMapper.productsParaDTO(prodRepo.findAll());
    }


    //update product
    @Transactional
    public void atualizarProdutoPorId(Long prodId, Product update){
        if (update == null){
            return;
        }

        //Encontrando Product
        Product prod = prodRepo.findById(prodId).orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + prodId));
        //Checando se ah dados a serem atualizados, se sim entáo fazemos as alteracoes
        prod.setName(update.getName() != null ? update.getName() : prod.getName());
        prod.setDescription(update.getDescription() != null ? update.getDescription() : prod.getDescription());
        if( update.getPrice() != null) prod.setPrice(update.getPrice().doubleValue() >= 0 ? update.getPrice() : prod.getPrice());
        if( update.getStockQuantity() != null) prod.setStockQuantity(update.getStockQuantity() >= 0 ? update.getStockQuantity() : prod.getStockQuantity());
        prod.setImageUrl(update.getImageUrl() != null ? update.getImageUrl() : prod.getImageUrl());
        prod.setSku(update.getSku() != null ? update.getSku() : prod.getSku());
        prod.setActive(update.getActive() != null ? update.getActive() : prod.getActive());

        prodRepo.save(prod);
    }

    //delete product
    @Transactional
    public void deletarProdutoPorId(Long prodId){
        prodRepo.deleteById(prodId);
    }
}
