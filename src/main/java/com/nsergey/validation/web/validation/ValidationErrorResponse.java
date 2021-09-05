package com.nsergey.validation.web.validation;

import lombok.Data;

import java.util.Map;

@Data
public class ValidationErrorResponse {

    private final String id;
    private final String errorType = "VALIDATION_ERROR";
    private final Map<String, String> fieldErrors;

    public ValidationErrorResponse(String id, Map<String, String> fieldErrors) {
        this.id = id;
        this.fieldErrors = fieldErrors;
    }

}
