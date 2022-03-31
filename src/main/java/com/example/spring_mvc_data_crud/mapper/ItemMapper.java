package com.example.spring_mvc_data_crud.mapper;

import com.example.spring_mvc_data_crud.domain.Item;
import com.example.spring_mvc_data_crud.domain.Sale;
import com.example.spring_mvc_data_crud.dto.ItemDto;
import com.example.spring_mvc_data_crud.dto.SaleDto;
import com.example.spring_mvc_data_crud.service.ProductService;
import com.example.spring_mvc_data_crud.service.SaleService;
import com.example.spring_mvc_data_crud.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SaleService.class, ProductService.class})
public interface ItemMapper {
    @Mapping(source = "itemDto.product_id",target = " product.id")
    @Mapping(source = "itemDto.sale_id",target = "sale.id")
    Item ItemDtoToItem(ItemDto itemDto);

    @Mapping(source = "item.product.id",target = "product_id")
    @Mapping(source = "item.product.name",target = "productName")
    @Mapping(source = "item.sale.id",target = "sale_id")
    ItemDto ItemToItemDto(Item item);

}

