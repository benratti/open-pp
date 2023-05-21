package com.benratti.openpp.api;

import com.benratti.openpp.api.services.EstimateSessionServiceJDBCImpl;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class OpenPPApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenPPApiApplication.class, args);
	}





}
