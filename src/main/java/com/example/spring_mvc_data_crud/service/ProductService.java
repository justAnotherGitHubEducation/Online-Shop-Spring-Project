package com.example.spring_mvc_data_crud.service;

import com.example.spring_mvc_data_crud.domain.*;
import com.example.spring_mvc_data_crud.exception.ObjectAlreadyExistsException;
import com.example.spring_mvc_data_crud.exception.ObjectNotFoundException;
import com.example.spring_mvc_data_crud.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService   {

    private final ProductRepo productRepo;

    public List<Product> findAllBySaleId(Long id) {

        return productRepo.findAllById(id);
    }
    public List<Product> findAll(){

        return productRepo.findAll();
    }

    public boolean addProduct(Product product) {
        Optional<Product> productFromDB = productRepo.findByName(product.getName());
        if (productFromDB.isPresent()){

            throw new ObjectAlreadyExistsException("Product with name " +product.getName()+ " already Exists" );
        }

        productRepo.save(product);
        return true;
    }

    public Product findById(Long id){

        return productRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found( id "+ id+")"));
    }

    public Product update( Product product, Long id ) {

        return productRepo.findById(id)
                .map(productId -> {
                    productId.setId(product.getId());
                    productId.setDescription(product.getDescription());
                    productId.setName(product.getName());

                    productRepo.save(productId);
                    return productId;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Product not found( id "+ id+")"));

    }

    public void delete(Long id){
        productRepo.deleteById(id);
    }
}