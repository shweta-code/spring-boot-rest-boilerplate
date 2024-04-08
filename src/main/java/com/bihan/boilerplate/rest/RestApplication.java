package com.bihan.boilerplate.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class RestApplication {
	public static void main(String[] args) {
		System.out.println("Starting the application.");
		SpringApplication.run(RestApplication.class, args);
	}
}
