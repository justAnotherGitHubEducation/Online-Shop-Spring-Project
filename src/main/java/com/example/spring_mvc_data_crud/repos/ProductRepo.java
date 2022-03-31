package com.example.spring_mvc_data_crud.repos;

import com.example.spring_mvc_data_crud.domain.Item;
import com.example.spring_mvc_data_crud.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo  extends JpaRepository<Product, Long> {

    List<Product> findAllById(long id);
    Optional<Product> findByName(String name);
}
