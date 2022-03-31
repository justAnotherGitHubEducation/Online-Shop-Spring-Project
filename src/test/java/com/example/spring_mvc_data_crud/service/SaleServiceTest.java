package com.example.spring_mvc_data_crud.service;

import com.example.spring_mvc_data_crud.domain.Role;
import com.example.spring_mvc_data_crud.domain.Sale;
import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.SaleDto;
import com.example.spring_mvc_data_crud.dto.UserDto;
import com.example.spring_mvc_data_crud.mapper.SaleMapper;
import com.example.spring_mvc_data_crud.mapper.SaleMapperImpl;
import com.example.spring_mvc_data_crud.mapper.UserMapper;
import com.example.spring_mvc_data_crud.repos.SaleRepo;
import com.example.spring_mvc_data_crud.repos.UserRepo;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    private List<SaleDto> saleListDto = new ArrayList<>();;
    private List<Sale> saleList = new ArrayList<>();;
    private SaleDto saleDto;
    private Sale sale;

    @Mock
    private SaleRepo saleRepo;

    @InjectMocks
    private SaleService saleService;

    @BeforeEach
    public void init() {

         sale= Sale.builder()
                .id(1000l)
                .date(LocalDate.of(2017, Month.NOVEMBER, 30))
                 .user(User.builder().username("123").id(100l).build())
                .description("wqe")
                .build();

        saleList.add(sale);
    }

    @Test
    @DisplayName("Should Retrieve all sales")
    void shouldReturnFindAll() {

        when(saleRepo.findAll()).thenReturn(saleList);

        List<Sale> userServiceAll = saleService.findAll();

        assertThat(userServiceAll).isNotNull();
        assertThat(userServiceAll.size()).isEqualTo(1);

    }

    @Test
    @DisplayName("Should save sale")
    void ShouldSavedSaleCorrect() {

        when(saleRepo.save(sale)).thenReturn(sale);
        boolean result = saleService.addSale(sale);

        assertThat(result).isTrue();
        verify(saleRepo, Mockito.times(1))
                .save(sale);
    }

    @Test
    @DisplayName("Should update sale by id")
    void ShouldBeUpdateSale() {

        when(saleRepo.findById(1000l)).thenReturn(Optional.of(sale));
        sale.setDescription("changeV");

        Sale saleUpdate = saleService.update(sale, 1000l);


        assertThat(saleUpdate).isNotNull();
        assertThat(saleUpdate.getDescription()).isEqualTo("changeV");
        verify(saleRepo, Mockito.times(1))
                .save(sale);

    }

    @Test
    @DisplayName("Should Retrieve sale by id")
    void ShouldFindSaleById() {

        when(saleRepo.findById(1000l)).thenReturn(Optional.of(sale));

        Sale saleById = saleService.findSaleById(1000l);

        assertThat(saleById).isNotNull();
        assertThat(saleById.getDate()).isEqualTo(sale.getDate());
        assertThat(saleById.getUser()).isEqualTo( sale.getUser());
        assertThat(saleById.getId()).isEqualTo(sale.getId());

    }

    @Test
    @DisplayName("Should delete sale by id")
    void deleteSale() {

        saleService.deleteSale(sale.getId());
        Mockito.verify(saleRepo, Mockito.times(1))
                .deleteById(sale.getId());
    }


}