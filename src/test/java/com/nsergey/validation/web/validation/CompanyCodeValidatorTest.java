package com.nsergey.validation.web.validation;

import com.nsergey.validation.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(LocalValidatorFactoryBean.class)
class CompanyCodeValidatorTest {

    @Autowired
    private Validator validator;

    @MockBean
    private CompanyService companyService;

    @DisplayName("Код компании валидный и компания активна")
    @Test
    void validCompany() {
        // given
        String companyCode = "123456789";
        when(companyService.isCompanyActive(companyCode)).thenReturn(true);
        // when
        val violations = validator.validate(new TestClass(companyCode));
        // then
        violations.forEach(System.out::println);
        assertEquals(0, violations.size());
    }

    @DisplayName("Код компании валидный, однако компания отключена")
    @Test
    void disabledCompany() {
        // given
        String companyCode = "987654321";
        when(companyService.isCompanyActive(companyCode)).thenReturn(false);
        // when
        val violations = validator.validate(new TestClass(companyCode));
        // then
        violations.forEach(System.out::println);
        assertViolation(violations, "Компания не активна");
        assertEquals(1, violations.size());
    }

    @DisplayName("Код компании невалидный")
    @Test
    void invalidCompany() {
        // given
        String invalidCompanyCode = "abcd";
        // when
        val violations = validator.validate(new TestClass(invalidCompanyCode));
        // then
        violations.forEach(System.out::println);
        assertViolation(violations, "Невалидный код компании");
        assertEquals(1, violations.size());
    }

    @DisplayName("Код компании отсутствует")
    @Test
    void companyCodeIsNull() {
        // when
        val violations = validator.validate(new TestClass(null));
        // then
        violations.forEach(System.out::println);
        assertViolation(violations, "Невалидный код компании");
        assertEquals(1, violations.size());
    }

    public static <T> void assertViolation(Set<ConstraintViolation<T>> violations, String message) {
        boolean found = violations.stream().anyMatch(v -> v.getMessage().equals(message));
        assertTrue(found);
    }

    @Data
    @AllArgsConstructor
    private static class TestClass {
        @CompanyCode(message = "Невалидный код компании", allowNull = false)
        private String companyCode;
    }

}