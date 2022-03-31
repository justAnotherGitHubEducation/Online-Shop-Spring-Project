package com.example.spring_mvc_data_crud.utils;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@UtilityClass
public class ValidateUtils {

        static public Map<String, String> getErrors(BindingResult bindingResult) {
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            return bindingResult.getFieldErrors().stream().collect(collector);
        }
    }

