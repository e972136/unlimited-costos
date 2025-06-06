package com.gaspar.unlimited_costos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
public class UnlimitedCostosApplication {
	@Bean
	public SpringDataDialect springDataDialect() {
		return new SpringDataDialect();
	}
	public static void main(String[] args) {
		SpringApplication.run(UnlimitedCostosApplication.class, args);
	}

}
