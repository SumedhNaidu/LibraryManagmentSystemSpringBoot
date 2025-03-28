package com.digitallibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.digitallibrary.repository")
public class DigitalLibraryApplication {
	public static void main(String[] args) {
		SpringApplication.run(DigitalLibraryApplication.class, args);
	}
}