package com.nsergey.validation.service;

import com.nsergey.validation.web.dto.NewProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    public void process(NewProductRequest request) {
        log.info("Some request processing: {}", request);
    }

}
