package com.example.spring_mvc_data_crud.service;

import com.example.spring_mvc_data_crud.domain.Item;
import com.example.spring_mvc_data_crud.domain.Product;
import com.example.spring_mvc_data_crud.domain.Sale;
import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.ItemDto;
import com.example.spring_mvc_data_crud.repos.ItemRepo;
import com.example.spring_mvc_data_crud.repos.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private List<Product> productList = new ArrayList<>();;
    private  Product product;

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;


    @BeforeEach
    public void init() {

         product = Product.builder()
                .id(100l)
                .name("asdasd")
                .description("asdasd")
                .build();

        productList.add(product);
    }

    @Test
    @DisplayName("Should Retrieve all products by sale id")
    void shouldFindAllProducts() {

        when(productRepo.findAll()).thenReturn(productList);

        List<Product> productList = productService.findAll();

        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(1);
        verify(productRepo, Mockito.times(1))
                .findAll();

    }

    @DisplayName("Should save Product")
    void ShouldSavedProductCorrect() {

        when(productRepo.save(product)).thenReturn(product);
        boolean result = productService.addProduct(product);

        assertThat(result).isTrue();
        verify(productRepo, Mockito.times(1))
                .save(product);
    }

    @Test
    @DisplayName("Should Retrieve Product by id")
    void ShouldFindProductById() {

        when(productRepo.findById(1000l)).thenReturn(Optional.of(product));

        Product productById = productService.findById(1000l);

        assertThat(productById).isNotNull();
        assertThat(productById.getName()).isEqualTo(product.getName());
        assertThat(productById.getDescription()).isEqualTo( product.getDescription());
        assertThat(productById.getId()).isEqualTo(product.getId());

    }

    @Test
    @DisplayName("Should update Product by id")
    void ShouldBeUpdateProduct() {

        when(productRepo.findById(1000l)).thenReturn(Optional.of(product));
        product.setDescription("changeV");
        Product ProductUpdated = productService.update(product, 1000l);

        assertThat(ProductUpdated).isNotNull();
        assertThat(ProductUpdated.getDescription()).isEqualTo("changeV");
        verify(productRepo, Mockito.times(1))
                .save(product);
    }
    @Test
    @DisplayName("Should delete Product by id")
    void deleteProduct() {

        productService.delete(product.getId());
        Mockito.verify(productRepo, Mockito.times(1))
                .deleteById(product.getId());
    }

}