package com.example.spring_mvc_data_crud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("login");
        registry.addViewController("/users/userUpdate").setViewName("userUpdate");
        registry.addViewController("/users/users").setViewName("users");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/sales/sales").setViewName("sales");
        registry.addViewController("/sales/saleNew").setViewName("saleNew");
        registry.addViewController("/sales/saleUpdate").setViewName("saleUpdate");
        registry.addViewController("/items/itemUpdate").setViewName("itemUpdate");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }

}