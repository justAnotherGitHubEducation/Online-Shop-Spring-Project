package com.example.spring_mvc_data_crud.service;


import com.example.spring_mvc_data_crud.domain.Sale;
import com.example.spring_mvc_data_crud.exception.ObjectNotFoundException;
import com.example.spring_mvc_data_crud.mapper.SaleMapper;
import com.example.spring_mvc_data_crud.repos.SaleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class SaleService {

    private final SaleMapper saleMapper;
    private final SaleRepo saleRepo;

    public List<Sale> findAll() {

        return saleRepo.findAll();
    }

    public boolean addSale(Sale sale) {

        saleRepo.save(sale);
        return true;
    }

    public Sale update(Sale sale,Long id ) {

        return saleRepo.findById(id)
                .map(saleid -> {
                    saleid.setId(sale.getId());
                    saleid.setDescription(sale.getDescription());
                    saleid.setUser(sale.getUser());
                    saleid.setDate(sale.getDate());
                   // saleid.setItems(sale.getItems());
                    saleRepo.save(saleid);
                    return saleid;
                     })
                .orElseThrow(() -> new ObjectNotFoundException("User not found( id "+ id+")"));

    }

    public Sale findSaleById(Long id) {

        return saleRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Sale not found( id "+ id+")"));
    }

    public void deleteSale(Long id){
        saleRepo.deleteById(id);
    }

}
