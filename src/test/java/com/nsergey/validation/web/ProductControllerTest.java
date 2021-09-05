package com.nsergey.validation.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.nsergey.validation.testing.TestResourceLoader.loadFileContent;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    private static final String PRODUCTS_URL = "/rest/v1/products";
    private static final String VALIDATION_ERROR = "VALIDATION_ERROR";

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Успешное добавление товаров")
    @Test
    void createProducts_should_be_ok() throws Exception {
        String json = loadFileContent(this.getClass(), "products_ok.json");
        mockMvc.perform(post(PRODUCTS_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @DisplayName("Некорректный код продавца")
    @Test
    void invalid_seller_code() throws Exception {
        String json = loadFileContent(this.getClass(), "products_invalid_seller_code.json");
        mockMvc.perform(post(PRODUCTS_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id", any(String.class)))
                .andExpect(jsonPath("$.errorType", is(VALIDATION_ERROR)))
                .andExpect(jsonPath("$.fieldErrors.seller", is("Некорректный код продавца")));
    }

    @DisplayName("Компания не активна")
    @Test
    void inactive_customer() throws Exception {
        String json = loadFileContent(this.getClass(), "products_inactive_customer.json");
        mockMvc.perform(post(PRODUCTS_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id", any(String.class)))
                .andExpect(jsonPath("$.errorType", is(VALIDATION_ERROR)))
                .andExpect(jsonPath("$.fieldErrors.customer", is("Компания не активна")));
    }

    @DisplayName("Некорректные данные товаров")
    @Test
    void bad_products() throws Exception {
        String json = loadFileContent(this.getClass(), "products_bad.json");
        ResultActions resultActions = mockMvc.perform(post(PRODUCTS_URL).contentType(MediaType.APPLICATION_JSON).content(json));
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id", any(String.class)))
                .andExpect(jsonPath("$.errorType", is(VALIDATION_ERROR)))
                .andExpect(jsonPath("$.fieldErrors['products[0].name']", is("Название товара не может быть пустым")))
                .andExpect(jsonPath("$.fieldErrors['products[1].code']", is("Код товара должен содержать 13 символов")));
    }

}