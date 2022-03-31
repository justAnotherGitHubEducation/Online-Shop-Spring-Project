package com.example.spring_mvc_data_crud.controller;

import com.example.spring_mvc_data_crud.domain.Product;
import com.example.spring_mvc_data_crud.dto.ProductDto;
import com.example.spring_mvc_data_crud.dto.SaleDto;
import com.example.spring_mvc_data_crud.mapper.ProductMapper;
import com.example.spring_mvc_data_crud.mapper.SaleMapper;
import com.example.spring_mvc_data_crud.mapper.UserMapper;
import com.example.spring_mvc_data_crud.service.ProductService;
import com.example.spring_mvc_data_crud.service.SaleService;
import com.example.spring_mvc_data_crud.service.UserService;
import com.example.spring_mvc_data_crud.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SaleMapper saleMapper;


    @GetMapping()
    String getSalesList(Model model){

        model.addAttribute("products",
                productService.findAll().stream().map(product -> productMapper.ProductToProductDto(product)).collect(toList()));
        return "products/products";
    }

    @GetMapping("/new")
    public String ProductCreate(Model model, ProductDto productDto){

        model.addAttribute("product", productDto);
        return "products/productNew";
    }

    @GetMapping("/{id}/update")
    public String ProductUpdate(Model model,@PathVariable Long id){

        model.addAttribute("product",productMapper.ProductToProductDto(productService.findById(id))) ;
        return "products/productUpdate";

    }

    @PostMapping("/new")
    public String createProduct(@ModelAttribute("product")  @Valid ProductDto productDto, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = ValidateUtils.getErrors(bindingResult);
            model.addAttribute("errors",errors);
            return "products/productNew";

        }
        else {

            productService.addProduct(productMapper.ProductDtoToProduct(productDto));
            return "redirect:/products";
        }
    }

    @PostMapping("/{id}/update")
    public String updateProduct(@ModelAttribute("product") @Valid ProductDto productDto, @PathVariable Long id, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = ValidateUtils.getErrors(bindingResult);
            model.addAttribute("errors",errors);
            return "products/productUpdate";

        }
        else {

            productService.update(productMapper.ProductDtoToProduct(productDto),id);
            return "redirect:/products";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long Id) {
        productService.delete(Id);
        return "redirect:/products";
    }


}
