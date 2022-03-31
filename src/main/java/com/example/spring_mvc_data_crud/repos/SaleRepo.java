package com.example.spring_mvc_data_crud.repos;

import com.example.spring_mvc_data_crud.domain.Sale;
import com.example.spring_mvc_data_crud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleRepo extends JpaRepository<Sale, Long> {

    Optional <Sale> findById(Long id);
}
