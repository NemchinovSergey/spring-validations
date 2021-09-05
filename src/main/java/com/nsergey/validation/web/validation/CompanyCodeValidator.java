package com.nsergey.validation.web.validation;

import com.nsergey.validation.service.CompanyService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CompanyCodeValidator implements ConstraintValidator<CompanyCode, String> {
    private static final String PRODUCT_CODE_REGEX = ".{9}"; // можно вынести в проперти

    private final Pattern pattern = Pattern.compile(PRODUCT_CODE_REGEX);
    private boolean allowNull;

    private final CompanyService companyService;

    public CompanyCodeValidator(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public void initialize(CompanyCode constraintAnnotation) {
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return allowNull;
        }
        if (!pattern.matcher(value).matches()) {
            return false;
        }
        if (!companyService.isCompanyActive(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Компания не активна").addConstraintViolation();
            return false;
        }
        return true;
    }

}
