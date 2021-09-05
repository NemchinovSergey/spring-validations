package com.nsergey.validation.web.dto;

import com.nsergey.validation.web.validation.CompanyCode;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class NewProductRequest {

    @CompanyCode(message = "Некорректный код продавца", allowNull = false)
    private String seller;

    @CompanyCode(message = "Некорректный код покупателя", allowNull = false)
    private String customer;

    @Valid
    private List<ProductDto> products;
}
