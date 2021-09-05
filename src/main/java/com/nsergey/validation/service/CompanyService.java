package com.nsergey.validation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyService {

    public boolean isCompanyActive(String companyCode) {
        log.debug("Check if the company '{}' is active", companyCode);
        if ("987654321".equals(companyCode)) {
            log.debug("The company '{}' has been disabled", companyCode);
            return false;
        }
        return true;
    }

}
