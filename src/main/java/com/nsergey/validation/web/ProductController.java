package com.nsergey.validation.web;

import com.nsergey.validation.service.ProductService;
import com.nsergey.validation.web.dto.NewProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/rest/v1")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/products")
    public void createProducts(@Valid @RequestBody NewProductRequest request) {
        log.info("Create products: {}", request);
        productService.process(request);
    }
}
