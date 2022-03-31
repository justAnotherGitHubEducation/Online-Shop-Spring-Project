package com.example.spring_mvc_data_crud.controller;

import com.example.spring_mvc_data_crud.domain.Item;
import com.example.spring_mvc_data_crud.domain.Role;
import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.ItemDto;
import com.example.spring_mvc_data_crud.dto.UserDto;
import com.example.spring_mvc_data_crud.mapper.ItemMapper;
import com.example.spring_mvc_data_crud.mapper.ProductMapper;
import com.example.spring_mvc_data_crud.mapper.UserMapper;
import com.example.spring_mvc_data_crud.service.ItemService;
import com.example.spring_mvc_data_crud.service.ItemService;
import com.example.spring_mvc_data_crud.service.ProductService;
import com.example.spring_mvc_data_crud.service.UserService;
import com.example.spring_mvc_data_crud.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private  final ItemMapper itemMapper;

    @GetMapping("/{id}/sale")
    String getListItemsBySaleId(Model model, @PathVariable("id") Long id){
        model.addAttribute("items",
                itemService.findAllBySaleId(id)
                        .stream().
                        map(item -> itemMapper.ItemToItemDto(item))
                        .collect(Collectors.toList()));
        model.addAttribute("sale_id",id);
        return "items/items";
    }

    @GetMapping("/{id}/new")
    public String ItemCreate(Model model,@PathVariable("id") Long id){
        model.addAttribute("products", productService.findAll().stream().map(product -> productMapper.ProductToProductDto(product)).collect(Collectors.toList()));
        model.addAttribute("item", ItemDto.builder().sale_id(id).build());
        return "items/itemNew";
    }

    @PostMapping("/new")
    public String createItem(@ModelAttribute("item") @Valid ItemDto itemDto,BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidateUtils.getErrors(bindingResult);
            model.addAttribute("errors",errors);
            return "items/itemNew";
        }
        else {
            itemService.addItem(itemMapper.ItemDtoToItem(itemDto));
            return "redirect:/items/"+itemDto.getSale_id()+ "/sale";
        }
    }

    @GetMapping("/{id}/update")
    public String getUserById(Model model,@PathVariable Long id){
        model.addAttribute("item",itemMapper.ItemToItemDto(itemService.findById(id)));
        model.addAttribute("products", productService.findAll().stream().map(product -> productMapper.ProductToProductDto(product)).collect(Collectors.toList()));
        return "items/itemUpdate";
    }

    @PostMapping("/{id}/update")
    public String updateItem(@ModelAttribute("item") @Valid ItemDto itemDto,@PathVariable Long id, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidateUtils.getErrors(bindingResult);
            model.addAttribute("errors",errors);
            return "users/userUpdate";
        }
        else {
            itemService.update(itemMapper.ItemDtoToItem(itemDto),id);
            return "redirect:/items/"+itemDto.getSale_id()+"/sale";
        }
    }

    @GetMapping("/{id}/{saleid}/delete")
    public String deleteItem(@PathVariable("id") Long Id,@PathVariable("saleid") Long saleid) {
        itemService.deleteItem(Id);
        return "redirect:/items/"+saleid+"/sale";
    }
}
