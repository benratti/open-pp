package com.benratti.openpp.api.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EstimateSessionServiceJDBCImplTestConfiguration {

    @Bean
    public EstimateSessionService service() {
        return new EstimateSessionServiceJDBCImpl();
    }

}
