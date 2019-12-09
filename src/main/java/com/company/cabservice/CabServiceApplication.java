package com.company.cabservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.company.cabservice.repository.jpa")
public class CabServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabServiceApplication.class, args);
	}

}
