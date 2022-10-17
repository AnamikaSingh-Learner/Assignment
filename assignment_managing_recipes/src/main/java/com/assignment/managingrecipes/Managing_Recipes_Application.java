package com.assignment.managingrecipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.assignment.managingrecipes.exceptions.ApplicationExceptionHandler;

@EnableWebMvc
@SpringBootApplication
@EntityScan(basePackages = {"com.assignment.managingrecipes.entities"})
@EnableJpaRepositories(basePackages = "com.assignment.managingrecipes.repositories")
@Import(ApplicationExceptionHandler.class)
public class Managing_Recipes_Application {

	public static void main(String[] args) {
		SpringApplication.run(Managing_Recipes_Application.class, args);
	}
}
