package com.nsergey.validation.web.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ProductDto {
    @NotEmpty(message = "Название товара не может быть пустым")
    private String name;

    @NotEmpty(message = "Код товара не может быть пустым")
    @Length(min = 13, max = 13, message = "Код товара должен содержать {max} символов")
    private String code;
}
