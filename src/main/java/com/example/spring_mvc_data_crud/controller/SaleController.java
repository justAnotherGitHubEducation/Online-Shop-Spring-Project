package com.example.spring_mvc_data_crud.controller;


import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.SaleDto;
import com.example.spring_mvc_data_crud.mapper.SaleMapper;
import com.example.spring_mvc_data_crud.mapper.UserMapper;
import com.example.spring_mvc_data_crud.service.SaleService;
import com.example.spring_mvc_data_crud.service.UserService;
import com.example.spring_mvc_data_crud.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final SaleMapper saleMapper;


    @GetMapping()
    String getSalesList(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User CurrentUser = (User) userService.loadUserByUsername(auth.getName());
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")))
            model.addAttribute("listSales",
                    saleService.findAll().stream().map(sale -> saleMapper.saleToSaleDto(sale)).collect(toList()));
        else if (auth != null)
            model.addAttribute("listSales",
                    saleService.findAll().stream().filter(sale -> sale.getUser().equals(CurrentUser)).map(sale -> saleMapper.saleToSaleDto(sale)).collect(toList()));
        return "sales/sales";
    }

    @GetMapping("/new")
    public String saleCreate(Model model, SaleDto saleDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")))
            model.addAttribute("users", userService.findAll().stream().map(user -> userMapper.userToUserDto(user)).collect(Collectors.toList()));
        else if (auth != null)
            model.addAttribute("users", userMapper.userToUserDto((User) userService.loadUserByUsername(auth.getName())));


        model.addAttribute("sale", saleDto);

        return "sales/saleNew";

    }

    @GetMapping("/{id}/update")
    public String saleUpdate(Model model, @PathVariable Long id) {

        model.addAttribute("sale", saleMapper.saleToSaleDto(saleService.findSaleById(id)));
        return "sales/saleUpdate";

    }

    @PostMapping("/new")
    public String createSale(@ModelAttribute("sale") @Valid SaleDto saleDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = ValidateUtils.getErrors(bindingResult);
            model.addAttribute("errors", errors);
            return "sales/saleNew";

        } else {

            saleService.addSale(saleMapper.saleDtoToSale(saleDto));
            return "redirect:/sales";
        }
    }

    @PostMapping("/{id}/update")
    public String updateSale(@ModelAttribute("sale") @Valid SaleDto saleDto, @PathVariable Long id, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = ValidateUtils.getErrors(bindingResult);
            model.addAttribute("errors", errors);
            return "sales/saleUpdate";

        } else {

            saleService.update(saleMapper.saleDtoToSale(saleDto), id);
            return "redirect:/sales";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteSale(@PathVariable("id") Long Id) {
        saleService.deleteSale(Id);
        return "redirect:/sales";
    }


}
