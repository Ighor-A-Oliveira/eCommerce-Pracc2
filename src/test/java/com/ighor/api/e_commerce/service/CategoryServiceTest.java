package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.CategoryDTO;
import com.ighor.api.e_commerce.exception.DuplicateResourceException;
import com.ighor.api.e_commerce.mapper.CategoryMapper;
import com.ighor.api.e_commerce.model.entity.Category;
import com.ighor.api.e_commerce.repo.CategoryRepo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepo catRepo;
    @Mock
    private CategoryMapper catMapper;
    @InjectMocks
    CategoryService categoryServ;

    CategoryDTO request;
    Category cat;
    List<CategoryDTO> dtos;

    @BeforeEach
    void setUp() {

        cat = new Category();
        cat.setId(1L);
        cat.setName("B");
        cat.setSlug("B");
        cat.setDescription("B");
        cat.setActive(true);
        cat.setProducts(new ArrayList<>());

        request = new CategoryDTO(
                2L,
                "A",
                "A",
                "A",
                true,
                new ArrayList<>()
        );


        dtos = new ArrayList<>();
        dtos.add(request);
    }

    @Test
    @DisplayName("Cria categoria")
    void criarCategoria() {
        //definindo comportamento
            //precisamos desse repo para checar se ja existe uma categoria com esse nome
        when(catRepo.findByName(request.name().toUpperCase())).thenReturn(Optional.empty());
        //chamando o metodo com a request
        categoryServ.criarCategoria(request);
        //checando se foi salvo uma nova categoria
        verify(catRepo).save(any(Category.class));
    }

    @Test
    @DisplayName("Lanca exception por duplicidade")
    void criarCategoria2() {
        //definindo comportamento
        //precisamos desse repo para checar se ja existe uma categoria com esse nome
        when(catRepo.findByName(request.name().toUpperCase())).thenReturn(Optional.ofNullable(cat));
        //chamando o metodo com a request
        assertThrows(DuplicateResourceException.class, () -> {
            categoryServ.criarCategoria(request);
        });
    }

    @Test
    @DisplayName("Busca categoria")
    void buscarCategoriaPorId() {
        when(catRepo.findById(1L)).thenReturn(Optional.ofNullable(cat));
        when(catMapper.categoryParaDTO(cat)).thenReturn(request);

        CategoryDTO result = categoryServ.buscarCategoriaPorId(1L);
        assertEquals(result, request);
    }

    @Test
    @DisplayName("Busca todas as categorias")
    void buscarTodasCategorias() {
        when(catRepo.findAll()).thenReturn(List.of(cat));
        when(catMapper.categoriesParaDTO(List.of(cat))).thenReturn(List.of(request));

        List<CategoryDTO> result = categoryServ.buscarTodasCategorias();
        assertEquals(result, dtos);
    }

    @Test
    @DisplayName("Atualiza categoria")
    void atualizarCategoriaPorId() {
        cat.setId(2L);
        when(catRepo.findById(2L)).thenReturn(Optional.ofNullable(cat));
        categoryServ.atualizarCategoriaPorId(2L, request);
        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(catRepo).save(captor.capture());
        Category saved = captor.getValue();

        verify(catRepo).save(any(Category.class));
        assertEquals(request.name(), saved.getName());
    }

    @Test
    @DisplayName("Deleta categoria")
    void deletarCategoriaPorId() {
        categoryServ.deletarCategoriaPorId(1L);
        verify(catRepo).deleteById(1L);
    }
}