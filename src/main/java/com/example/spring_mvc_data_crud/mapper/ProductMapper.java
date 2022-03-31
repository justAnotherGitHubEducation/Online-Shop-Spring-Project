package com.example.spring_mvc_data_crud.mapper;

import com.example.spring_mvc_data_crud.domain.Item;
import com.example.spring_mvc_data_crud.domain.Product;
import com.example.spring_mvc_data_crud.dto.ItemDto;
import com.example.spring_mvc_data_crud.dto.ProductDto;
import com.example.spring_mvc_data_crud.service.ProductService;
import com.example.spring_mvc_data_crud.service.SaleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {ProductService.class})
public interface ProductMapper {

    Product ProductDtoToProduct(ProductDto productDto);
    @Mapping(source = "product.id",target = "id")
    ProductDto ProductToProductDto(Product product);

}
