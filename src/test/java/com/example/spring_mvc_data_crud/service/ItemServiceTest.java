package com.example.spring_mvc_data_crud.service;

import com.example.spring_mvc_data_crud.domain.Item;
import com.example.spring_mvc_data_crud.domain.Product;
import com.example.spring_mvc_data_crud.domain.Sale;
import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.ItemDto;
import com.example.spring_mvc_data_crud.dto.SaleDto;
import com.example.spring_mvc_data_crud.repos.ItemRepo;
import com.example.spring_mvc_data_crud.repos.SaleRepo;
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
class ItemServiceTest {

    private List<Item> itemList = new ArrayList<>();;
    private Item item;

    @Mock
    private ItemRepo itemRepo;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void init() {

       Sale sale= Sale.builder()
                .id(1000l)
                .date(LocalDate.of(2017, Month.NOVEMBER, 30))
                .user(User.builder().username("123").id(100l).build())
                .description("wqe")
                .build();
       Product product = Product.builder()
               .id(100l)
               .name("asdasd")
               .description("asdasd")
               .build();

        item= Item.builder()
                .id(1000l)
                .sale(sale)
                .comment("sdasda")
                .quantity(345)
                .product(product)
                .build();

        itemList.add(item);
    }

    @Test
    @DisplayName("Should Retrieve all Items by sale id")
    void shouldFindItemsAllBySaleId() {

        when(itemRepo.findAllBySale_Id(1l)).thenReturn(itemList);

        List<Item> itemServiceAll = itemService.findAllBySaleId(1l);

        assertThat(itemServiceAll).isNotNull();
        assertThat(itemServiceAll.size()).isEqualTo(1);
        verify(itemRepo, Mockito.times(1))
                .findAllBySale_Id(1l);

    }

    @DisplayName("Should save Item")
    void ShouldSavedItemCorrect() {

        when(itemRepo.save(item)).thenReturn(item);
        boolean result = itemService.addItem(item);

        assertThat(result).isTrue();
        verify(itemRepo, Mockito.times(1))
                .save(item);
    }


    @Test
    @DisplayName("Should Retrieve Item by id")
    void ShouldFindItemById() {

        when(itemRepo.findById(1000l)).thenReturn(Optional.of(item));

        Item itemById = itemService.findById(1000l);

        assertThat(itemById).isNotNull();
        assertThat(itemById.getComment()).isEqualTo(item.getComment());
        assertThat(itemById.getSale()).isEqualTo( item.getSale());
        assertThat(itemById.getId()).isEqualTo(item.getId());

    }

    @Test
    @DisplayName("Should update item by id")
    void ShouldBeUpdateItem() {

        when(itemRepo.findById(1000l)).thenReturn(Optional.of(item));
        item.setComment("changeV");

        Item itemUpdated = itemService.update(item, 1000l);


        assertThat(itemUpdated).isNotNull();
        assertThat(itemUpdated.getComment()).isEqualTo("changeV");
        verify(itemRepo, Mockito.times(1))
                .save(item);
    }

    @Test
    @DisplayName("Should delete item by id")
    void deleteItem() {

        itemService.deleteItem(item.getId());
        Mockito.verify(itemRepo, Mockito.times(1))
                .deleteById(item.getId());
    }

}